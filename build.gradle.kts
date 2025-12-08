import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint.gradle)

    `maven-publish`
    signing
}

group = "io.github.bossm0n5t3r"
version = "0.1.0"

kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.core)

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.kotlinx.serialization.json)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

ktlint {
    version.set(libs.versions.pinterest.ktlint.get())
    verbose.set(true)
    outputColorName.set("RED")
}

val ossrhUsername: String? by project
val ossrhPassword: String? by project

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "kotlinx-serialization-jvm-extra"
            from(components["java"])
            pom {
                name.set("kotlinx-serialization-jvm-extra")
                description.set("Extra serializers for JVM-only types using kotlinx.serialization")
                url.set("https://github.com/bossm0n5t3r/kotlinx-serialization-jvm-extra")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("bossm0n5t3r")
                        name.set("bossm0n5t3r")
                        email.set("bossm0n5t3r@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/bossm0n5t3r/kotlinx-serialization-jvm-extra")
                    connection.set("scm:git:git://github.com/bossm0n5t3r/kotlinx-serialization-jvm-extra.git")
                    developerConnection.set("scm:git:ssh://github.com:bossm0n5t3r/kotlinx-serialization-jvm-extra.git")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl =
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl =
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

            url =
                if (version.toString().endsWith("SNAPSHOT")) {
                    snapshotsRepoUrl
                } else {
                    releasesRepoUrl
                }

            credentials {
                // gradle.properties 값이 있으면 우선 사용, 없으면 환경변수 사용
                username = ossrhUsername ?: System.getenv("OSSRH_USERNAME")
                password = ossrhPassword ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project

    // gradle.properties 에 없으면 환경변수도 체크
    val key = signingKey ?: System.getenv("SIGNING_KEY")
    val pass = signingPassword ?: System.getenv("SIGNING_PASSWORD")

    if (key != null && pass != null) {
        useInMemoryPgpKeys(key, pass)
        // 특정 퍼블리케이션만 명시적으로 서명
        sign(publishing.publications["maven"])
    }
}
