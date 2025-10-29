```markdown
# Notification Service (Spring Boot)

This module implements a simple notification service providing:
- Persistence of notifications (H2/JPA)
- Sending EMAIL notifications via JavaMailSender
- REST endpoints to send and query notifications

Endpoints:
- POST /api/notifications
  - Body: { "recipient": "user@example.com", "type": "EMAIL", "subject": "Hi", "body": "Hello" }
- GET /api/notifications/{id}

Configuration:
- application.yml contains SMTP placeholders (spring.mail.*). Replace with your SMTP credentials.
- Uses an in-memory H2 DB by default. Change datasource to use Postgres/MySQL in production.

Run:
- mvn spring-boot:run

Example curl:
```bash
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"recipient":"test@example.com","type":"EMAIL","subject":"Hi","body":"This is a test"}'
```

Notes & Extensions:
- SMS and PUSH types are left as placeholders; integrate Twilio, Firebase, or your provider in NotificationServiceImpl.
- Add async/queue (e.g., RabbitMQ, Kafka) for higher throughput and retries.
- Add authentication/authorization for endpoints if exposing publicly.
```