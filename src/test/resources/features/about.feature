Feature: Webshop - About
  Automatic webshop test for the school.

  ## Author: Ingela Bladh
  Scenario: Verify that the "To all products" button on About page leads to Products page
    Given User navigates to "https://webshop-agil-testautomatiserare.netlify.app/about"
    When User clicks To all products button
    Then The url should be "https://webshop-agil-testautomatiserare.netlify.app/products.html"




