plugins {
    id("java")
}

group = "com.miche"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vertx:vertx-core:4.5.11")

    implementation("org.apache.kafka:kafka_2.13:3.9.0")
    implementation("org.apache.kafka:kafka-clients:3.9.0")

    implementation("com.google.code.gson:gson:2.10.1")

    compileOnly("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}