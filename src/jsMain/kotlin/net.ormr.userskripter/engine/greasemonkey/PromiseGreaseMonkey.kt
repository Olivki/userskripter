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

import net.ormr.userskripter.engine.ScriptEngineGreaseMonkey
import kotlin.js.Promise

@JsName("GM")
@ScriptEngineGreaseMonkey
public external object PromiseGreaseMonkey {
    public val info: GMInfo

    @GrantGMSetValue
    public fun setValue(name: String, value: String): Promise<Nothing?>

    @GrantGMSetValue
    public fun setValue(name: String, value: Int): Promise<Nothing?>

    @GrantGMSetValue
    public fun setValue(name: String, value: Boolean): Promise<Nothing?>

    @GrantGMGetValue
    public fun <T> getValue(name: String, default: T = definedExternally): Promise<T?>

    @GrantGMDeleteValue
    public fun deleteValue(name: String): Promise<Nothing?>

    @GrantGMListValues
    public fun listValues(): Promise<Array<String>>

    // resource
    @GrantGMGetResourceUrl
    public fun getResourceUrl(resourceName: String): Promise<String>

    // other
    @GrantGMNotification
    public fun notification(
        text: String,
        title: String = definedExternally,
        image: String = definedExternally,
        onClick: () -> Unit = definedExternally,
    ): Promise<Nothing?>

    @GrantGMNotification
    public fun notification(options: GMNotificationOptions): Promise<Nothing?>

    @GrantGMOpenInTab
    public fun openInTab(url: String, openInBackground: Boolean = definedExternally): Promise<Nothing?>

    @GrantGMRegisterMenuCommand
    public fun registerMenuCommand(caption: String, onSelect: () -> Unit, accessKey: Char): Promise<Nothing?>

    @GrantGMSetClipboard
    public fun setClipboard(text: String): Promise<Nothing?>

    @GrantGMXmlHttpRequest
    public fun <C> xmlHttpRequest(details: GMXmlHttpRequestDetails<C>): Promise<Nothing?>
}