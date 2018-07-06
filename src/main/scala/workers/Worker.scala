package workers

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks
import org.apache.flink.streaming.api.scala.DataStream

trait Worker[T]{
  def run(num: Int): Unit
  protected def transform(map: RichMapFunction[String, T], watermark: AssignerWithPunctuatedWatermarks[T]): DataStream[T]
}
