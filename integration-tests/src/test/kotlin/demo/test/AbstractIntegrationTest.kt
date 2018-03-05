package demo.test

import demo.TestProcessor
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.test.binder.MessageCollector
import org.springframework.cloud.stream.test.matcher.MessageQueueMatcher
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

abstract class AbstractIntegrationTest {

	@Autowired
	private val webClient: WebTestClient? = null

	@Autowired
	private var messageCollector: MessageCollector? = null

	@Autowired
	private var testProcessor: TestProcessor? = null

	@Test
	fun requestDelivered() {
		webClient!!
				.post()
				.uri("/")
				.contentType(MediaType.APPLICATION_JSON)
				.syncBody(Foo(bar = "baz"))
				.exchange()
				.expectStatus().isOk
				.expectBody().isEmpty
		assertThat(messageCollector!!.forChannel(testProcessor!!.test()), MessageQueueMatcher.receivesPayloadThat(Is.`is`("{\"bar\":\"baz\"}")))
	}
}

data class Foo(val bar: String)
