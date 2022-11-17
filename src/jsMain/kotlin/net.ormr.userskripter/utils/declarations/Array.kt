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

package net.ormr.userskripter.utils.declarations

public inline fun <T> Array<T>.unshift(element: T): Int = this.asDynamic().unshift(element).unsafeCast<Int>()

public inline fun <T> Array<T>.unshift(vararg elements: T): Int =
    this.asDynamic().unshift.apply(this, arrayOf(*elements)).unsafeCast<Int>()

public inline fun <T> Array<T>.shift(): T? = this.asDynamic().shift()?.unsafeCast<T>()

public inline fun <T> Array<T>.push(element: T): Int = this.asDynamic().push(element).unsafeCast<Int>()

public inline fun <T> Array<T>.push(vararg elements: T): Int =
    this.asDynamic().push.apply(this, arrayOf(*elements)).unsafeCast<Int>()

public inline fun <T> Array<T>.pop(): T? = this.asDynamic().pop()?.unsafeCast<T>()

public inline fun <T> Array<T>.splice(start: Int, deleteCount: Int): Array<T> =
    asDynamic().splice(start, deleteCount).unsafeCast<Array<T>>()

public inline fun <T> Array<T>.splice(start: Int, deleteCount: Int, vararg items: T): Array<T> =
    asDynamic().splice.apply(this, arrayOf(start, deleteCount, *items)).unsafeCast<Array<T>>()

public inline fun <T> Array<T>.removeAt(index: Int): T? = splice(index, 1)[0]

public fun <T> Array<T>.remove(element: T): T? {
    val index = indexOf(element)
    return if (index >= 0) removeAt(index) else null
}
