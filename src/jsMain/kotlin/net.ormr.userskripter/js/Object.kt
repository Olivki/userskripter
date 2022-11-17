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

/**
 * Creates a new JavaScript object cast to the given [T], populated by [builder].
 *
 * Note that this is ***very*** unsafe, and should only be used if you know what you're doing.
 */
public inline fun <T> jsObject(builder: T.() -> Unit): T = newJsObject<T>().apply(builder)

/**
 * Creates a new JavaScript object populated by the given [builder].
 */
public inline fun buildObject(builder: dynamic.() -> Unit): dynamic {
    val obj: dynamic = js("({})")
    builder(obj)
    return obj
}

/**
 * Creates a new empty JavaScript object cast to the given [T].
 *
 * Note that this is ***very*** unsafe, and should only be used if you know what you're doing.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> newJsObject(): T = js("({})").unsafeCast<T>()

public fun entriesOf(jsObject: dynamic): List<Pair<dynamic, dynamic>> =
    Object.entries(jsObject).map { it[0] to it[1] }

public fun keysOf(jsObject: dynamic): List<String> = Object.keys(jsObject).toList()

public fun valuesOf(jsObject: dynamic): List<String> = Object.values(jsObject).toList()

public inline fun forEachEntry(jsObject: dynamic, action: (key: dynamic, value: dynamic) -> Unit) {
    for (tuple in Object.entries(jsObject)) {
        action(tuple[0], tuple[1])
    }
}

public external object Object {
    public fun assign(target: dynamic, vararg sources: dynamic): dynamic

    public fun create(prototype: dynamic, propertiesObject: dynamic = definedExternally): dynamic

    public fun defineProperty(obj: dynamic, prop: String, descriptor: PropertyDescriptor): dynamic

    public fun defineProperty(obj: dynamic, prop: SymbolInstance, descriptor: PropertyDescriptor): dynamic

    public fun defineProperties(obj: dynamic, props: Dictionary<String, PropertyDescriptor>): dynamic

    public fun entries(obj: dynamic): Array<Array<dynamic>>

    public fun keys(obj: dynamic): Array<String>

    public fun values(obj: dynamic): Array<dynamic>

    public fun freeze(obj: dynamic): dynamic

    public fun fromEntries(iterable: dynamic): dynamic

    public fun getOwnPropertyDescriptor(obj: dynamic, prop: String): PropertyDescriptor?

    public fun getOwnPropertyDescriptor(obj: dynamic, prop: SymbolInstance): PropertyDescriptor?

    public fun getOwnPropertyDescriptors(obj: dynamic): Dictionary<dynamic, PropertyDescriptor>

    public fun getOwnPropertyNames(obj: dynamic): Array<String>

    public fun getOwnPropertySymbols(obj: dynamic): Array<SymbolInstance>

    public fun getPrototypeOf(obj: dynamic): dynamic

    public fun setPrototypeOf(obj: dynamic, prototype: dynamic): dynamic

    @JsName("is")
    public fun isSame(value1: dynamic, value2: dynamic): Boolean

    public fun isExtensible(obj: dynamic): Boolean

    public fun isFrozen(obj: dynamic): Boolean

    public fun isSealed(obj: dynamic): Boolean

    public fun preventExtensions(obj: dynamic): dynamic

    public fun seal(obj: dynamic): dynamic
}

public inline infix fun Any.isSame(other: Any): Boolean = Object.isSame(this, other)

public sealed external interface PropertyDescriptor {
    public var configurable: Boolean

    public var enumerable: Boolean
}

public external interface DataPropertyDescriptor : PropertyDescriptor {
    public var value: dynamic

    public var writable: Boolean
}

// TODO: generics?
public external interface AccessorPropertyDescriptor : PropertyDescriptor {
    public var get: (() -> dynamic)?

    public var set: ((dynamic) -> Unit)?
}