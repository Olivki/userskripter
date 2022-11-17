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

package net.ormr.userskripter.engine.greasemonkey

import kotlinx.coroutines.await
import net.ormr.userskripter.engine.ScriptEngineGreaseMonkey
import net.ormr.userskripter.engine.greasemonkey.GMXmlHttpRequestDetails.EventHandler
import net.ormr.userskripter.js.UnsafeJs
import net.ormr.userskripter.js.jsObject

@OptIn(UnsafeJs::class)
@GrantGMNotification
@ScriptEngineGreaseMonkey
public suspend inline fun GreaseMonkey.notification(builder: GMNotificationOptions.() -> Unit) {
    notification(jsObject(builder)).await()
}

@GrantGMXmlHttpRequest
@ScriptEngineGreaseMonkey
public suspend inline fun GreaseMonkey.xmlHttpRequest(builder: GMXmlHttpRequestDetails<Nothing?>.() -> Unit) {
    xmlHttpRequest<Nothing?>(builder)
}

@OptIn(UnsafeJs::class)
@GrantGMXmlHttpRequest
@ScriptEngineGreaseMonkey
public suspend inline fun <C> GreaseMonkey.xmlHttpRequest(builder: GMXmlHttpRequestDetails<C>.() -> Unit) {
    xmlHttpRequest(jsObject(builder)).await()
}

@OptIn(UnsafeJs::class)
@ScriptEngineGreaseMonkey
public inline fun <C> GMXmlHttpRequestDetails<C>.eventHandler(builder: EventHandler<C>.() -> Unit) {
    eventHandler = jsObject(builder)
}