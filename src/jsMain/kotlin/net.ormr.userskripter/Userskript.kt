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

import kotlinx.browser.document
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.ormr.userskripter.operations.Operations
import net.ormr.userskripter.settings.SettingsGroup
import net.ormr.userskripter.stylesheet.MATCH_ALL
import net.ormr.userskripter.stylesheet.MATCH_NONE
import net.ormr.userskripter.stylesheet.StyleSheetWithId
import net.ormr.userskripter.stylesheet.UserskripterStyleSheets
import net.ormr.userskripter.utils.appendComment
import net.ormr.userskripter.utils.appendNewElement
import org.kodein.di.direct
import org.w3c.dom.HTMLStyleElement

public class Userskript<S : UserskripterStyleSheets, G : SettingsGroup> internal constructor(
    public val id: String,
    settingsFactory: () -> G,
    styleSheetsFactory: () -> S,
    operationsFactory: () -> Operations,
) : UserskriptAwareDI {
    public companion object {}

    public val settings: G by lazy { settingsFactory() }
    public val styleSheets: S by lazy { styleSheetsFactory() }
    public val operations: Operations by lazy { operationsFactory() }

    internal suspend fun setup() {
        createStyleElements(id, styleSheets)
        runOperations(this, operations)
    }
}

private suspend fun createStyleElements(userskriptId: String, styleSheets: UserskripterStyleSheets) {
    val comment = "${userskriptId.uppercase()} STYLESHEETS"
    val fragment = document.createDocumentFragment().apply {
        appendComment(comment)
        for (styleSheet in styleSheets.registeredStyleSheets) {
            appendNewElement<HTMLStyleElement>("style") {
                media = if (styleSheet.condition()) MATCH_ALL else MATCH_NONE
                if (styleSheet is StyleSheetWithId) id = styleSheet.id
                textContent = styleSheet.contentText()
            }
        }
        appendComment(comment)
    }
    document.documentElement!!.appendChild(fragment)
}

private suspend fun runOperations(userskript: Userskript<*, *>, plan: Operations) = coroutineScope {
    val direct = userskript.direct
    for (operation in plan.operations) {
        launch {
            while (operation.deferUntil?.invoke(document.readyState) == false) {
                delay(plan.interval)
            }

            if (operation.condition()) {
                try {
                    operation.action(direct)
                } catch (e: Exception) {
                    plan.exceptionHandler?.invoke(e, operation)
                }
            }
        }
    }
}