/*
 * Copyright 2022 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ormr.userskripter.utils

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.*

@Serializable(with = MaybeSerializer::class)
public sealed class Maybe<out T> {
    public inline fun <R> fold(ifNone: () -> R, ifSome: (T) -> R): R = when (this) {
        None -> ifNone()
        is Some -> ifSome(value)
    }

    public fun unwrap(): T? = fold({ null }, { it })

    @Serializable
    public object None : Maybe<Nothing>() {
        override fun toString(): String = "None"
    }

    @Serializable
    public data class Some<T>(@Contextual public val value: T) : Maybe<T>() {
        override fun toString(): String = "Some($value)"
    }
}

public fun <T> T?.toMaybe(): Maybe<T> = if (this != null) Maybe.Some(this) else Maybe.None

internal class MaybeSerializer<T>(private val valueSerializer: KSerializer<T>) : KSerializer<Maybe<T>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Maybe") {
        element("type", String.serializer().descriptor)
        element("value", valueSerializer.descriptor)
    }

    override fun deserialize(decoder: Decoder): Maybe<T> = decoder.decodeStructure(descriptor) {
        lateinit var type: String
        var hasValue = false
        var value: T? = null

        while (true) {
            when (val i = decodeElementIndex(descriptor)) {
                0 -> type = decodeStringElement(descriptor, 0)
                1 -> {
                    value = decodeSerializableElement(descriptor, 1, valueSerializer)
                    hasValue = true
                }
                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index: $i")
            }

        }
        when (type) {
            "none" -> Maybe.None
            "some" -> Maybe.Some(wrapValue(type, value, hasValue))
            else -> throw SerializationException("Expected 'none' or 'some', got: $type")
        }
    }

    override fun serialize(encoder: Encoder, value: Maybe<T>) {
        encoder.encodeStructure(descriptor) {
            when (value) {
                Maybe.None -> {
                    encodeStringElement(descriptor, 0, "none")
                }
                is Maybe.Some -> {
                    encodeStringElement(descriptor, 0, "some")
                    encodeSerializableElement(descriptor, 1, valueSerializer, value.value)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
internal fun <T> wrapValue(type: String, value: T?, hasValue: Boolean): T = when {
    hasValue -> value as T
    else -> throw SerializationException("Entry '$type' should have 'value' element, but it didn't.")
}
