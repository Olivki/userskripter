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

import net.ormr.userskripter.operations.Operations
import net.ormr.userskripter.settings.SettingsGroup
import net.ormr.userskripter.stylesheet.UserskripterStyleSheets
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.DirectDIAware
import org.kodein.di.bindEagerSingleton
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.experimental.ExperimentalTypeInference

@UserskripterDsl
public class UserskriptBuilder<S : UserskripterStyleSheets, G : SettingsGroup> @PublishedApi internal constructor(
    public val id: String,
    override val directDI: DirectDI,
) : DirectDIAware {
    private lateinit var settings: () -> G
    private lateinit var styleSheets: () -> S
    private lateinit var operations: () -> Operations

    @UserskripterDsl
    public fun settings(factory: () -> G) {
        settings = factory
    }

    @UserskripterDsl
    public fun styleSheets(factory: () -> S) {
        styleSheets = factory
    }

    @UserskripterDsl
    public fun operations(factory: () -> Operations) {
        operations = factory
    }

    @PublishedApi
    internal suspend fun build(): Userskript<S, G> {
        val userskript = Userskript(
            id = id,
            settingsFactory = settings,
            styleSheetsFactory = styleSheets,
            operationsFactory = operations,
        )

        userskriptDI.addExtend(directDI.di).addConfig {
            bindEagerSingleton { userskript }
            bindEagerSingleton<Userskript<*, *>> { userskript }
        }

        userskript.setup()

        return userskript
    }
}

@OptIn(ExperimentalTypeInference::class)
@UserskripterDsl
public suspend inline fun <S : UserskripterStyleSheets, G : SettingsGroup> userskript(
    id: String,
    initialAction: () -> Unit = {},
    initAction: Userskript<S, G>.() -> Unit = {},
    crossinline di: DI.MainBuilder.() -> Unit = {},
    @BuilderInference builder: UserskriptBuilder<S, G>.() -> Unit,
) {
    contract {
        callsInPlace(initialAction, InvocationKind.EXACTLY_ONCE)
        callsInPlace(di, InvocationKind.EXACTLY_ONCE)
        callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        callsInPlace(initAction, InvocationKind.EXACTLY_ONCE)
    }

    val kodein = DI.direct {
        bindEagerSingleton(tag = "userskriptId") { id }
        di()
    }

    val userskript = UserskriptBuilder<S, G>(id, kodein).apply(builder).build()

    userskript.apply(initAction)
}