# Kafka-Driven User Notification System

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.9.1-blue.svg)](https://kafka.apache.org/)
[![Kafbat](https://img.shields.io/badge/Kafbat-UI-lightgrey.svg)](https://www.kafbat.io/)
[![Confluent](https://img.shields.io/badge/Confluent-Platform-blueviolet.svg)](https://www.confluent.io/)


A comprehensive demonstration of Apache Kafka integration with Spring Boot, covering everything from basic producer-consumer patterns to advanced schema registry implementations.

## 🚀 Overview

This project demonstrates the integration of Apache Kafka with Spring Boot to build robust, event-driven applications. It showcases various Kafka features including:

- **Producer-Consumer Architecture**: Implementing reliable message publishing and consumption
- **Event-Driven Communication**: Building loosely coupled microservices
- **Schema Evolution**: Using Confluent Schema Registry for data governance
- **Real-time Processing**: Handling high-throughput message streams

## ✨ Features

### Core Features
- ✅ **Kafka Producer** - Send messages from user-service to Kafka topics
- ✅ **Kafka Consumer** - Consume messages with notification-service using different strategies
- ✅ **Topic Management** - Programmatic topic creation and configuration
- ✅ **Error Handling** - Robust error handling and retry mechanisms
- ✅ **Serialization** - JSON, Avro, and custom serialization support

### Advanced Features
- 🔧 **Schema Registry Integration** - Confluent Schema Registry support
- 📊 **Message Monitoring** - Comprehensive metrics and monitoring
- 🛡️ **Security Configuration** - SSL/SASL authentication setup
- 🔄 **Batch Processing** - Efficient batch message processing
- ⚡ **Performance Tuning** - Optimized configurations for different use cases

## 📋 Prerequisites

Before running this project, ensure you have:

- **Java 17+** - Required for Spring Boot 3.x
- **Apache Kafka 3.9.1** - Message broker
- **Maven 3.6+** - Build tool
- **Docker** (Optional) - For containerized setup
- **Git** - Version control

### System Requirements
- **Memory**: Minimum 4GB RAM
- **Storage**: At least 2GB free space
- **Network**: Kafka default ports (9092, 2181)

## 📚 Learning Resources

## Documentation & Guides
### 📖 **PDF Notes**: [Comprehensive Kafka concepts and Spring Boot integration](https://github.com/user-attachments/files/20737401/Apache.Kafka.pdf)
### 📘 **Official Docs**: [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
### 🐘 **Apache Kafka**: [Official Kafka Documentation](https://kafka.apache.org/documentation/)


## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/ARONAGENT/Kafka_SpringBoot.git
cd Kafka_SpringBoot
```

### 2. Start Kafka Server
```bash
# Generate cluster UUID
.\bin\windows\kafka-storage.bat random-uuid

# Format storage (replace UUID with generated one)
.\bin\windows\kafka-storage.bat format -t aW47Xy3ASHGaGkZgOqOaNA -c .\config\kraft\server.properties

# Start Kafka server
.\bin\windows\kafka-server-start.bat .\config\kraft\server.properties
```

### 3. Create Kafka Topic
```bash
# Create topic for events
kafka-topics.bat --create --topic rohan-events --bootstrap-server localhost:9092

# Verify topic creation
kafka-topics.bat --describe --topic rohan-events --bootstrap-server localhost:9092
```

### 4. Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

## ⚙️ Kafka Setup

### Manual Setup (Windows)

#### Step 1: Start Kafka with KRaft Mode
```bash
# Navigate to Kafka directory
cd kafka_2.13-3.9.1

# Generate unique cluster ID
.\bin\windows\kafka-storage.bat random-uuid
# Output: aW47Xy3ASHGaGkZgOqOaNA

# Format the storage directory
.\bin\windows\kafka-storage.bat format -t aW47Xy3ASHGaGkZgOqOaNA -c .\config\kraft\server.properties

# Start the Kafka server
.\bin\windows\kafka-server-start.bat .\config\kraft\server.properties
```

#### Step 2: Topic Management
```bash
# Create a new topic
kafka-topics.bat --create --topic rohan-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# List all topics
kafka-topics.bat --list --bootstrap-server localhost:9092

# Describe topic details
kafka-topics.bat --describe --topic rohan-events --bootstrap-server localhost:9092

# Delete a topic (if needed)
kafka-topics.bat --delete --topic rohan-events --bootstrap-server localhost:9092
```

### Docker Setup (Alternative)
```yaml
# docker-compose.yml
---
services:

  broker:
    image: confluentinc/cp-kafka:7.7.1
    hostname: broker
    container_name: broker
    ports:
      - "9092:9092"
      - "9101:9101"
    environment:
      KAFKA_NODE_ID: 1
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: 'CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT'
      KAFKA_ADVERTISED_LISTENERS: 'PLAINTEXT://broker:29092,PLAINTEXT_HOST://localhost:9092'
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_JMX_PORT: 9101
      KAFKA_JMX_HOSTNAME: localhost
      KAFKA_PROCESS_ROLES: 'broker,controller'
      KAFKA_CONTROLLER_QUORUM_VOTERS: '1@broker:29093'
      KAFKA_LISTENERS: 'PLAINTEXT://broker:29092,CONTROLLER://broker:29093,PLAINTEXT_HOST://0.0.0.0:9092'
      KAFKA_INTER_BROKER_LISTENER_NAME: 'PLAINTEXT'
      KAFKA_CONTROLLER_LISTENER_NAMES: 'CONTROLLER'
      KAFKA_LOG_DIRS: '/tmp/kraft-combined-logs'
      # Replace CLUSTER_ID with a unique base64 UUID using "bin/kafka-storage.sh random-uuid"
      # See https://docs.confluent.io/kafka/operations-tools/kafka-tools.html#kafka-storage-sh
      CLUSTER_ID: 'MkU3OEVBNTcwNTJENDM2Qk'

  schema-registry:
    image: confluentinc/cp-schema-registry:7.7.1
    hostname: schema-registry
    container_name: schema-registry
    depends_on:
      - broker
    ports:
      - "8081:8081"
    environment:
      SCHEMA_REGISTRY_HOST_NAME: schema-registry
      SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS: 'broker:29092'
      SCHEMA_REGISTRY_LISTENERS: http://0.0.0.0:8081

  connect:
    image: cnfldemos/cp-server-connect-datagen:0.6.4-7.6.0
    hostname: connect
    container_name: connect
    depends_on:
      - broker
      - schema-registry
    ports:
      - "8083:8083"
    environment:
      CONNECT_BOOTSTRAP_SERVERS: 'broker:29092'
      CONNECT_REST_ADVERTISED_HOST_NAME: connect
      CONNECT_GROUP_ID: compose-connect-group
      CONNECT_CONFIG_STORAGE_TOPIC: docker-connect-configs
      CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_OFFSET_FLUSH_INTERVAL_MS: 10000
      CONNECT_OFFSET_STORAGE_TOPIC: docker-connect-offsets
      CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_STATUS_STORAGE_TOPIC: docker-connect-status
      CONNECT_STATUS_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_KEY_CONVERTER: org.apache.kafka.connect.storage.StringConverter
      CONNECT_VALUE_CONVERTER: io.confluent.connect.avro.AvroConverter
      CONNECT_VALUE_CONVERTER_SCHEMA_REGISTRY_URL: http://schema-registry:8081
      # CLASSPATH required due to CC-2422
      CLASSPATH: /usr/share/java/monitoring-interceptors/monitoring-interceptors-7.7.1.jar
      CONNECT_PRODUCER_INTERCEPTOR_CLASSES: "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor"
      CONNECT_CONSUMER_INTERCEPTOR_CLASSES: "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor"
      CONNECT_PLUGIN_PATH: "/usr/share/java,/usr/share/confluent-hub-components"

  control-center:
    image: confluentinc/cp-enterprise-control-center:7.7.1
    hostname: control-center
    container_name: control-center
    depends_on:
      - broker
      - schema-registry
      - connect
      - ksqldb-server
    ports:
      - "9021:9021"
    environment:
      CONTROL_CENTER_BOOTSTRAP_SERVERS: 'broker:29092'
      CONTROL_CENTER_CONNECT_CONNECT-DEFAULT_CLUSTER: 'connect:8083'
      CONTROL_CENTER_CONNECT_HEALTHCHECK_ENDPOINT: '/connectors'
      CONTROL_CENTER_KSQL_KSQLDB1_URL: "http://ksqldb-server:8088"
      CONTROL_CENTER_KSQL_KSQLDB1_ADVERTISED_URL: "http://localhost:8088"
      CONTROL_CENTER_SCHEMA_REGISTRY_URL: "http://schema-registry:8081"
      CONTROL_CENTER_REPLICATION_FACTOR: 1
      CONTROL_CENTER_INTERNAL_TOPICS_PARTITIONS: 1
      CONTROL_CENTER_MONITORING_INTERCEPTOR_TOPIC_PARTITIONS: 1
      CONFLUENT_METRICS_TOPIC_REPLICATION: 1
      PORT: 9021

  ksqldb-server:
    image: confluentinc/cp-ksqldb-server:7.7.1
    hostname: ksqldb-server
    container_name: ksqldb-server
    depends_on:
      - broker
      - connect
    ports:
      - "8088:8088"
    environment:
      KSQL_CONFIG_DIR: "/etc/ksql"
      KSQL_BOOTSTRAP_SERVERS: "broker:29092"
      KSQL_HOST_NAME: ksqldb-server
      KSQL_LISTENERS: "http://0.0.0.0:8088"
      KSQL_CACHE_MAX_BYTES_BUFFERING: 0
      KSQL_KSQL_SCHEMA_REGISTRY_URL: "http://schema-registry:8081"
      KSQL_PRODUCER_INTERCEPTOR_CLASSES: "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor"
      KSQL_CONSUMER_INTERCEPTOR_CLASSES: "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor"
      KSQL_KSQL_CONNECT_URL: "http://connect:8083"
      KSQL_KSQL_LOGGING_PROCESSING_TOPIC_REPLICATION_FACTOR: 1
      KSQL_KSQL_LOGGING_PROCESSING_TOPIC_AUTO_CREATE: 'true'
      KSQL_KSQL_LOGGING_PROCESSING_STREAM_AUTO_CREATE: 'true'


```

## 🔧 Configuration

### Application Configuration (`application.yml`)
```yaml
# For Consumer
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: ${spring.application.name}
      key-deserializer: org.apache.kafka.common.serialization.LongDeserializer
      value-deserializer: io.confluent.kafka.serializers.KafkaAvroDeserializer
      properties:
        schema.registry.url: http://127.0.0.1:8081
        specific.avro.reader: true
        spring.json.trusted.packages: com.springJourneyMax.*

# For Producer
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.LongSerializer
      value-serializer: io.confluent.kafka.serializers.KafkaAvroSerializer
      properties:
        schema.registry.url: http://127.0.0.1:8081

kafka:
  topic:
    user-random-topic: user-random-topic
    user-created-topic: user-created-topic

```

### Producer Configuration
```java
@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.user-random-topic}")
    private  String KAFKA_RANDOM_USER_TOPIC;

    @Value("${kafka.topic.user-created-topic}")
    private  String KAFKA_CREATED_USER_TOPIC;

    @Bean
    public NewTopic userRandomTopic(){
        return new NewTopic(KAFKA_RANDOM_USER_TOPIC,3, (short) 1);
    }

    @Bean
    public NewTopic userCreatedTopic(){
        return new NewTopic(KAFKA_CREATED_USER_TOPIC,3, (short) 1);
    }
}

```

### Consumer Configuration
```java
# no  need to config for smaller consumers....
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "event-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.kafka.model");
        return new DefaultKafkaConsumerFactory<>(props);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
```

## 💻 Usage Examples

### 1. Sending Messages (Producer)
```java
# Service code
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    @Value("${kafka.topic.user-created-topic}")
    private String KAFKA_CREATED_USER_TOPIC;

    public void createUser(UserDto userDto){
       UserEntity userEntity= modelMapper.map(userDto, UserEntity.class);
       UserEntity userEntity1=  userRepository.save(userEntity);

       UserCreatedEvent userCreatedEvent=modelMapper.map(userEntity1,UserCreatedEvent.class);
       kafkaTemplate.send(KAFKA_CREATED_USER_TOPIC,userCreatedEvent.getId(),userCreatedEvent);
    }

}

# Controller code
@RestController
@RequestMapping("/createUser")
@RequiredArgsConstructor
public class UserCreatedController {

    private final UserServices userServices;

    @PostMapping("/add")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        userServices.createUser(userDto);
        return ResponseEntity.ok("user is created");
    }
}

