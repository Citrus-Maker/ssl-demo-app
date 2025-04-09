# Spring Boot SSL Demo

This project demonstrates how to configure SSL/TLS in a Spring Boot application using keystores and truststores. It provides a simple and clear example of setting up HTTPS in a Spring Boot 3.4.4 application.

## Features

- HTTPS configuration with custom keystores
- Truststore configuration for client certificate validation (optional)
- Environment-based configuration
- Simple REST endpoints for testing

## Prerequisites

- Java 21 or later
- Maven 3.6+
- (Optional) OpenSSL for testing

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/ssl-demo.git
cd ssl-demo
```

### Generate Keystores and Truststores

Generate a keystore and truststore for local development:

```bash
# Navigate to resources directory
cd src/main/resources

# Generate keystore
keytool -genkeypair -alias ssl-demo-app -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ssl-demo-app.p12 -validity 3650 -storepass password -keypass password

# Export certificate
keytool -exportcert -alias ssl-demo-app -file ssl-demo-app.crt -keystore ssl-demo-app.p12 -storepass password

# Create truststore and import certificate
keytool -importcert -alias ssl-demo-app -file ssl-demo-app.crt -keystore ssl-demo-app-truststore.p12 -storetype PKCS12 -storepass password -noprompt
```

### Configure Local Environment

Create a file named `application-local.yml` in `src/main/resources/` with the following content:

```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:ssl-demo-app.p12
    key-store-password: password
    key-store-type: PKCS12
    key-alias: ssl-demo-app
    trust-store: classpath:ssl-demo-app-truststore.p12
    trust-store-password: password
    trust-store-type: PKCS12
```

### Run the Application

```bash
./mvnw spring-boot:run
```

Access the application at https://localhost:8443/

Note: Since we're using a self-signed certificate, your browser will show a security warning. You can proceed by accepting the risk.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── ssldemo/
│   │               ├── SslDemoApplication.java
│   │               ├── controller/
│   │               │   └── HelloController.java
│   │               └── config/
│   │                   └── SecurityConfig.java
│   └── resources/
│       ├── application.yml         # Shared configuration with placeholders
│       └── application-local.yml   # Local configuration (git-ignored)
```

## SSL Configuration Options

The application supports the following SSL configuration options:

### Basic SSL (HTTPS)

```yaml
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: password
    key-store-type: PKCS12
    key-alias: myalias
```

### Mutual TLS (Client Certificate Authentication)

```yaml
server:
  ssl:
    client-auth: need  # Options: need, want, none
```

## Environment Variables

You can customize the application using the following environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | The port to run the server on | `8443` |
| `SSL_KEYSTORE_NAME` | Keystore file name | `ssl-demo-app.p12` |
| `SSL_KEYSTORE_PASSWORD` | Keystore password | `changeit` |
| `SSL_KEYSTORE_TYPE` | Keystore type | `PKCS12` |
| `SSL_KEY_ALIAS` | Key alias in keystore | `ssl-demo-app` |

## Testing the SSL Connection

You can test the SSL connection using curl:

```bash
# Skip certificate validation
curl -k https://localhost:8443/

# With certificate validation (if you have configured your truststore)
curl --cacert ssl-demo-app.crt https://localhost:8443/
```

## Endpoints

- `GET /` - Returns a simple greeting message
- `GET /info` - Returns information about the SSL connection

## Additional Resources

- [Spring Boot SSL Configuration Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web.security.ssl)
- [Java Keytool Documentation](https://docs.oracle.com/en/java/javase/21/docs/specs/man/keytool.html)

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Copyright

Copyright © 2025 Benjamin Blocksom. All rights reserved.