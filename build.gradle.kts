import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.8.20-RC"
	id("com.google.cloud.tools.jib") version "3.1.4"
	application
}

group = "service.wealth"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	google()
	mavenLocal()
}

dependencies {
	//Jersey

	// https://mvnrepository.com/artifact/org.glassfish.jersey.inject/jersey-hk2
	implementation("org.glassfish.jersey.inject:jersey-hk2:3.1.1")

	// https://mvnrepository.com/artifact/org.glassfish.jersey.core/jersey-server
	implementation("org.glassfish.jersey.core:jersey-server:3.1.1")

	// https://mvnrepository.com/artifact/org.glassfish.jersey.media/jersey-media-json-jackson
	implementation("org.glassfish.jersey.media:jersey-media-json-jackson:3.1.1")

	// https://mvnrepository.com/artifact/org.glassfish.jersey.containers/jersey-container-netty-http
	implementation("org.glassfish.jersey.containers:jersey-container-netty-http:3.1.1")

	// https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime
	implementation("org.glassfish.jaxb:jaxb-runtime:4.0.2")


	// Logging
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
	testImplementation("ch.qos.logback:logback-classic:1.4.6")


	// Kotlin
	// https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib
	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")


	// Jackson
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")

	// https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")

	implementation("commons-io:commons-io:2.11.0")

	implementation(project("controller"))
	implementation(project(":core"))
	implementation(project(":data"))
}

application {
	mainClass.set("service.wealth.terminus.Main")
}

allprojects {
	tasks.withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = JavaVersion.VERSION_17.toString()
		}
	}
}

jib {
	from {
		image = "amazoncorretto:17-alpine-jdk"
	}
	to {
		image = "terminus"
	}
	container {
		ports = listOf("8080")
		mainClass = "service.wealth.terminus.Main"
	}
	containerizingMode = "packaged"
}

tasks.test {
	useJUnitPlatform()
}