```

### 2. Consuming Messages (Consumer)
```java
@Service
@Slf4j
public class UserCreatedConsumer {

    @KafkaListener(topics ="user-created-topic")
    public void listenUserCreatedEvent(UserCreatedEvent userCreatedEvent){
        log.info("listenUserCreatedEvent {}",userCreatedEvent);

    }
}

```

### 3. Event Model
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private Long id;
    private String name;
    private String emaiil;
}
```

## 🔬 Advanced Features

### Schema Registry Integration

#### 1. Add Confluent Dependencies
```xml
<!----Repository---->
<repositories>
		<repository>
			<id>confluent</id>
			<url>https://packages.confluent.io/maven/</url>
		</repository>
	</repositories>

<!-----dependencies------->
<!-- Avro core -->
		<dependency>
			<groupId>org.apache.avro</groupId>
			<artifactId>avro</artifactId>
			<version>1.11.0</version>
		</dependency>

		<!-- Kafka Avro Serializer (for producing/consuming Avro messages) -->
		<dependency>
			<groupId>io.confluent</groupId>
			<artifactId>kafka-avro-serializer</artifactId>
			<version>7.4.0</version>
		</dependency>

		<!-- Confluent Schema Registry Client -->
		<dependency>
			<groupId>io.confluent</groupId>
			<artifactId>kafka-schema-registry-client</artifactId>
			<version>7.4.0</version>
		</dependency>

<!-------Plugins------>
          <plugin>
						<groupId>org.apache.avro</groupId>
						<artifactId>avro-maven-plugin</artifactId>
						<version>1.8.2</version>
						<executions>
							<execution>
								<id>schemas</id>
								<phase>generate-sources</phase>
								<goals>
									<goal>schema</goal>
								</goals>
								<configuration>
									<sourceDirectory>${project.basedir}/src/main/resources/</sourceDirectory>
									<outputDirectory>${project.basedir}/src/main/java</outputDirectory>
								</configuration>
							</execution>
						</executions>
					</plugin>
```

