package watermarks

import models.Workers
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

/**
  * Custom timestamp generator.
  */
class StringWatermarkGenerator extends AssignerWithPunctuatedWatermarks[Workers] {
  override def extractTimestamp(element: Workers, previousElementTimestamp: Long): Long = {
    element.elapsedTime
  }

  override def checkAndGetNextWatermark(lastElement: Workers, extractedTimestamp: Long): Watermark = {
    new  Watermark(extractedTimestamp)
  }
}