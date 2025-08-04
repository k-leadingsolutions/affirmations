# Daily Affirmations & Mental Health REST API

A Spring Boot project for daily affirmations, intentions and mental health resources.  
Includes secure JWT authentication, CRUD operations, personalized endpoints and Swagger API docs.
Includes a real-time chat system using WebSockets. Includes a modern UI for sending and viewing live affirmations.

---

## Features

- **User Registration & JWT Login**
- **CRUD for Affirmations, Intentions**
- **Personalized Recommendations**
- **Help Resources (helplines, etc.)**
- **Feedback & Reporting**
- **User Stats**
- **Full API Documentation (Swagger/OpenAPI)**
- **Unit/integration tests**
- **WebSocket (STOMP/SockJS) Live Chat** for real-time affirmations
- **Modern UI**: Responsive chat interface (`chat.html`)
- **Docker Support**: Run the app easily in a container

---

## WebSocket Functionality

- **WebSocket Endpoint:** `/ws-chat`  
  Powered by Spring WebSocket with STOMP and SockJS fallback.
- **Message Flow:**
   - Clients send messages to `/app/send`
   - Server broadcasts messages to `/topic/messages`
- **DTO:**  
  `ChatMessageDto` - Contains `username`, `message` and server-side `timestamp`.
- **Security:**
   - WebSocket message sending is restricted to authenticated users.
   - Uses `@PreAuthorize("isAuthenticated()")` annotation in `ChatController` to secure message sending.
- **Extensibility:**
   - Controller is split into an interface and a concrete implementation for clarity and easier unit testing.
   - You can easily extend `ChatMessageDto` or controller logic to support new features or message types.

**Example WebSocket usage (JavaScript):**

```javascript
stompClient.send("/app/send", {}, JSON.stringify({ username: "Alice", message: "You are awesome!" }));
```

**Example Controller snippet:**

```java
@PreAuthorize("isAuthenticated()")
@MessageMapping("/send")
@SendTo("/topic/messages")
public ChatMessageDto send(ChatMessageDto message) {
    message.setTimestamp(Instant.now().toString());
    return message;
}
```

---

## UI Implementation

- **Entry Point:**  
  Open [`chat.html`](src/main/resources/static/chat.html) in your browser.
- **Features:**
   - Enter your name and affirmation; send to live chat.
   - View all live messages, with your messages highlighted.
   - See the list of affirmations you've sent.
   - Modern, mobile-friendly design.

---

## Quickstart

### Prerequisites

- Java 17+
- Maven 3.8+

### Setup

1. **Clone the repository**
   ```sh
   git clone https://github.com/yourusername/affirmations.git
   cd affirmations
   ```

2. **Run the app**
   ```sh
   mvn spring-boot:run
   ```

3. **Access API documentation**  
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

4. **H2 Database Console**  
   [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:testdb`
   - User: `sa`
   - Password: (blank)

5. **Access Live Chat:**  
   [http://localhost:8080/chat.html](http://localhost:8080/chat.html)

6. **Test WebSocket:**  
   Open multiple browser windows/tabs and send affirmations in real time.

---

## Docker

You can run the application using Docker for easier deployment.

### Build the Docker image

```sh
docker build -t affirmations-app .
```

### Run the container

```sh
docker run -p 8080:8080 affirmations-app
```

The app will be available at [http://localhost:8080](http://localhost:8080).

#### Example `Dockerfile` (placed in the project root):

```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Demo Spring Boot App

### Register & Login (get JWT)

1. Register:
    ```sh
    curl -X POST "http://localhost:8080/api/auth/register?username=user1&password=pass123"
    ```
2. Login (returns JWT):
    ```sh
    curl -X POST "http://localhost:8080/api/auth/login?username=user1&password=pass123"
    ```

### Use JWT

For protected endpoints, add this HTTP header:
```
Authorization: Bearer <your-token-here>
```

### Get Affirmations

```sh
curl http://localhost:8080/api/affirmations
```

### Add Daily Intention

```sh
curl -X POST "http://localhost:8080/api/intentions" \
 -H "Authorization: Bearer <token>" \
 -H "Content-Type: application/json" \
 -d '{"intentionText":"Go for a walk","date":"2025-07-31"}'
```

### Get Help Resources

```sh
curl http://localhost:8080/api/help/resources
```

---

## Running Tests

```sh
mvn test
```

---

## API Reference

See [Swagger UI](http://localhost:8080/swagger-ui.html) for full interactive documentation.

---

## Contributing

PRs welcome! Please add tests and documentation for new features.

---

**Made with Spring Boot 3, JWT, H2, Docker and love.**