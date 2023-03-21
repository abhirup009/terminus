plugins {
	kotlin("jvm")
}

group = "service.wealth"
version = "0.0.1-SNAPSHOT"

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

}
