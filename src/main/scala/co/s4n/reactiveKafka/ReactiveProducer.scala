package co.s4n.reactiveKafka

import akka.actor.ActorSystem
import akka.kafka.ProducerMessage
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringSerializer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import scala.concurrent.Future
import akka.Done
import scala.util.{ Failure, Success }

/**
 * Created by seven4n on 3/04/17.
 */
object ReactiveProducer {

  val system = ActorSystem("example")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer.create(system)

  val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers("localhost:9092")
  val kafkaProducer = producerSettings.createKafkaProducer()

  def produce(msg: String): Unit = {
    val done = Source(1 to 1)
      .map(_.toString)
      .map { elem =>
        println("\n" + msg);
        new ProducerRecord[Array[Byte], String]("UsersTopic", msg)
      }
      .runWith(Producer.plainSink(producerSettings, kafkaProducer))
    // #plainSinkWithProducer

    //    terminateWhenDone(done)
  }

  def terminateWhenDone(result: Future[Done]): Unit = {
    result.onComplete {
      case Failure(e) =>
        system.log.error(e, e.getMessage)
        system.terminate()
      case Success(_) => system.terminate()
    }
  }

}
