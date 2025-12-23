# kotlinx-serialization-jvm-extra

[![Maven Central](https://img.shields.io/maven-central/v/io.github.bossm0n5t3r/kotlinx-serialization-jvm-extra)](https://central.sonatype.com/artifact/io.github.bossm0n5t3r/kotlinx-serialization-jvm-extra)

`kotlinx.serialization`에서 기본 제공하지 않는 JVM 타입(BigDecimal, `java.time.*`, `UUID` 등)을 직렬화/역직렬화하기 위한 Serializer 모음 라이브러리입니다.

## 지원 Serializer

- `BigDecimalSerializer` (문자열 기반, `toPlainString`)
- `BigIntegerSerializer` (문자열 기반)
- `LocalDateSerializer` (`ISO-8601` 포맷)
- `LocalDateTimeSerializer` (`ISO-8601` 포맷)
- `OffsetDateTimeSerializer` (`ISO-8601` 포맷)
- `InstantSerializer` (`ISO-8601`, UTC 기반)
- `UUIDSerializer` (표준 UUID 문자열)

## 사용법

```kotlin
import io.github.bossm0n5t3r.ksx.jvm.bigdecimal.BigDecimalSerializer
import io.github.bossm0n5t3r.ksx.jvm.time.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
data class Sample(
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
)

fun main() {
    val sample = Sample(BigDecimal("1234.56"), LocalDateTime.parse("2024-06-01T12:30:45"))
    val json = Json.encodeToString(sample)
    println(json)
    // {"amount":"1234.56","timestamp":"2024-06-01T12:30:45"}
}
```

Gradle 예시:

```kotlin
dependencies {
    implementation("io.github.bossm0n5t3r:kotlinx-serialization-jvm-extra:0.2.0")
}
```

## 개발/테스트

- JDK 21 (Temurin)
- Kotlin 2.0.x
- Gradle Wrapper 사용
- 코드 스타일: ktlint 1.2.1

```bash
./gradlew ktlintCheck
./gradlew test
```

## 라이선스

MIT
