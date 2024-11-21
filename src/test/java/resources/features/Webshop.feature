Feature: Webshop Test
Automatic webshop test for the school.

  Scenario: Verify that the website copyright text displays correctly
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the copyright text should be "© 2023 The Shop"

  Scenario: Validate SSL certificate
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the SSL certificate should be valid and not expiring in 60 days