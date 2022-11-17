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

package net.ormr.userskripter.util

import net.ormr.userskripter.js.Object

public fun <K, V> Map<K, V>.toJsObject(): dynamic {
    val obj: dynamic = js("({})")
    for ((key, value) in this) obj[key] = value
    return obj
}

// we can't declare dynamic extension functions, so it's a bit uglier
public fun <K, V> jsObjectToMap(jsObject: dynamic): MutableMap<K, V> {
    val map = hashMapOf<K, V>()
    for (entry in Object.entries(jsObject)) {
        map[entry[0].unsafeCast<K>()] = entry[1].unsafeCast<V>()
    }
    return map
}