# TicketingService

A simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance
venue

**Dependencies**
- JUnit
- gradle
- JDK 1.8

**Assumptions**
- Seat numbering is not considered. Booking done based on the number of seats in a level
- Seats should be first held before they are booked. Reservations are done only on the seats which are held already

**Points to Note**
- Initial data loaded with MockDataLoader
- Spring used for dependency management and scheduling release of seats based on a fixed delay 
- Since TicketingService is wrtten in a way it could be extended as a webapp also
- Test Coverage except for models is 100%. This includes mocking the dependencies and testing all scenarios
- Any seats which are held and not booked for a certain time interval(mentioned in application.properties) are automatically released 
- Used Joda for DateTime. Have included the dependency in the gradle build


**Bulid Instructions**
````
gradle clean build
````

**Test Instructions**
````
gradle test
````

**Run application**
- Currently the console application doesn't do much since it is just uses TicketService as an API
- Since this is running as Springboot the process won't stop until it is killed

````
gradle bootRun
````
