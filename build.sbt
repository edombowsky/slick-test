
organization := "com.abb"

name := "slick-test"

version := "1.0"

scalaVersion in ThisBuild := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

resolvers += Resolver.bintrayRepo("pathikrit", "maven")
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
resolvers += "Ty commercial" at "https://dl.bintray.com/typesafe/commercial-maven-releases/com/typesafe/slick/slick-extensions_2.11/3.1.0/"

libraryDependencies ++= Seq(
  // "com.typesafe.slick"         %% "slick"             % "3.1.0",
  // "com.typesafe.slick"         %% "slick-extensions"  % "3.1.0",
  // "com.typesafe.slick"         %% "slick-codegen"     % "3.1.0",
  "org.suecarter"              %% "freeslick"         % "3.1.1.1",
  "org.slf4s"                  %% "slf4s-api"         % "1.7.13",
  "org.scalatest"              %% "scalatest"         % "3.0.0"    % "test",
  "com.github.pathikrit"       %% "better-files"      % "2.15.0",
  "com.typesafe.scala-logging" %% "scala-logging"     % "3.4.0",
  "ch.qos.logback"             %  "logback-classic"   % "1.1.7"
)
