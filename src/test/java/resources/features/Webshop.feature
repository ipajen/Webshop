Feature: Webshop Test
  Automation Testing Of the Webshop for the ITHS project


  Scenario: User checks the main heading text
    Given Webshop is available
    When User checks the main heading
    Then the main heading should be "This shop is all you need"

