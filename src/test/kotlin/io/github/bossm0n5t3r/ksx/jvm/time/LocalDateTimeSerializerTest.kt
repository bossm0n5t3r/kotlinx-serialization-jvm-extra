package io.github.bossm0n5t3r.ksx.jvm.time

import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalDateTimeSerializerTest {
    @Test
    fun `localdatetime should serialize and deserialize`() {
        val value = LocalDateTime.of(2024, 6, 1, 12, 30, 45, 123_000_000)
        val serialized = Json.encodeToString(LocalDateTimeSerializer, value)
        val deserialized = Json.decodeFromString(LocalDateTimeSerializer, serialized)

        assertEquals("\"2024-06-01T12:30:45.123\"", serialized)
        assertEquals(value, deserialized)
    }

    @Test
    fun `invalid localdatetime string should fail`() {
        assertFailsWith<DateTimeParseException> {
            Json.decodeFromString(LocalDateTimeSerializer, "\"2024-06-01T25:61:00\"")
        }
    }
}
