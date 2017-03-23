package co.s4n.infrastructure.kafka

import java.util.{ Date, Properties }

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{ KafkaProducer, ProducerRecord }

import scala.util.Random

object Producer extends LazyLogging {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("client.id", "KafkaProducerExample")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val rnd = new Random()
  val events = 1
  val topic: String = "UsersTopic"

  def produceKafka(msg: String): Unit = {

    val producer = new KafkaProducer[String, String](props)

    for (nEvents <- Range(0, events)) {
      val data = new ProducerRecord[String, String](topic, msg)

      producer.send(data, (_, _) => {
        logger.info("Message sent to Kafka : " + msg)
      })

    }
    producer.close()
  }

}
