Feature: Webshop Test
Automatic webshop test for the school.

  Scenario: Verify that the website copyright text displays correctly
    Given Webshop is available
    When User visits webshop-agil-testautomatiserare.netlify.app
    Then the copyright text should be "© 2023 The Shop"