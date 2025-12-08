package io.github.bossm0n5t3r.ksx.jvm.uuid

import kotlinx.serialization.json.Json
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UUIDSerializerTest {
    @Test
    fun `uuid should serialize and deserialize`() {
        val value = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        val serialized = Json.encodeToString(UUIDSerializer, value)
        val deserialized = Json.decodeFromString(UUIDSerializer, serialized)

        assertEquals("\"123e4567-e89b-12d3-a456-426614174000\"", serialized)
        assertEquals(value, deserialized)
    }

    @Test
    fun `invalid uuid string should fail`() {
        assertFailsWith<IllegalArgumentException> {
            Json.decodeFromString(UUIDSerializer, "\"not-a-uuid\"")
        }
    }
}
