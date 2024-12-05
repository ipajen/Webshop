Feature: Webshop - Homepage
  Automatic webshop test for the school.

  ## Author: Jarko Piironen
  Scenario: Verify that the website copyright text displays correctly
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the copyright text should be "© 2024 The Shop"

    ## Author: Jarko Piironen
  Scenario: Verify that there are no uncaught syntax errors in the browser console logs
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the console logs should not contain errors

    ## Author: Jarko Piironen
  Scenario: Validate SSL certificate
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the SSL certificate should be valid and not expiring in 60 days

    ## Author: Jarko Piironen
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

    ## Author: Barnali Mohanty
  Scenario: Verify the image element on the webpage
    Given Webshop is available
    When User checks the image element
    Then the image source should be "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
    And the image height should be "650"
    And the image should have an alt text

    ## Author: Barnali Mohanty
  Scenario: Check if the declared language and content language match
    Given I open the web page "https://webshop-agil-testautomatiserare.netlify.app/"
    When I check the "lang" attribute of the "html" tag
    Then the language should be "sv"
    And the content should appear in English

    ## Author: Barnali Mohanty
  Scenario: Check website title
    Given User is on the Webpage
    When User checks the title
    Then the title should be "The Shop | Checkout"
    And the heading should be "🛍️ The Shop"

    ## Author: Barnali Mohanty
  Scenario: User checks the main heading text
    Given User is on the Home page
    When User checks the main heading
    Then the main heading should be "This shop is all you need"

    ## Author: Ingela Bladh
  Scenario: Verify that the Shop link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks Shop link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/products"

     ## Author: Ingela Bladh
  Scenario: Verify that the About link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks About link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/about"

     ## Author: Ingela Bladh
  Scenario: Verify that the Checkout button works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks Checkout button
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/checkout"

     ## Author: Ingela Bladh
  Scenario: Verify that the Home image link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    When User clicks Home image link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/"

     ## Author: Ingela Bladh
  Scenario: Verify that the Home link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    When User clicks Home link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/"

     ## Author: Ingela Bladh
  Scenario: Verify that the "All products" button on Homepage leads to Products page
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks All products button
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/products.html"