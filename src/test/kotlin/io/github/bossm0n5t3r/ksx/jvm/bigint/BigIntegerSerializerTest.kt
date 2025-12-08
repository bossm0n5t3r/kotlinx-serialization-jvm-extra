package io.github.bossm0n5t3r.ksx.jvm.bigint

import kotlinx.serialization.json.Json
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BigIntegerSerializerTest {
    @Test
    fun `biginteger should serialize and deserialize`() {
        val value = BigInteger("9876543210123456789")
        val serialized = Json.encodeToString(BigIntegerSerializer, value)
        val deserialized = Json.decodeFromString(BigIntegerSerializer, serialized)

        assertEquals("\"9876543210123456789\"", serialized)
        assertEquals(value, deserialized)
    }

    @Test
    fun `invalid biginteger string should fail`() {
        assertFailsWith<NumberFormatException> {
            Json.decodeFromString(BigIntegerSerializer, "\"bad-int\"")
        }
    }
}
