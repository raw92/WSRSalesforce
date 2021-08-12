Feature: Create Header


Scenario: Create a Header
Given Landing page is displayed
And User moves to WSR Builder
And User click in Headers tab
When User click new, fills all the required fields and click save
Then Validate confirmation message



Scenario: Error creating header without name
Given Landing page is displayed
And User moves to WSR Builder
And User click in Headers tab
When User click new, set all the required fields but leaves Header name empty and click save
Then Validate error message for name field


Scenario: Error creating header without user
Given Landing page is displayed
And User moves to WSR Builder
And User click in Headers tab
When User click new, set all the required fields but leaves user name empty and click save
Then Validate error message for user field


Scenario: Error creating header without proper email format
Given Landing page is displayed
And User moves to WSR Builder
And User click in Headers tab
When User click new, set all the required fields but send email field with "testcucumber.com" and click save
Then Validate error message for email field



Scenario: Error creating header with invalid user
Given Landing page is displayed
And User moves to WSR Builder
And User click in Headers tab
When User click new, set all the required fields but send user field with "invalid user" and click save
Then Validate error message for wrong user field


Scenario: Error creating header just one per user allowed
Given Landing page is displayed
And User moves to WSR Builder
And User click in Headers tab
When User start going through all headers check if "Rodrigo Miguez" already have one assigned
Then proceed to create the header
