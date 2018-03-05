package demo

import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.messaging.MessageChannel

const val TEST_PROCESSOR_OUTPUT_CHANNEL_NAME = "test"

interface TestProcessor: Sink {
  @Output(TEST_PROCESSOR_OUTPUT_CHANNEL_NAME)
  fun test(): MessageChannel
}
