Feature: Verify Webshop
  Automatic webshop test for the school.

  Scenario: Verify that the Shop link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks Shop link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/products"

  Scenario: Verify that the About link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks About link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/about"

  Scenario: Verify that the Checkout button works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks Checkout button
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/checkout"

  Scenario: Verify that the Home image link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    When User clicks Home image link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/"

  Scenario: Verify that the Home link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    When User clicks Home link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/"

  Scenario: Verify that the "All products" button on Homepage leads to Products page
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks All products button
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/products.html"

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

  Scenario: Verify product has image, title, price, description and Add to cart button
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    Then Product should have all elements

  Scenario: Verify email form field validation - Wrong
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User fills in email field with "test.email.com"
    And User clicks Continue to checkout button
    Then An error message should be displayed

  Scenario: Verify email form field validation - Correct
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User fills in email field with "test@email.com"
    And User clicks Continue to checkout button
    Then A check mark should be displayed

  Scenario: Verify the PayPal radio button works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User selects PayPal radio button
    Then The page should display "You will be redirected to PayPal in the next step."

  Scenario: Verify the Continue to checkout button works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/checkout"
    And User clicks Continue to checkout button
    Then The form tag should have the classes "needs-validation was-validated"


