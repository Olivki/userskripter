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

package net.ormr.userskripter.engine

import net.ormr.userskripter.engine.greasemonkey.GrantUnsafeWindow
import org.w3c.dom.Window

@RequiresOptIn(message = "Use of 'unsafeWindow' is VERY insecure, and should be AVOIDED whenever possible")
public annotation class UnsafeWindowOptIn

@UnsafeWindowOptIn
@GrantUnsafeWindow
@UnsafeWindowCompatibleScriptEngine
public external val unsafeWindow: UnsafeWindow

// TODO: will this work properly?
@JsName("Window")
@UnsafeWindowOptIn
@GrantUnsafeWindow
@UnsafeWindowCompatibleScriptEngine
public abstract external class UnsafeWindow : Window

@UnsafeWindowOptIn
@GrantUnsafeWindow
@UnsafeWindowCompatibleScriptEngine
public inline operator fun UnsafeWindow.get(key: String): dynamic = asDynamic()[key]

@UnsafeWindowOptIn
@GrantUnsafeWindow
@UnsafeWindowCompatibleScriptEngine
public inline operator fun UnsafeWindow.set(key: String, value: dynamic) {
    asDynamic()[key] = value
}