#### 2. Avro Schema Definition
```json
{
  "type": "record",
  "name": "UserCreatedEvent",
  "namespace": "com.springJourneyMax.events",
  "fields": [
    {
      "name": "id",
      "type": "long"
    },
    {
      "name": "name",
      "type": "string"
    },
    {
      "name": "email",
      "type": "string"
    }
  ]
}

```

#### 3. Schema Registry Configuration
```yaml
spring:
  kafka:
    properties:
      schema.registry.url: http://localhost:8081
      specific.avro.reader: true
```

## 📊 Monitoring & Visualization

### KafBat UI Setup
KafBat UI provides a web-based interface for monitoring Kafka clusters, topics, and messages.

#### Running KafBat UI
```bash
# Download KafBat UI JAR
# Create application-local.yml configuration

# Run KafBat UI
java -Dspring.config.additional-location="D:\kafka_2.13-3.9.1\application-local.yml" \
     --add-opens java.rmi/javax.rmi.ssl=ALL-UNNAMED \
     -jar kafbat-ui-v1.0.0.jar
```

#### KafBat Configuration (`application-local.yml`)
```yaml
logging:
  level:
    root: INFO
    io.kafbat.ui: DEBUG

spring:
  jmx:
    enabled: true

kafka:
  clusters:
    - name: local
      bootstrapServers: localhost:9092
```

