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

  Scenario: Verify items in the cart
    Given the user is on the webpage
    When the user clicks the "Shop" button
    And the user scrolls down to the "Add to Cart" button for "Mens Casual Premium Slim Fit T-Shirts"
    And the user clicks the "Add to Cart" button
    And the user clicks the "Checkout" button
    Then the item "Mens Casual Premium Slim Fit T-Shirts" should be present in the cart
    And the item "Non-Existing Item" should not be present in the cart