name := "scala_cookbook"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.0" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.5" % "test"
