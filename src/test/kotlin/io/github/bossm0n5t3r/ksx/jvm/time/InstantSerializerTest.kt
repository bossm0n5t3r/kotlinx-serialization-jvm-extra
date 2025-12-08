package io.github.bossm0n5t3r.ksx.jvm.time

import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.format.DateTimeParseException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class InstantSerializerTest {
    @Test
    fun `instant should serialize and deserialize`() {
        val value = Instant.parse("2024-06-01T12:30:45.123Z")
        val serialized = Json.encodeToString(InstantSerializer, value)
        val deserialized = Json.decodeFromString(InstantSerializer, serialized)

        assertEquals("\"2024-06-01T12:30:45.123Z\"", serialized)
        assertEquals(value, deserialized)
    }

    @Test
    fun `invalid instant string should fail`() {
        assertFailsWith<DateTimeParseException> {
            Json.decodeFromString(InstantSerializer, "\"2024-06-01 12:30:45\"")
        }
    }
}