### Accessing KafBat UI
- **URL**: http://localhost:8080
- **Features**:
  - Topic management and monitoring
  - Message browsing and searching  
  - Consumer group monitoring
  - Schema registry integration
  - Cluster health metrics

## 📡 API Documentation

### REST Endpoints

#### Send Event
```http
POST /users/add/
Content-Type: application/json

{
  "id": "1",
  "name": "Rohan",
  "email": "rohan12@gmail.com"
}
```

#### Batch Send Events
```http
POST /api/events/batch
Content-Type: String
```
```
messgae send
```


#### Get Topic Information
```http
GET /api/topics/rohan-events
```

Response:
```json
{
  "name": "rohan-events",
  "partitions": 3,
  "replicationFactor": 1,
  "configs": {
    "cleanup.policy": "delete",
    "retention.ms": "604800000"
  }
}
```

## 🛠️ Troubleshooting

### Common Issues

#### 1. Connection Refused Error
```
org.apache.kafka.common.errors.TimeoutException: Timed out waiting for a node assignment
```
**Solution**: Ensure Kafka server is running on localhost:9092

#### 2. Serialization Errors
```
org.apache.kafka.common.errors.SerializationException: Error serializing Avro message
```
**Solution**: Check schema registry is running and schemas are registered

#### 3. Consumer Lag Issues
```
Consumer group 'event-consumer-group' has lag of 1000 messages
```
**Solution**: Increase consumer concurrency or optimize processing logic

### Debugging Tips

#### Enable Debug Logging
```yaml
logging:
  level:
    org.apache.kafka: DEBUG
    org.springframework.kafka: DEBUG
```

