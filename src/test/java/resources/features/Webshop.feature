Feature: Webshop Test
Automation Testing Of the Webshop for the ITHS project


 Scenario: Check website title
   Given Webshop is available
   When User checks the title
   Then the title should be "Webbutiken"
   And the heading should be "🛍️ The Shop"