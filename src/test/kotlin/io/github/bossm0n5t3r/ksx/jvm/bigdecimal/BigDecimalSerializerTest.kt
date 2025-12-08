package io.github.bossm0n5t3r.ksx.jvm.bigdecimal

import kotlinx.serialization.json.Json
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BigDecimalSerializerTest {
    @Test
    fun `bigdecimal should serialize and deserialize`() {
        val value = BigDecimal("1234.5678")
        val serialized = Json.encodeToString(BigDecimalSerializer, value)
        val deserialized = Json.decodeFromString(BigDecimalSerializer, serialized)

        assertEquals("\"1234.5678\"", serialized)
        assertEquals(value, deserialized)
    }

    @Test
    fun `invalid bigdecimal string should fail`() {
        assertFailsWith<NumberFormatException> {
            Json.decodeFromString(BigDecimalSerializer, "\"not-a-number\"")
        }
    }
}
