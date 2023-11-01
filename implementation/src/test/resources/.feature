Feature: ControlCar

  Scenario: Capture Speed
    Given Car drives by the speedcamera
    When car drives next to speedcamera
    Then the speedcamera captures the speed of the car

  Scenario: activate light
    Given the speed of car
    When speed of car is above 50
    Then Activate Light

  Scenario: capture picture
    Given the speed of car
    When speed of car is above 50
    Then Capture Picture

    Scenario: capture timestamp
      Given the speed of car
      When speed of car is above 50
      Then Capture timestamp

  Scenario: send Data
    Given the speed of car, the picture and the timestamp
    When speed of car is above 50
    Then send Data to FineEngine



