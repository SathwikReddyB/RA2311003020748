# Notification System Design

Here is a quick overview of how I designed the notification backend system. I tried to keep the architecture as simple and modular as possible.

### Overview
The system basically has two main jobs:
1. Manage notifications (create them and list them out).
2. Send logs to an external evaluation server whenever something important happens.

### Components

**1. Models and DTOs**
I created a simple `Notification` class to hold the basic data: an ID, the type of notification, the message, and who it's for. There's also a `LogRequest` object that perfectly matches the format the remote logging server expects (stack, level, package name, and message).

**2. Controllers**
The `NotificationController` is the entry point. It has two main endpoints:
- A `POST` endpoint that takes in the notification details via a JSON body and passes them to the service.
- A `GET` endpoint to retrieve all the created notifications.

**3. Services**
- **NotificationService**: This handles the core logic. It creates the notification, adds a unique UUID, and stores it in a simple list. Once it's created, it immediately triggers the logger to record the success.
- **LoggingService**: This is the middleware part. It takes the log details and sends an HTTP POST request to the remote test server. 

### Overcoming Challenges
One issue I ran into was that the remote test server was throwing SSL certificate errors (PKIX path building failed) because it didn't have a trusted certificate. To fix this without breaking the flow, I wrote a custom `RestTemplate` inside the `LoggingService` that tells Java to trust the connection. It also automatically attaches the authorization token from the properties file to every request.

### Conclusion
By splitting the code into models, controllers, and services, the project stays clean. The logging is totally decoupled from the notification logic, meaning I can easily reuse the logger anywhere else in the app just by calling it.
