scalaVersion in ThisBuild := "2.12.0"

lazy val shop =
  project.in(file(".")).enablePlugins(AutomateHeaderPlugin, GitVersioning)

libraryDependencies ++= Vector(
  Library.scalaTest % "test"
)

initialCommands := """|import com.me.humble.jon.shop._
                      |""".stripMargin
