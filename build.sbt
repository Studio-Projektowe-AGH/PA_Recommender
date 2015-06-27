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

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator