Feature: Check Webshop
  Automatic webshop test for the school.

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
  
  Scenario: Display error messages when required fields are left empty
    Given the user is on the checkout page at "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    When the user clicks the "Continue to checkout" button without filling required fields
    Then the error messages for required fields should be displayed