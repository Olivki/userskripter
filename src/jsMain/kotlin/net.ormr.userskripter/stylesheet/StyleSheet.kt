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

import kotlinx.browser.document

public typealias StyleSheetCondition = suspend () -> Boolean
public typealias StyleSheetContent = suspend () -> String

internal const val MATCH_ALL = "all"
internal const val MATCH_NONE = "not all"

public sealed class StyleSheet {
    public abstract val condition: StyleSheetCondition
    protected abstract val content: StyleSheetContent

    private var cachedContent: String? = null

    public suspend fun contentText(): String = when (val content = cachedContent) {
        null -> content().also { cachedContent = it }
        else -> content
    }
}

public class StyleSheetWithoutId internal constructor(
    override val condition: StyleSheetCondition,
    override val content: StyleSheetContent,
) : StyleSheet()

public class StyleSheetWithId internal constructor(
    override val condition: StyleSheetCondition,
    override val content: StyleSheetContent,
    public val id: String,
) : StyleSheet() {
    public fun enable() {
        document.getElementById(id)?.setAttribute("media", MATCH_ALL)
    }

    public fun disable() {
        document.getElementById(id)?.setAttribute("media", MATCH_NONE)
    }
}