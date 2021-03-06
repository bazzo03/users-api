package co.s4n.infrastructure.kafka

import java.util.Properties

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{ KafkaProducer, ProducerRecord }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

object Producer extends LazyLogging {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("client.id", "KafkaProducerExample")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val topic: String = "UsersTopic"

  def produceKafka(msg: String): Future[Unit] = Future {

    val producer = new KafkaProducer[String, String](props)

    val data = new ProducerRecord[String, String](topic, msg)

    producer.send(data, (_, _) => {
      logger.info("Message sent to Kafka : " + msg)
    })
    producer.close()
  }

}
