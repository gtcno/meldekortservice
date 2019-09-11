import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val prometheusVersion = "0.6.0"
val ktorVersion = "1.2.2"
val junitVersion = "5.4.1"
val confluentVersion = "5.2.0"
val logstashVersion = "5.2"
val logbackVersion = "1.2.3"
val vaultJdbcVersion = "1.3.1"
val assertJVersion = "3.12.2"
val jacksonVersion = "2.9.9"

val mainClass = "no.nav.meldeplikt.meldekortservice.AppKt"

plugins {
    java
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.allopen") version "1.3.41"

    application
}

group = "no.nav.meldeform"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    maven("http://packages.confluent.io/maven")
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // testCompile("junit", "junit", "4.12")
    compile("no.nav:vault-jdbc:$vaultJdbcVersion")
    compile("ch.qos.logback:logback-classic:$logbackVersion")
    compile("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    compile("io.prometheus:simpleclient_common:$prometheusVersion")
    compile("io.prometheus:simpleclient_hotspot:$prometheusVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-auth:$ktorVersion")
    compile("io.ktor:ktor-auth-jwt:$ktorVersion")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    compile("io.ktor:ktor-jackson:$ktorVersion")
    compile("io.confluent:kafka-avro-serializer:$confluentVersion")
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testCompile("org.assertj:assertj-core:$assertJVersion")
    testCompile(kotlin("test-junit5"))
    testImplementation("io.confluent:kafka-schema-registry:$confluentVersion")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = mainClass
}

tasks {
    withType<Jar> {
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }
        from(configurations.runtime.get().map { if (it.isDirectory) it else zipTree(it) })
    }

    register("runServer", JavaExec::class) {
        main = application.mainClassName
        classpath = sourceSets["main"].runtimeClasspath
    }
}