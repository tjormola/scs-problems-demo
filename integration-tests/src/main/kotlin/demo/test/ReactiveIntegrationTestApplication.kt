package demo.test

import demo.processor.ReactiveProcessorApplication
import demo.processor.ReactiveProcessorConfiguration
import demo.source.HttpSourceApplication
import demo.source.HttpSourceConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration
import org.springframework.context.annotation.Import

@SpringBootApplication(exclude = arrayOf(TestSupportBinderAutoConfiguration::class))
@Import(value = arrayOf(HttpSourceConfiguration::class, ReactiveProcessorConfiguration::class))
class ReactiveIntegrationTestApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder().sources(HttpSourceApplication::class.java, ReactiveProcessorApplication::class.java)
            .run(*args)
}
