package demo.source

import org.reactivestreams.Publisher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.messaging.Source
import org.springframework.cloud.stream.reactive.StreamEmitter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ResolvableType
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.webflux.dsl.WebFlux
import org.springframework.messaging.Message

@SpringBootApplication
class HttpSourceApplication

fun main(args: Array<String>) {
    runApplication<HttpSourceApplication>(*args)
}

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
