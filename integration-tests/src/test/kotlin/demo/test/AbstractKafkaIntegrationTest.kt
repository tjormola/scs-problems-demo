package demo.test

import org.springframework.kafka.test.rule.KafkaEmbedded

abstract class AbstractKafkaIntegrationTest : AbstractIntegrationTest() {

    private var embeddedKafka: KafkaEmbedded? = null

    init {
        embeddedKafka = KafkaEmbedded(1)
        embeddedKafka!!.before()
        System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka!!.brokersAsString)
        System.setProperty("spring.cloud.stream.kafka.binder.zkNodes", embeddedKafka!!.zookeeperConnectionString)
    }
}
