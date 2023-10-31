Feature: Calculator

  Scenario Outline: Add
    Given user types "<left>+<right>"
    When the calculator evaluates the expression
    Then the result should be "<result>"

    Examples:
      | left | right | result |
      | 1    | 2     | 3      |
      | 10   | 20    | 30     |
      | 100  | 200   | 300    |

  Scenario: Subtraction
    Given user types "1-6"
    When the calculator evaluates the expression
    Then the result should be "-5"

  Scenario: Multiplication
    Given user types "4*8"
    When the calculator evaluates the expression
    Then the result should be "32"

  Scenario: Division
    Given user types "10/5"
    When the calculator evaluates the expression
    Then the result should be "2"

  Scenario: Division by zero
    Given user types "20/0"
    When the calculator evaluates the expression
    Then the result should be "error: division by zero"

  Scenario Outline: Unknown expression
    Given user types "<expression>"
    When the calculator evaluates the expression
    Then the result should be ""

    Examples:
      | expression |
      | 2^3        |
      | ?          |