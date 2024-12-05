Feature: Webshop - Products
  Automatic webshop test for the school.

  ## Author: Jarko Piironen
  Scenario: Validate Search Functionality
    Given User visits the products page at "https://webshop-agil-testautomatiserare.netlify.app/products"
    When User searches for "Gold"
    Then the search results should display items related to "Gold"

  ## Author: Jarko Piironen
  Scenario Outline: Validate Filter Functionality
    Given User visits the products page at "https://webshop-agil-testautomatiserare.netlify.app/products"
    When the user clicks on "<filter>"
    Then the user verifies that the "<filter>" loads its respective products

    Examples:
      | filter           |
      | All              |
      | Men's clothing   |
      | Women's clothing |
      | Jewelery         |
      | Electronics      |

  ## Author: Ingela Bladh
  Scenario: Verify product has image, title, price, description and Add to cart button
    Given User visits the products page at "https://webshop-agil-testautomatiserare.netlify.app/products"
    Then Product should have all elements