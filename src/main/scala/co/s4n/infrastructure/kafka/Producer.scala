package co.s4n.infrastructure.kafka

import java.util.{ Date, Properties }

import org.apache.kafka.clients.producer.{ KafkaProducer, ProducerRecord }

import scala.util.Random

object Producer {

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
      val runtime = new Date().getTime()
      val data = new ProducerRecord[String, String](topic, msg)

      //async
      producer.send(data, (m, e) => {
        println("\n\n MESSAGE SENT TO KAFKA!!!!\n\n")
      })
      //sync
      //      producer.send(data)

    }
    producer.close()
  }

}
