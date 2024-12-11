Feature: Webshop - Checkout
  Automatic webshop test for the school.

  ## Author: Ingela Bladh
  Scenario: Verify that the Total sum is correct and that the Remove button works
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/products"
    And User adds backpack to cart
    And User adds Tshirt to cart
    And User adds jacket to cart
    When User visits the page "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    Then The Total sum should be correct
    And User removes items
    Then Your cart list should only contain Total

  ## Author: Ingela Bladh
  Scenario: Verify email form field validation - Invalid email
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User fills in email field with "test.email.com"
    And User clicks Continue to checkout button
    Then The page should show "Please enter a valid email address for shipping updates."

  ## Author: Ingela Bladh
  Scenario: Verify email form field validation - Valid email
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User fills in email field with "test@email.com"
    And User clicks Continue to checkout button
    Then A check mark should be displayed

  ## Author: Ingela Bladh
  Scenario: Verify the payment radio buttons work
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User selects PayPal radio button
    Then The page should display "You will be redirected to PayPal in the next step."
    And The card div should have the class "hidden-element"
    And User selects Credit card radio button
    Then The card div should not have any class
    And User selects PayPal radio button
    Then The page should display "You will be redirected to PayPal in the next step."
    And The card div should have the class "hidden-element"
    And User selects Credit card radio button
    Then The card div should not have any class

  ## Author: Ingela Bladh
  Scenario: Verify the Continue to checkout button works
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User clicks Continue to checkout button
    Then The form tag should have the classes "needs-validation was-validated"

  ## Author: Barnali Mohanty
  Scenario: User scrolls to locate checkout button and verify Billing and Payment headings on check out page
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/"
    When the user scrolls down to the "Checkout" link
    And the user clicks the "Checkout" link
    And the user scrolls down to the "Billing address" heading
    Then the "Billing address" heading text should be "Billing address"
    And the user scrolls down to the "Payment" heading
    Then the "Payment" heading text should be "Payment"

  ## Author: Barnali Mohanty
  Scenario: Verify items in the cart
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/products"
    When the user clicks the Add to Cart button
    And the user clicks the Checkout button
    Then the item "Mens Casual Premium Slim Fit T-Shirts" should be present in the cart

  ## Author: Barnali Mohanty
  Scenario: Display error messages when required fields are left empty
    Given User visits the page "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    When the user clicks the "Continue to checkout" button without filling required fields
    Then the error messages for required fields should be displayed