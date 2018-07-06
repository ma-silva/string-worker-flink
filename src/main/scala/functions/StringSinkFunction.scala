package functions

import models.Workers
import org.apache.flink.streaming.api.functions.sink.SinkFunction

/**
  * The default sink function used to print the results.
  */
class StringSinkFunction extends SinkFunction[Workers]{

  override def invoke(value: Workers, context: SinkFunction.Context[_]): Unit = {
    println(value)
  }
}
