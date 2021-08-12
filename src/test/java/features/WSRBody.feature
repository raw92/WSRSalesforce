Feature: WSRBody


Scenario: Validates Body fields present
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
When User click new, Body form display
Then Validate all the fields from the form are displayed


Scenario: Creates WSR Body
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
When User click new, fills all the fields from the form and click save
Then Validate if the body was created


Scenario: Validates Empty days fields from Body creation displays an error when they are empty
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
When User click new, Body form display
Then Fill in all fields, leave one day empty then save, it repeats for everyday error is displayed


Scenario: Validates Extra Hours are not allowed
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
When User click new, Body form display
Then Fill in all fields, putting extra hours then save, it repeats for every day hours fields an error is displayed


Scenario: Validates total stories amount
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
When User click new, Body form display
Then Fill in all fields, validates the total stories amounts


Scenario: Validates error when Sprint end date is before start date
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
When User click new, then fill in all fields sending end date "2021-08-30" and start date "2021-08-25"
Then validates Sprint end date could not be before start date


Scenario: Validates error when Sprint end and start date are the same
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
When User click new, then fill in all fields and sending "2021-08-25" for both dates
Then validates Sprint end date and start date should not be able to be the same day


Scenario: Validates send record to manager for the week and checks for email
Given Landing page is displayed
And User moves to WSR Builder
And User click in Body WSRs tab
And User click on Body Name "Rodrigo Miguez"
And User click on Header Name to validate if the email there is the same than "test@test.com" then go back to details
Then click on submit manager button and proceed to click save to send the report