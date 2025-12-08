package io.github.bossm0n5t3r.ksx.jvm.time

import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeParseException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalDateSerializerTest {
    @Test
    fun `localdate should serialize and deserialize`() {
        val value = LocalDate.of(2024, 6, 1)
        val serialized = Json.encodeToString(LocalDateSerializer, value)
        val deserialized = Json.decodeFromString(LocalDateSerializer, serialized)

        assertEquals("\"2024-06-01\"", serialized)
        assertEquals(value, deserialized)
    }

    @Test
    fun `invalid localdate string should fail`() {
        assertFailsWith<DateTimeParseException> {
            Json.decodeFromString(LocalDateSerializer, "\"2024-13-01\"")
        }
    }
}
