name := """PA_Recommender"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.mongodb.morphia" % "morphia" % "0.111"

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "2.11.0"

libraryDependencies += "org.apache.mahout" % "mahout-core" % "0.9"
//libraryDependencies += "org.apache.mahout" % "mahout-math" % "0.9"
libraryDependencies += "org.apache.mahout" % "mahout-integration" % "0.9"
//libraryDependencies += "org.apache.mahout" % "mahout-collections" % "1.0"

libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % "2.5.3"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
