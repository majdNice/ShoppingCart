package com.example.ShoppingCart.cucumber.stepdefinitions;

import static org.hamcrest.Matchers.hasSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.Before;

@SpringBootTest
public class ItemStepDefinitions {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    private String itemName;
    private int itemQuantity;
    private int itemPrice;
    private String cartId;
    private int discountPercentage;

    @Before
    public void initializeItemSystem() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Given("item {string} with quantity {int} with price {int} had been added to the store")
    public void addItemToStore(String name, int quantity, int price) throws Exception {
        itemName = name;
        itemQuantity = quantity;
        itemPrice = price;

        String json = String.format("""
                {"name":"%s","quantity":%d,"price":%d}
                """, name, quantity, price);

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @And("user created a cart with discountPercentage {int}")
    public void createCartWithDiscount(int discount) throws Exception {
        discountPercentage = discount;

        String json = String.format("""
                {"discountPercentage":%d}
                """, discount);

        MvcResult result = mockMvc.perform(post("/api/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        cartId = com.jayway.jsonpath.JsonPath.read(
                                result.getResponse().getContentAsString(), "$.cartId");;
    }

    @When("adding item {string} with quantity {int}")
    public void addItemToCart(String name, int quantity) throws Exception {
        String json = String.format("""
                {"itemName":"%s","quantity":%d}
                """, name, quantity);

        mockMvc.perform(post("/api/carts/" + cartId + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Then("the item {string} should be added to the cart and item stock should be {int}")
    public void verifyItemAddedAndStockReduced(String name, int newStock) throws Exception {
        // Verify item is in cart
        mockMvc.perform(get("/api/carts/" + cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[?(@.itemName == '" + name + "')]").exists());

        // Verify item stock reduced
        mockMvc.perform(get("/api/items/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(newStock));
    }

    @And("cart total price should be {int} after applying 10% discount")
    public void verifyCartTotalPrice(int expectedTotal) throws Exception {
        mockMvc.perform(get("/api/carts/" + cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(expectedTotal));
    }

    @When("updating item {string} quantity to {int}")
    public void updateItemQuantityInCart(String name, int newQuantity) throws Exception {
        String json = String.format("""
                {"quantity":%d}
                """, newQuantity);

        mockMvc.perform(put("/api/carts/" + cartId + "/items/" + name)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Then("the item {string} quantity in the cart should be updated to {int} and item stock should be {int}")
    public void verifyItemUpdatedAndStockAdjusted(String name, int newQuantity, int newStock) throws Exception {
        // Verify quantity in cart
        mockMvc.perform(get("/api/carts/" + cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[?(@.itemName == '" + name + "')].quantity").value(newQuantity));

        // Verify item stock
        mockMvc.perform(get("/api/items/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(newStock));
    }
    @And("cart total price should be {int} after updating with 10% discount") 
    public void verifyCartTotalPriceAfterUpdate(int expectedTotal) throws Exception {
        mockMvc.perform(get("/api/carts/" + cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(expectedTotal));
    }

    @When("removing item {string} from the cart")
    public void removeItemFromCart(String name) throws Exception {
        mockMvc.perform(delete("/api/carts/" + cartId + "/items/" + name))
                .andExpect(status().isOk());
    }

    @Then("the item {string} should be removed from the cart and item stock should be {int}")
    public void verifyItemRemovedAndStockRestored(String name, int newStock) throws Exception {
        // Verify item not in cart
        mockMvc.perform(get("/api/carts/" + cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[?(@.itemName == '" + name + "')]").doesNotExist());

        // Verify item stock restored
        mockMvc.perform(get("/api/items/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(newStock));
    }

    @And("the cart should be empty and cart total price should be {int}")
    public void verifyCartEmptyAndPriceZero(int expectedPrice) throws Exception {
        mockMvc.perform(get("/api/carts/" + cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(0)))
                .andExpect(jsonPath("$.finalTotal").value(expectedPrice));
    }
}
