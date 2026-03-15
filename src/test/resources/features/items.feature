Feature: Item Management
  As a user
  I want to manage items in the shopping cart system
  So that I can maintain my inventory

  Background:
    Given the following items exist in the store:
      | name   | quantity | price |
      | Laptop | 5        | 1000  |
    And user created a cart with discountPercentage 10

  Scenario: Add item to cart successfully
    When adding item "Laptop" with quantity 2
    Then the item "Laptop" should be added to the cart and item stock should be 3
    And cart total price should be 1800 after applying 10% discount

  Scenario: Update item quantity in cart successfully
    Given item "Laptop" with quantity 2 has been added to the cart
    When updating item "Laptop" quantity to 3
    Then the item "Laptop" quantity in the cart should be updated to 3 and item stock should be 2
    And cart total price should be 2700 after updating with 10% discount

  Scenario: Remove item from cart successfully
    Given item "Laptop" with quantity 2 has been added to the cart
    When removing item "Laptop" from the cart
    Then the item "Laptop" should be removed from the cart and item stock should be 5
    And the cart should be empty and cart total price should be 0

