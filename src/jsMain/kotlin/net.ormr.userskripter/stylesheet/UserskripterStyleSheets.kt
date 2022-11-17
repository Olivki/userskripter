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

package net.ormr.userskripter.stylesheet

import net.ormr.userskripter.UserskriptComponent

public abstract class UserskripterStyleSheets : UserskriptComponent() {
    internal val registeredStyleSheets: MutableList<StyleSheet> = mutableListOf()

    public val entries: List<StyleSheet>
        get() = registeredStyleSheets

    protected fun styleSheet(
        condition: StyleSheetCondition,
        content: StyleSheetContent,
    ): StyleSheetWithoutId = StyleSheetWithoutId(condition, content).also { registeredStyleSheets += it }

    protected fun styleSheet(
        condition: StyleSheetCondition,
        content: StyleSheetContent,
        id: String,
    ): StyleSheetWithId = StyleSheetWithId(condition, content, id).also { registeredStyleSheets += it }

    protected fun id(name: String): String = "${userskript.id}-$name"
}