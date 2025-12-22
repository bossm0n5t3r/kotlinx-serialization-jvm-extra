package io.github.bossm0n5t3r.ksx.jvm.time

import kotlinx.serialization.json.Json
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OffsetDateTimeSerializerTest {
    @Test
    fun `offsetdatetime should serialize and deserialize`() {
        val value = OffsetDateTime.of(2024, 6, 1, 12, 30, 45, 123_000_000, ZoneOffset.ofHours(9))
        val serialized = Json.encodeToString(OffsetDateTimeSerializer, value)
        val deserialized = Json.decodeFromString(OffsetDateTimeSerializer, serialized)

        assertEquals("\"2024-06-01T12:30:45.123+09:00\"", serialized)
        assertEquals(value, deserialized)
    }

    @Test
    fun `invalid offsetdatetime string should fail`() {
        assertFailsWith<DateTimeParseException> {
            Json.decodeFromString(OffsetDateTimeSerializer, "\"2024-06-01T12:30:45\"")
        }
    }
}
