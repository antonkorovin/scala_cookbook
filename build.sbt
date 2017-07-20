name := "scala_cookbook"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"
