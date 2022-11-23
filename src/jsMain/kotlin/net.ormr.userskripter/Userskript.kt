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

package net.ormr.userskripter

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext

public class Userskript(private val context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = context
}

public inline fun userskript(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    action: Userskript.() -> Unit,
) {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }
    with(Userskript(dispatcher)) {
        action()
    }
}