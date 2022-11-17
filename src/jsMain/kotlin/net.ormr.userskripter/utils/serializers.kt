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

@file:Suppress("FunctionName")

package net.ormr.userskripter.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public fun <T> MutableListSerializer(elementSerializer: KSerializer<T>): KSerializer<MutableList<T>> {
    val delegate = ListSerializer(elementSerializer)
    return object : KSerializer<MutableList<T>> {
        override val descriptor: SerialDescriptor get() = delegate.descriptor

        override fun deserialize(decoder: Decoder): MutableList<T> = delegate.deserialize(decoder).toMutableList()

        override fun serialize(encoder: Encoder, value: MutableList<T>) {
            delegate.serialize(encoder, value)
        }
    }
}

public fun <T> MutableSetSerializer(elementSerializer: KSerializer<T>): KSerializer<MutableSet<T>> {
    val delegate = SetSerializer(elementSerializer)
    return object : KSerializer<MutableSet<T>> {
        override val descriptor: SerialDescriptor get() = delegate.descriptor

        override fun deserialize(decoder: Decoder): MutableSet<T> = delegate.deserialize(decoder).toMutableSet()

        override fun serialize(encoder: Encoder, value: MutableSet<T>) {
            delegate.serialize(encoder, value)
        }
    }
}

public fun <K, V> MutableMapSerializer(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
): KSerializer<MutableMap<K, V>> {
    val delegate = MapSerializer(keySerializer, valueSerializer)
    return object : KSerializer<MutableMap<K, V>> {
        override val descriptor: SerialDescriptor get() = delegate.descriptor

        override fun deserialize(decoder: Decoder): MutableMap<K, V> = delegate.deserialize(decoder).toMutableMap()

        override fun serialize(encoder: Encoder, value: MutableMap<K, V>) {
            delegate.serialize(encoder, value)
        }
    }
}