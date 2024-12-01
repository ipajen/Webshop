Feature: Verify Webshop
  Automatic webshop test for the school.

  Scenario: Verify that the Shop link works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    When User clicks Shop link
    Then The current url should be "https://webshop-agil-testautomatiserare.netlify.app/products"

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
  
  Scenario: Verify that the Products button has the text "All products"
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app"
    Then The button text should be "All products"
  
  Scenario: Verify that the Remove button works
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    And User clicks Add to cart button
    And User clicks Checkout button
    When User clicks Remove button
    Then Your cart list should only contain Total

  Scenario: Verify product has image, title, price, description and Add to cart button
    Given User visits "https://webshop-agil-testautomatiserare.netlify.app/products"
    Then Product should have all elements


