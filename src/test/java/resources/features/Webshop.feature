Feature: Webshop Test
Automatic webshop test for the school.

  Scenario: Verify that the website copyright text displays correctly
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the copyright text should be "© 2023 The Shop"

  Scenario: Verify that there are no uncaught syntax errors in the browser console logs
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the console logs should not contain errors

  Scenario: Validate SSL certificate
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the SSL certificate should be valid and not expiring in 60 days

  Scenario: Validate Search Functionality
    Given Webshop is available
    When User visits the products page at "webshop-agil-testautomatiserare.netlify.app/products"
    And User searches for "Gold"
    Then the search results should display items related to "Gold"
    #TODO search with gold and another with press enter.
    #And User searches for "gold"
    #Then the search results should display items related to "gold"
    #users press enter
