import play.Project._

name := "etn-play"

version := "0.1"

playScalaSettings

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0" % "test"
)