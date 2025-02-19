plugins {
    id("java")
    application
}

group = "com.miche"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vertx:vertx-core:4.5.11")
    implementation("io.vertx:vertx-pg-client:4.5.13")

    implementation("org.apache.kafka:kafka_2.13:3.9.0")
    implementation("org.apache.kafka:kafka-clients:3.9.0")

    implementation("com.google.code.gson:gson:2.10.1")

    compileOnly("org.projectlombok:lombok:1.18.30")

    implementation("com.ongres.scram:client:2.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainVerticle" // 메인 클래스 설정 (파일명에 따라 변경)
    }

    from(
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    )

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}