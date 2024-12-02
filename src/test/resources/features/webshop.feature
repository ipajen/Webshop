Feature: Webshop
  Automatic webshop test for the school.

  Scenario: Verify that the website copyright text displays correctly
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the copyright text should be "© 2024 The Shop"

  Scenario: Verify that there are no uncaught syntax errors in the browser console logs
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the console logs should not contain errors

  Scenario: Validate SSL certificate
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the SSL certificate should be valid and not expiring in 60 days
  
  Scenario: Validate Search Functionality
    Given User visits the products page at "https://webshop-agil-testautomatiserare.netlify.app/products"
    When User searches for "Gold"
    Then the search results should display items related to "Gold"
    #TODO search with gold and another with press enter.
  
  Scenario Outline: Verify navigation links
    Given Webshop is available
    When the user clicks the following "<Link>" link
    Then the "<Page>" page should be displayed
  
    Examples:
      | Link     | Page                                                           |
      | Home     | https://webshop-agil-testautomatiserare.netlify.app/           |
      | Shop     | https://webshop-agil-testautomatiserare.netlify.app/products   |
      | Checkout | https://webshop-agil-testautomatiserare.netlify.app/checkout   |
      | About    | https://webshop-agil-testautomatiserare.netlify.app/about      |
  
  Scenario Outline: Validate Filter Functionality
    Given User visits the products page at "https://webshop-agil-testautomatiserare.netlify.app/products"
    When the user clicks on "<filter>"
    Then the user verifies that the "<filter>" loads its respective products
    
    Examples:
      | filter            |
      | All               |
      | Men's clothing    |
      | Women's clothing  |
      | Jewelery          |
      | Electronics       |
