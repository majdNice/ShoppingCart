Feature: Item Management
  As a user
  I want to create items in the shopping cart system
  So that I can manage my inventory

  Scenario: Create a new item successfully
    Given the item system is initialized
    When I create an item with name "Laptop", quantity 5, and price 999
    Then the item should be created successfully
    And the item should be retrievable by name "Laptop"
