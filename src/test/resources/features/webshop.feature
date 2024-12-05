Feature: Webshop
  Automatic webshop test for the school.




  
  Scenario: Validate Search Functionality
    Given User visits the products page at "https://webshop-agil-testautomatiserare.netlify.app/products"
    When User searches for "Gold"
    Then the search results should display items related to "Gold"
    #TODO search with gold and another with press enter.
  

  
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
