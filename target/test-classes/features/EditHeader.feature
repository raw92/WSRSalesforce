Feature: Edit Header


#Scenario: Employee Edit Header Name
#Given Landing page is displayed
#And User moves to WSR Builder
#And User click in Headers tab
#When User click arrow, then in name field, then send new name "Header name changed cucumber" and save
#Then Validate if header name changed for "Header name changed cucumber"


Scenario: Employee Edit Header Name, then try to move to another tab without saving, a window shows warning the user then cancel
the editing and check if the name changed or is the old one
Given Landing page is displayed
And User moves to WSR Builder
And User click in Headers tab
When User click on header name from headers tab
And Click in name field it send new name "Header name changed cucumber X"
And Move to tab "Holidays" a window shows and click on continue editing button
And Click on cancel button from Header details
Then Validate if header name changed for "Header name changed cucumber X"




