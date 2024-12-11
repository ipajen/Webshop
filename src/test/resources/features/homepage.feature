Feature: Webshop - Homepage
  Automatic webshop test for the school.

  Background:
    Given Webshop is available

  ## Author: Jarko Piironen
  Scenario: Verify that the website copyright text displays correctly
    Then the copyright text should be "© 2024 The Shop"

  ## Author: Jarko Piironen
  Scenario: Verify that there are no uncaught syntax errors in the browser console logs
    Then the console logs should not contain errors

  ## Author: Jarko Piironen
  Scenario: Validate SSL certificate
    Then the SSL certificate should be valid and not expiring in 60 days

  ## Author: Jarko Piironen
  Scenario Outline: Verify footer navigation links
    When the user clicks the following "<Link>" link
    Then the "<Page>" page should be displayed

    Examples:
      | Link     | Page                                                         |
      | Home     | https://webshop-agil-testautomatiserare.netlify.app/         |
      | Shop     | https://webshop-agil-testautomatiserare.netlify.app/products |
      | Checkout | https://webshop-agil-testautomatiserare.netlify.app/checkout |
      | About    | https://webshop-agil-testautomatiserare.netlify.app/about    |

  ## Author: Barnali Mohanty
  Scenario: Verify the image element on the webpage
    When User checks the image element
    Then the image source should be "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
    And the image height should be "650"
    And the image should have an alt text

  ## Author: Barnali Mohanty
  Scenario: Check if the declared language and content language match
    When I check the "lang" attribute of the "html" tag
    Then the language should be "sv"
    And the content should appear in English

  ## Author: Barnali Mohanty
  Scenario: Check website title
    When User checks the title
    Then the title should start with "The Shop"
    And the heading should be "🛍️ The Shop"

  ## Author: Barnali Mohanty
  Scenario: User checks the main heading text
    When User checks the main heading
    Then the main heading should be "This shop is all you need"

  ## Author: Ingela Bladh
  Scenario Outline: Verify header navigation links
    When User clicks link with "<cssSelector>"
    Then The page displayed should be "<expectedPage>"

    Examples:
      | cssSelector                                                                      | expectedPage  |
        ## Shop
      | body > header > div > div > ul > li:nth-child(2) > a                             | products      |
        ## About
      | body > header > div > div > ul > li:nth-child(3) > a                             | about         |
        ## Checkout
      | body > header > div > div > div > a                                              | checkout      |
        ## All products
      | body > div.container.my-5 > div > div.col-lg-7.p-3.p-lg-5.pt-lg-3 > div > button | products.html |