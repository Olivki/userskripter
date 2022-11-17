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

import kotlinx.browser.document
import org.w3c.dom.Comment
import org.w3c.dom.Element
import org.w3c.dom.Node

public inline fun Node.appendComment(data: String): Comment = document.createComment(data).also { appendChild(it) }

@Suppress("UNCHECKED_CAST")
public inline fun <T : Element> Node.appendNewElement(
    localName: String,
    builder: T.() -> Unit = {},
): T = appendChild((document.createElement(localName) as T).apply(builder)) as T