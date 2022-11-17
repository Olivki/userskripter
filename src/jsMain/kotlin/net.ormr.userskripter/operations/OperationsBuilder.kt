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

package net.ormr.userskripter.operations

import net.ormr.userskripter.UserskriptComponent
import net.ormr.userskripter.UserskripterDsl
import net.ormr.userskripter.utils.ALWAYS
import org.kodein.di.DirectDI
import org.w3c.dom.DocumentReadyState
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@UserskripterDsl
public class OperationsBuilder @PublishedApi internal constructor() : UserskriptComponent() {
    public var interval: Duration = 200.milliseconds
    public var operations: MutableList<Operations.Entry> = mutableListOf()
    public var exceptionHandler: OperationsExceptionHandler? = null

    @UserskripterDsl
    public fun entries(vararg operations: Operations.Entry) {
        this.operations.addAll(operations)
    }

    @UserskripterDsl
    public fun operation(
        description: String,
        deferUntil: (suspend (DocumentReadyState) -> Boolean)? = null,
        condition: suspend () -> Boolean = ALWAYS,
        action: suspend DirectDI.() -> Unit,
    ): Operations.Entry = Operations.Entry(
        description = description,
        deferUntil = deferUntil,
        condition = condition,
        action = action
    )

    @PublishedApi
    internal fun build(): Operations = Operations(
        operations = operations.toList(),
        interval = interval,
        exceptionHandler = exceptionHandler,
    )
}

@UserskripterDsl
public inline fun operations(crossinline builder: OperationsBuilder.() -> Unit): () -> Operations = {
    OperationsBuilder().apply(builder).build()
}