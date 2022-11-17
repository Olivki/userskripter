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

package net.ormr.userskripter.engine.tampermonkey

import net.ormr.userskripter.engine.ScriptEngineTamperMonkey
import net.ormr.userskripter.engine.greasemonkey.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import kotlin.js.Promise

@JsName("GM")
@ScriptEngineTamperMonkey
public external object NativeTamperMonkey {
    public val info: TMInfo

    @GrantTMAddStyle
    public fun addStyle(css: String): Promise<HTMLStyleElement>

    @GrantTMAddElement
    public fun addElement(tagName: String, attributes: dynamic): Promise<HTMLElement>

    @GrantTMAddElement
    public fun addElement(parentNode: Node, tagName: String, attributes: dynamic): Promise<HTMLElement>

    // values
    @GrantGMSetValue
    public fun setValue(name: String, value: dynamic): Promise<Nothing?>

    @GrantGMGetValue
    public fun <T> getValue(name: String, default: T = definedExternally): Promise<T?>

    @GrantGMDeleteValue
    public fun deleteValue(name: String): Promise<Nothing?>

    @GrantGMListValues
    public fun listValues(): Promise<Array<String>>

    /**
     * TODO
     *
     * @return the id of the registered listener, to be used with [removeValueChangeListener]
     */
    @GrantTMAddValueChangeListener
    public fun addValueChangeListener(
        name: String,
        listener: (name: String, oldValue: dynamic, newValue: dynamic, isRemote: Boolean) -> Unit,
    ): Promise<Double>

    @GrantTMRemoveValueChangeListener
    public fun removeValueChangeListener(listenerId: Double): Promise<Nothing?>

    // log
    /**
     * Logs the given [message] to the console.
     *
     * The difference between this and doing `console.log` is that this will be logged as if it was sent from the
     * site the script is running at.
     */
    @GrantTMLog
    public fun log(message: dynamic): Promise<Nothing?>

    // resource
    /**
     * Returns the text content of the `@resource` with the given [name].
     */
    @GrantTMGetResourceText
    public fun getResourceText(name: String): Promise<String>

    /**
     * Returns a Base64 encoded URI of the contents of the `@resource` with the given [name].
     */
    @GrantGMGetResourceUrl
    public fun getResourceUrl(name: String): Promise<String>

    // menu commands
    /**
     * TODO
     * @return the id of the registered menu command, to be used with [unregisterMenuCommand]
     */
    @GrantGMRegisterMenuCommand
    public fun registerMenuCommand(name: String, onSelect: (event: Event) -> Unit, accessKey: Char): Promise<Double>

    @GrantTMUnregisterMenuCommand
    public fun unregisterMenuCommand(menuCommandId: Double): Promise<Nothing?>

    // tabs
    @GrantGMOpenInTab
    public fun openInTab(url: String): Promise<TMOpenInTabResult>

    @GrantGMOpenInTab
    public fun openInTab(url: String, options: TMOpenInTabOptions): Promise<TMOpenInTabResult>

    @GrantGMOpenInTab
    public fun openInTab(url: String, loadInBackground: Boolean): Promise<TMOpenInTabResult>

    // https://stackoverflow.com/a/61639813

    /**
     * Returns a JavaScript object that is persistent as long as the current tab is open.
     */
    @GrantTMGetTab
    @JsName("getTab")
    public fun getTabObject(): Promise<dynamic>

    @GrantTMSaveTab
    @JsName("saveTab")
    public fun saveTabObject(tab: dynamic): Promise<Nothing?>

    @GrantTMGetTabs
    @JsName("getTabs")
    public fun getTabObjects(): Promise<dynamic>

    // notification
    @GrantGMNotification
    @JsName("notification")
    public fun notification(
        details: TMNotificationDetails,
        onDone: () -> Unit = definedExternally,
    ): Promise<Nothing?>

    @GrantGMNotification
    @JsName("notification")
    public fun notification(
        text: String,
        title: String? = definedExternally,
        image: String? = definedExternally,
        onClick: (() -> Unit)? = definedExternally,
    ): Promise<Nothing?>

    // clipboard
    @GrantGMSetClipboard
    public fun setClipboard(data: String): Promise<Nothing?>

    @GrantGMSetClipboard
    public fun setClipboard(data: String, info: String): Promise<Nothing?>

    @GrantGMSetClipboard
    public fun setClipboard(data: String, info: TMClipboardInfo): Promise<Nothing?>

    // xmlHttpRequest
    @GrantGMXmlHttpRequest
    public fun <C> xmlHttpRequest(details: TMXmlHttpRequestDetails<C>): Promise<TMXmlHttpRequestResponse<C>>

    // download
    // TODO: is this actually the correct signature?..
    @GrantTMDownload
    public fun download(): Promise<TMXmlHttpRequestResponse<Nothing?>>
}