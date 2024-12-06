Feature: Webshop - Products
  Automatic webshop test for the school.

  Background: User visits the products page at "https://webshop-agil-testautomatiserare.netlify.app/products"

  ## Author: Jarko Piironen
  Scenario: Validate Search Functionality
    When User searches for "Gold"
    Then the search results should display items related to "Gold"

  ## Author: Jarko Piironen
  Scenario Outline: Validate Filter Functionality
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
    Then Product should have all elements