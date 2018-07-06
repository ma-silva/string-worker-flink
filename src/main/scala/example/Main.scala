package example

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import workers.StringWorker

object Main {
  def main(args: Array[String]): Unit = {

    implicit val env = StreamExecutionEnvironment.getExecutionEnvironment

    def upperCase(x: String): String = x.toUpperCase
    val worker = new StringWorker(upperCase, source = "King in the north!")

    worker.run(3)

    env.execute()
  }
}
