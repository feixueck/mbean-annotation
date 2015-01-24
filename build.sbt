import com.typesafe.sbt.SbtPgp._

lazy val root = (project in file("."))
  .settings(basicSettings: _*)
  .settings(dependencySettings: _*)
  .settings(publishSettings: _*)
  .settings(releaseSettings: _*)
  .settings(addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full))

lazy val basicSettings = Seq(
  name := "mbean-annotation",
  organization := "com.wacai",
  scalaVersion := "2.11.5",
  scalacOptions += "-encoding",
  scalacOptions += "utf8",
  scalacOptions += "-feature",
  scalacOptions += "-unchecked",
  scalacOptions += "-deprecation",
  scalacOptions += "-language:_"
)

lazy val dependencySettings = Seq(
  libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-reflect" % _),
  libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

lazy val publishSettings = Seq(
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.trim.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { (repo: MavenRepository) => false},
  pomExtra := pomXml) ++ xerial.sbt.Sonatype.sonatypeSettings

lazy val pomXml = {
  <url>https://github.com/wacai/config-annotation</url>
    <licenses>
      <license>
        <name>Apache License 2.0</name>
        <url>http://www.apache.org/licenses/</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:wacai/mbean-annotation.git</url>
      <connection>scm:git:git@github.com:wacai/mbean-annotation.git</connection>
    </scm>
    <developers>
      <developer>
        <id>zhongl</id>
        <name>zhongl</name>
        <url>http://github.com/zhongl</url>
      </developer>
    </developers>
}

lazy val releaseSettings = sbtrelease.ReleasePlugin.releaseSettings ++ Seq(
  sbtrelease.ReleasePlugin.ReleaseKeys.publishArtifactsAction := PgpKeys.publishSigned.value
)
