package demo.processor

import demo.TEST_PROCESSOR_OUTPUT_CHANNEL_NAME
import demo.TestProcessor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux

@SpringBootApplication
class ReactiveProcessorApplication

fun main(args: Array<String>) {
    runApplication<ReactiveProcessorApplication>(*args)
}

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
