# Managing Kafka queue configuration

## Create a Kafka queue configuration

Let's start by defining a single queue with a simple consumer and producer:

```java
import io.orkes.conductor.client.model.event.QueueConfiguration;
import io.orkes.conductor.client.model.event.QueueWorkerConfiguration;
import io.orkes.conductor.client.model.event.kafka.KafkaConfiguration;
import io.orkes.conductor.client.model.event.kafka.KafkaConsumer;
import io.orkes.conductor.client.model.event.kafka.KafkaProducer;

private static final String KAFKA_QUEUE_TOPIC_NAME = "test_sdk_java_kafka_queue_name";
private static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG = "localhost:9092";

QueueConfiguration getKafkaQueueConfiguration() throws Exception {
    return new KafkaConfiguration(KAFKA_QUEUE_TOPIC_NAME)
            .withConsumer(getKafkaConsumer())
            .withProducer(getKafkaProducer());
}

QueueWorkerConfiguration getKafkaConsumer() throws Exception {
    return new KafkaConsumer(KAFKA_BOOTSTRAP_SERVERS_CONFIG)
            // 1 second, instead of default 2 seconds
            .withConfiguration(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "1000");
}

QueueWorkerConfiguration getKafkaProducer() throws Exception {
    return new KafkaProducer(KAFKA_BOOTSTRAP_SERVERS_CONFIG)
            // send messages in chunks of 1024 bytes, instead of default every new data
            .withConfiguration(ProducerConfig.BATCH_SIZE_CONFIG, "1024");
}
```

Now, the Conductor server must receive this brand new configuration:

```java
import io.orkes.conductor.client.EventClient;
import io.orkes.conductor.client.OrkesClients;

void registerKafkaQueueConfiguration(OrkesClients orkesClients) throws Exception {
    QueueConfiguration queueConfiguration = getKafkaQueueConfiguration();
    EventClient eventClient = orkesClients.getEventClient();
    eventClient.putQueueConfig(queueConfiguration);
}
```

## APIs

Currently supported APIs are described at the `EventClient` interface:

```java
String getQueueConfig(QueueConfiguration queueConfiguration);

void deleteQueueConfig(QueueConfiguration queueConfiguration);

void putQueueConfig(QueueConfiguration queueConfiguration) throws Exception;
```

## Example

You can check out our integration tests with all APIs usage: [link](https://github.com/orkes-io/orkes-conductor-client/blob/main/src/test/java/io/orkes/conductor/client/api/EventClientTests.java#L74)
