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

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.*

@Serializable(with = EitherSerializer::class)
public sealed class Either<out L, out R> {
    public inline fun <U> fold(ifLeft: (L) -> U, ifRight: (R) -> U): U = when (this) {
        is Left -> ifLeft(value)
        is Right -> ifRight(value)
    }

    @Serializable
    public data class Left<L>(@Contextual public val value: L) : Either<L, Nothing>() {
        override fun toString(): String = "Left($value)"
    }

    @Serializable
    public data class Right<R>(@Contextual public val value: R) : Either<Nothing, R>() {
        override fun toString(): String = "Right($value)"
    }
}

@Suppress("ClassName")
internal class EitherSerializer<L, R>(
    private val leftSerializer: KSerializer<L>,
    private val rightSerializer: KSerializer<R>,
) : KSerializer<Either<L, R>> {
    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Either") {
        element("type", String.serializer().descriptor)
        element(
            "value",
            buildSerialDescriptor(
                "(${leftSerializer.descriptor.serialName} | ${rightSerializer.descriptor.serialName})",
                SerialKind.CONTEXTUAL,
            ),
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Either<L, R> = decoder.decodeStructure(descriptor) {
        lateinit var type: String
        var value: Any? = NOT_SET

        while (true) {
            when (val i = decodeElementIndex(descriptor)) {
                0 -> type = decodeStringElement(descriptor, 0)
                1 -> when (type) {
                    "left" -> value = decodeSerializableElement(descriptor, 1, leftSerializer)
                    "right" -> value = decodeSerializableElement(descriptor, 1, rightSerializer)
                    else -> throw SerializationException("Expected 'left' or 'right', got: $type")
                }
                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index: $i")
            }
        }

        if (value == NOT_SET) throw SerializationException("Missing 'value' property")

        when (type) {
            "left" -> Either.Left(value as L)
            "right" -> Either.Right(value as R)
            else -> throw SerializationException("Expected 'left' or 'right', got: $type")
        }
    }

    private object NOT_SET

    override fun serialize(encoder: Encoder, value: Either<L, R>) {
        encoder.encodeStructure(descriptor) {
            when (value) {
                is Either.Left -> {
                    encodeStringElement(descriptor, 0, "left")
                    encodeSerializableElement(descriptor, 1, leftSerializer, value.value)
                }
                is Either.Right -> {
                    encodeStringElement(descriptor, 0, "right")
                    encodeSerializableElement(descriptor, 1, rightSerializer, value.value)
                }
            }
        }
    }
}