#### Monitor Consumer Groups
```bash
# List consumer groups
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list

# Describe consumer group
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group event-consumer-group
```

#### Check Topic Messages
```bash
# Consume from beginning
kafka-console-consumer.bat --topic rohan-events --from-beginning --bootstrap-server localhost:9092

# Consume with key
kafka-console-consumer.bat --topic rohan-events --from-beginning --bootstrap-server localhost:9092 --property print.key=true
```


## 📸 Screenshots

### 1. Kafbat UI Overview  
![1. Kafbat UI](https://github.com/user-attachments/assets/767bd4c4-2ff8-4e3c-8a55-441aa357347b)

---

### 2. Kafka Events View in Kafbat  
![2. Kafka Events View](https://github.com/user-attachments/assets/6a14ace3-2ab7-4828-aa44-c3b731e27422)

---

### 3. Message Published to Topic  
![3. Message Written](https://github.com/user-attachments/assets/10873fd5-b7be-4f45-9f35-9a45953eb2e8)

---

### 4. Detailed Info of an Event  
![4. Event Details](https://github.com/user-attachments/assets/3ac85046-b65c-4a13-93e3-9757fb56ef8b)

---

### 5. Notification Service Clients Listening to `user-random-topic`  
![5. Notification Clients](https://github.com/user-attachments/assets/a56e8078-6874-4407-b2f5-3d19a91f7db8)

---

### 6. Kafka Partition Assignment Based on Clients  
![6. Partition Assignment](https://github.com/user-attachments/assets/0e4b8c99-5ae8-4eca-8a61-72b419beaa3e)

---

### 7. Data Flow from User to Kafka Visualized in Confluent  
![7. User to Kafka Flow](https://github.com/user-attachments/assets/434a40d2-da1e-4ed9-99d5-3a58713e9ae3)


## 🎥 Demo Videos

Step-by-step implementation walkthroughs:

### 1. Kafka Setup & Events Writing  
[Watch Video ▶️](https://github.com/user-attachments/assets/7507d97e-9384-4eaf-9fed-527a61c18219)


---

### 2.Sending complex msg json format 
[Watch Video ▶️](https://github.com/user-attachments/assets/f7888bd8-45c9-4026-977d-38410fc380ba)

---

### 3.Sending 1000 msg and kafka internally do partitions with keys & hashed function  
[Watch Video ▶️](https://github.com/user-attachments/assets/313e33b4-48f5-4ef6-93d0-c3a89bbf669f)

---

### 4. Send msg to kafka via user service
[Watch Video ▶️](https://github.com/user-attachments/assets/c59ef9b7-ddc2-4ed8-b65e-448ed9eddb39)


---

### 5. Consumer-notification-server reads msg
[Watch Video ▶️](https://github.com/user-attachments/assets/92ed6a86-cbd3-4386-95cd-c75d99c40375)

---

### 6. Viewing Events in Confluent Schema Registry  
[Watch Video ▶️](https://github.com/user-attachments/assets/82cdae94-6b7d-4729-8a1b-16c7ee883c17)

<br>



### Key Topics Covered
1. **Introduction to Kafka** - Core concepts and use cases
2. **Kafka Architecture** - Internal working and components
3. **Installation & Setup** - Local development environment
4. **Spring Boot Integration** - Configuration and implementation
5. **Advanced Features** - Schema registry, monitoring, security
6. **Best Practices** - Performance tuning and error handling

### Development Guidelines
- Follow Spring Boot best practices
- Add unit tests for new features
- Update documentation for API changes
- Use conventional commit messages

## 🔗 Useful Links
### 🏠 **Repository**: [Kafka_SpringBoot](https://github.com/ARONAGENT/Kafka_SpringBoot)  
### 🐛 **Fix Issues**: [Report Bug/Feature](https://github.com/ARONAGENT/Kafka_SpringBoot/issues)  
### 📊 **Kafbat UI**: [kafbat.io](https://www.kafbat.io/)  
### 🌐 **Confluent Platform**: [Confluent Docs](https://docs.confluent.io/)  

## 📧 Contact

For questions or support, please open an issue in the GitHub repository or reach out through the project's discussion board.

## 👨‍💻 Author

**Rohan Uke**  
Backend Developer | Java & Spring Security Expert

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/rohan-uke)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ARONAGENT)

---

**Happy Coding! 🚀**

*Built with ❤️ using Apache Kafka and Spring Boot*
