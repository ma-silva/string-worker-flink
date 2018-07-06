name := "string-worker-flink"

version := "0.1"

scalaVersion := "2.11.12"

val flinkVersion = "1.4.0"
libraryDependencies ++= Seq(
  "org.apache.flink" % "flink-scala_2.11" % flinkVersion,
  "org.apache.flink" % "flink-streaming-scala_2.11" % flinkVersion,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)