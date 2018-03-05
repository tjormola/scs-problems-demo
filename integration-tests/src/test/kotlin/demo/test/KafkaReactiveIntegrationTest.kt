package demo.test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = arrayOf(ReactiveIntegrationTestApplication::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("kafka")
class KafkaReactiveIntegrationTest : AbstractKafkaIntegrationTest()