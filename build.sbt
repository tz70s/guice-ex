val commonSettings = Seq(
  name := "guice-ex",
  version := "0.1",
  scalaVersion := "2.12.6"
)

val guiceVersion = "4.2.0"
val guice = "com.google.inject" % "guice" % guiceVersion
val scalaTestVersion = "3.0.5"
val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % Test

val dependencies = Seq(guice, scalaTest)

lazy val `guice-ex` = (project in file("."))
  .settings(
    commonSettings,
    libraryDependencies ++= dependencies
  )