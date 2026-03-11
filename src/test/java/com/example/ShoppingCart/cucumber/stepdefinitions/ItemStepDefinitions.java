package com.example.ShoppingCart.cucumber.stepdefinitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
public class ItemStepDefinitions {

    // We'll initialize MockMvc manually using the web application context
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    private String itemName;
    private int itemQuantity;
    private int itemPrice;

    @Given("the item system is initialized")
    public void initializeItemSystem() {
        // MockMvc is auto-configured
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @When("I create an item with name {string}, quantity {int}, and price {int}")
    public void createItem(String name, int quantity, int price) throws Exception {
        itemName = name;
        itemQuantity = quantity;
        itemPrice = price;

        String json = String.format("""
                {"name":"%s","quantity":%d,"price":%d}
                """, itemName, itemQuantity, itemPrice);

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Then("the item should be created successfully")
    public void verifyItemCreated() {
        // Already verified in previous step with status check
    }

    @Then("the item should be retrievable by name {string}")
    public void verifyItemIsRetrievable(String name) throws Exception {
        mockMvc.perform(get("/api/items/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.quantity").value(itemQuantity))
                .andExpect(jsonPath("$.price").value(itemPrice));
    }
}
