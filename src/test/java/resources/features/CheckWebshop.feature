Feature: Webshop Test
  Automatic webshop test for the school.

  Scenario: Verify the image element on the webpage
    Given Webshop.Netify is available
    When User checks the image element
    Then the image source should be "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
    And the image height should be "650"
    And the image should have an alt text

  Scenario: User scroll to locate checkout button and verify Billing and Payment headings on check out page
    Given the user is on the webshop homepage
    When the user scrolls down to the "Checkout" link
    Then the user clicks the "Checkout" link
    And the user scrolls down to the "Billing address" heading
    Then the "Billing address" heading text should be "Billing address"
    And the user scrolls down to the "Payment" heading
    Then the "Payment" heading text should be "Payment"

  Scenario: Check if the declared language and content language match
    Given I open the web page "https://webshop-agil-testautomatiserare.netlify.app/"
    When I check the "lang" attribute of the "html" tag
    Then the language should be "sv"
    And the content should appear in English