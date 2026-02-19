# LOGAN KURR's QA Interview Submission

## 1.  High level framework architecture?
I decided to use `Java` with `Selenium`, `TestNG`, & `RestAssured` for my testing purposes because it is what I am currently most familiar with.
I apologize for not using `Playwright` and I hope that is acceptable. If need be for the role in the future I am definitely capable of using TypeScript, but for time-crunch sakes I utilized Selenium!

## 2.  Key design decisions and reasoning?
I try to separate and organize my tests/pages/helper as much as possible for 2 main reasons
1. Readability
2. Reusability

To best do, so I sectioned everything into the following level design
- **Flows:** This utilizes all the created Service Level steps to be able to easily place functionality where needed while making the `@Tests` as easily readable as possible. This helps with highlighting clear tests, outlining coverage, and onboarding new members. 
  - **Service Level:** This contains collected chains of Page Level actions to be defined in reusable step methods. This will help when creating new tests for similar sections of the site.
    - **Page Level:** This is the lowest level where all the nitty-gritty actions are defined _(ie: Clicking elements, filling out fields, and validations)_. These are defined here to be able to easily find a pages specific elements/actions.

## 3.  How UI and API tests interact in your approach?
In my current approach they don't particularly interact, but that is something I would like to improve on in the future if I had some more time with this project!

A few things I would likely change on first glance are >>
* If several tests were created needing Booking Data creating those entire via `POST` Request from the API
  * This would skip the respective steps of doing so via the UI and save on RunTime
* If `Login` is needed and repeatedly re-used utilize Session Storage and Cookie setting via API to skip monotonous UI Login time

^^ So essentially I would love to skip a lot of the repetive UI steps for setup via API!

## 4.  How test data and dates are managed?
- **DATES**: 
  - I managed this by creating a `DateHelper` File which assisted in the following
    -  Handling specific formatting of the Date `yyyy-MM-dd` _(which would also house other formats in the future)_
    - Handling Date Parsing _(to grab month, year, day)_
    - Calculating Future Dates _(to ensure not stale Date Values)_
- **DATA:**
  - I managed this by creating a `StayDetails` Object to house all my necessary data regarding the Booking
    - This included all necessary info for a booking such as `firstName`, `email`, etc. and it could be used and referenced thoughout the test
    - This also made it so when proving the data to methods I only had to pass it the one `stayDetails` param
      - In the future I would have liked to make a `StayDetailsFactory` to handle creating DEFAULT values
    

## 5.  How tests are executed locally?
* These tests can be executed locally by either of the following methods >>
  * 1. **Via the IDE Itself:** For example for this exercise I used Intellij to create these tests so when running them I simply was able to `Shift + F10` in order to run the specified file
    1. **Via the Command Line:** This can be done via a `mvn` command. For example `mvn clean test -Dsurefire.suiteXmlFiles=qa/src/test/suites/ui-flows.xml` would kick off the entire UI Flows Suite
       2. Or for a single test EX: `mvn test -Dtest=ContactFlow.java` would kick off only that specified Flow

## 6.  How you would run this in CI?
* These tests would be able to be run via their corresponding `.xml` file!
  * **GITLAB:** This would be done by being specified via a specified `.gitlab-ci.yml` file that contains specified stages, rules, etc.
    * Then as part of the `script` it would include the specified `mvn` command
      * Ex: `mvn clean test -Dsurefire.suiteXmlFiles=qa/src/test/suites/api-flows.xml`
      * With this approach we can also add any specified Environment Variables as well

## 7.  What you would improve next with more time?
* With more time I think I would definitely begin with more validations
  * There are several areas _(ie Invalid User Field Values, Unavailable Dates, etc.)_ that I would love to have provided more coverage for to ensure that their requirements, and all cases were met.
* I would also likely cleanup a lot of re-used portions of the code
  * When splitting them into steps & perform actions I already did this to provide reusability for future tests, but there are some cases in the API portion I could cleanup the JSON Creation and REQUEST defaults


### Logan Kurr's LinkedIn >>
https://www.linkedin.com/in/logankurr/