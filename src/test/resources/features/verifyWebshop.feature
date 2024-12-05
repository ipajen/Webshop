Feature: Verify Webshop
  Automatic webshop test for the school.

  Scenario: Verify that the "To all products" button on About page leads to Products page
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/about"
    When User clicks To all products button
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/products.html"

  Scenario: Verify that the Remove button works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    And User clicks Add to cart button
    And User clicks Checkout button
    When User clicks Remove button
    Then Your cart list should only contain Total

  Scenario: Verify email form field validation - Wrong
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User fills in email field with "test.email.com"
    And User clicks Continue to checkout button
    Then The page should show "Please enter a valid email address for shipping updates."

  Scenario: Verify email form field validation - Correct
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User fills in email field with "test@email.com"
    And User clicks Continue to checkout button
    Then A check mark should be displayed

  Scenario: Verify the payment radio buttons work
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
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

  Scenario: Verify the Continue to checkout button works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User clicks Continue to checkout button
    Then The form tag should have the classes "needs-validation was-validated"


