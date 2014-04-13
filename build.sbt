import play.Project._

name := "etn-scw-play"

version := "0.1"

playScalaSettings

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "2.0" % "test",
	jdbc,
	"com.typesafe.slick" %% "slick" % "2.0.1",
	"mysql" % "mysql-connector-java" % "5.1.30"
	) 
