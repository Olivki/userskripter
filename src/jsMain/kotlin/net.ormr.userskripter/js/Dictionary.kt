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

@file:Suppress("NOTHING_TO_INLINE")

package net.ormr.userskripter.js

public external interface Dictionary<K, V>

public inline operator fun <K, V> Dictionary<K, V>.get(key: K): V = asDynamic()[key].unsafeCast<V>()

public inline operator fun <K, V> Dictionary<K, V>.set(key: K, value: V) {
    asDynamic()[key] = value
}

public fun <K, V> Dictionary<K, V>.entries(): List<Pair<K, V>> = Object.entries(this).map { it[0] to it[1] }

public fun <K, V> Dictionary<K, V>.keys(): List<String> = Object.keys(this).map { it }

public fun <K, V> Dictionary<K, V>.values(): List<V> = Object.values(this).map { it }

public inline fun <K, V> asDictionary(jsObject: dynamic): Dictionary<K, V> = jsObject.unsafeCast<Dictionary<K, V>>()
