Feature: Item Management
  As a user
  I want to create items in the shopping cart system
  So that I can manage my inventory

  Scenario: user add/update/remove items from cart successfully
    Given item "Laptop" with quantity 5 with price 1000 had been added to the store 
    And user created a cart with discountPercentage 10 
    When adding item "Laptop" with quantity 2
    Then the item "Laptop" should be added to the cart and item stock should be 3
    And cart total price should be 1800 after applying 10% discount
    When updating item "Laptop" quantity to 3
    Then the item "Laptop" quantity in the cart should be updated to 3 and item stock should be 2
    And cart total price should be 2700 after updating with 10% discount
    When removing item "Laptop" from the cart
    Then the item "Laptop" should be removed from the cart and item stock should be 5
    And the cart should be empty and cart total price should be 0

