Feature: Get info from APIs

#  @positive @api @air
#  Scenario Outline: Get air pollution for P1 and P2
#    Given base url: "https://airtube.info/api/get_data_current.php?location_id="
#    And endpoint: "<sensor>"
#    When I made a call
#    Then I should receive data
#
#    Examples:
#      | sensor |
#      | 3129   |
#
#  @api
#  Scenario: Get information for my Repositories
#    Given base url: "https://api.github.com/users/adchaos/"
#    And endpoint: "repos"
#    When I made a call
#    Then I should receive data for my repos

  @api
  Scenario: Get users
    Given base url: "https://reqres.in/"
    And endpoint: "api/users?page=2"
    When I list all users
    Then I should receive: 3 users

  @api
  Scenario: Post user
    Given base url: "https://reqres.in/"
    And endpoint: "api/users"
    When Add user
    Then User added successfully

  @ui
  Scenario: Verify contacts
    Given open browser "chrome" and load "https://progressbg.net/%D0%BA%D0%BE%D0%BD%D1%82%D0%B0%D0%BA%D1%82%D0%B8/"
    When Verify page title
    Then It is written: "Контакти"