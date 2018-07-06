package functions

import models.Workers
import org.apache.flink.api.common.functions.RichMapFunction

/**
  * A class that does the data transformation.
  * @param key Name of the worker.
  */
class StringMapFunction(key: String, f: String => String) extends RichMapFunction[String, Workers] {

  override def map(value: String): Workers = {
    val startTime = System.currentTimeMillis()
    val result = f(value)
    val lapsed = lapsedTime(startTime, System.currentTimeMillis())

    Workers(key, lapsed, result)
  }

  //Compute the lapsed time.
  private def lapsedTime(start: Long, end: Long) =  end - start
}
