This project demonstrates a problem I was having when using the Stream Cloud Stream Kafka Binder with a project written in Kotlin.

The following chain of SCS handlers will throw 

```
org.springframework.messaging.MessageDeliveryException: failed to send Message to channel 'input'; nested exception is java.lang.IllegalArgumentException: Unknown type for contentType header value: class org.springframework.kafka.support.DefaultKafkaHeaderMapper$NonTrustedHeaderType
```

when bound together using the SCS Kafka Binder. When using SCS Rabbit Binder, it'll work just fine. 

Source configuration defined in [`demo.source.HttpSourceApplication`](http-source/src/main/kotlin/demo/source/HttpSourceApplication.kt):
```
@EnableBinding(Source::class)
@Configuration
class HttpSourceConfiguration {
    @Bean
    @StreamEmitter
    @Output(Source.OUTPUT)
    fun publishIncomingJsonPayload(): Publisher<Message<String>> {
        return IntegrationFlows
                .from(WebFlux.inboundChannelAdapter("/")
                        .requestMapping { it
                                .methods(HttpMethod.POST)
                                .consumes(MediaType.APPLICATION_JSON_VALUE)
                        }
                        .requestPayloadType(ResolvableType.forClass(String::class.java))
                        .statusCodeFunction { HttpStatus.OK })
                .toReactivePublisher()
    }
}
```

Processor configuration defined in [`demo.processor.ReactiveProcessorApplication`](reactive-processor/src/main/kotlin/demo/processor/ReactiveProcessorApplication.kt):
```
@EnableBinding(TestProcessor::class)
@Configuration
class ReactiveProcessorConfiguration {
    @StreamListener
    @Output(TEST_PROCESSOR_OUTPUT_CHANNEL_NAME)
    fun processJsonMessage(@Input(Processor.INPUT) input: Flux<String>): Flux<String> {
        return input
                .map { it }
    }
}
``` 

Run the tests [`demo.test.RabbitReactiveIntegrationTest`](integration-tests/src/test/kotlin/demo/test/RabbitReactiveIntegrationTest.kt) and [`demo.test.KafkaReactiveIntegrationTest`](integration-tests/src/test/kotlin/demo/test/KafkaReactiveIntegrationTest.kt) under the [`integration-tests`](integration-tests) subproject to trigger the effect. The Kafka test will use an embedded Kafka instance and the Rabbit test expects the default configuration, i.e. Rabbit running on `localhost` with default `guest` credentials.

I wonder if Kotlin native types stuff is interfering here somehow. https://docs.spring.io/spring-kafka/docs/2.1.4.RELEASE/api/org/springframework/kafka/support/DefaultKafkaHeaderMapper.html#addTrustedPackages-java.lang.String...- refers to `java.util, java.lang` as being trusted packages by default. I haven't tried implementing this in plain Java, maybe it would work.
But as [Kotlin is now very much liked in Spring world](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0) and the same code works with the other official binder implementation, I think this should work with Kafka out of the box, too. Thus I consider this behaviour a bug.
