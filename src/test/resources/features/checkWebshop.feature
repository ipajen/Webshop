Feature: Check Webshop
  Automatic webshop test for the school.

  Scenario: Verify the image element on the webpage
    Given Webshop.Netify is available
    When User checks the image element
    Then the image source should be "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
    And the image height should be "650"
    And the image should have an alt text

  Scenario: User scroll to locate checkout button and verify Billing and Payment headings on check out page
    Given the user is on the webshop homepage
    When the user scrolls down to the "Checkout" link
    Then the user clicks the "Checkout" link
    And the user scrolls down to the "Billing address" heading
    Then the "Billing address" heading text should be "Billing address"
    And the user scrolls down to the "Payment" heading
    Then the "Payment" heading text should be "Payment"


  Scenario: Verify items in the cart
   Given the user is on the ProductWebpage
    When the user clicks the Add to Cart button
    And the user clicks the Checkout button
    Then the item "Mens Casual Premium Slim Fit T-Shirts" should be present in the cart

  Scenario: Check if the declared language and content language match
    Given I open the web page "https://webshop-agil-testautomatiserare.netlify.app/"
    When I check the "lang" attribute of the "html" tag
    Then the language should be "sv"
    And the content should appear in English
  
  Scenario: Display error messages when required fields are left empty
    Given the user is on the checkout page at "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    When the user clicks the "Continue to checkout" button without filling required fields
    Then the error messages for required fields should be displayed
  
  Scenario: Check website title
    Given User is on the Webpage
    When User checks the title
    Then the title should be "The Shop | Checkout"
    And the heading should be "🛍️ The Shop"
  
  Scenario: User checks the main heading text
    Given User is on the Home page
    When User checks the main heading
    Then the main heading should be "This shop is all you need"
