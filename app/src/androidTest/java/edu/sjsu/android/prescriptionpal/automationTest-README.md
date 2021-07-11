These automated unit tests check the functionalities of the frontend UI implementation- how the application responds to button clicks, text inputs, etc.. It also tests the backend database functionalities such as fetching data from Google firestore and writing data to it.

Automation test 1: Test the sign in functionality.
- If a user enters a valid email address and password combination, the test case passes.
- If a user enters an invalid email address and password combination, the test case fails.

Automation test 2: test if a user can add his or her information
- If the user enters a valid age, phone number, and address, the test case passes.
- If the user enters an invalid age, phone number or address, the test case fails.

Automation test 3: test if a user can change his or her default pharmacy store
- If the user is able to update his or her default pharmacy and display the information on the “show pharmacy” page, the test case passes; otherwise, the test case fails.  

Instructions to run automation tests:
1. Install Appium from https://appium.io/
2. Start Appium server on host 0.0.0.0, port 4723
3. Open Android Studio, start application simulator
4. Go to src/androidTest/java/edu.sjsu.prescriptionpal and run 'automationTest3.main()' with coverage
5. If the process exits with code 0, it is a successful test.
