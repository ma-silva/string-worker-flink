package workers

import functions.{StringMapFunction, StringSinkFunction}
import models.Workers
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.datastream.DataStreamSink
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala._
import watermarks.StringWatermarkGenerator

/**
  * A string worker class.
  * @param source Source string.
  * @param env Flink StreamExecutionEnvironment.
  */
class StringWorker(f: String => String, sinkFunction: SinkFunction[Workers] = new StringSinkFunction, source: String)(implicit env: StreamExecutionEnvironment) extends Worker[Workers]{

  lazy val data: DataStream[String] = env.fromElements(source)
  private val name: String = "worker"

  {
    require(source!=null)
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    env.getConfig.disableSysoutLogging()
  }

  /**
    * Spawn a data stream sink worker.
    * @return Int => DataStreamSink[Workers].
    */
  private def spawn: Int => DataStreamSink[Workers] = (x: Int) =>
    transform(new StringMapFunction(s"$name-$x", f), new StringWatermarkGenerator)
        .addSink(sinkFunction)

  /**
    * Execute the data stream transformation.
    * @param num Define the number of workers or data stream.
    */
  override def run(num: Int = 1): Unit = (1 to num).foreach(spawn)

  /**
    *
    * @param map A map function.
    * @param watermark Define a watermark.
    * @return The computed stream
    */
  override def transform(map: RichMapFunction[String, Workers], watermark: AssignerWithPunctuatedWatermarks[Workers]): DataStream[Workers] =
    data
      .map(map)
      .assignTimestampsAndWatermarks(watermark)
}