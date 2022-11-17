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

import kotlinx.coroutines.await
import net.ormr.userskripter.engine.ScriptEngineTamperMonkey
import net.ormr.userskripter.engine.greasemonkey.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import net.ormr.userskripter.engine.tampermonkey.PromiseTamperMonkey as TM

@ScriptEngineTamperMonkey
public object TamperMonkey {
    public inline val info: TMInfo
        get() = TM.info

    @GrantTMAddStyle
    public suspend inline fun addStyle(css: String): HTMLStyleElement = TM.addStyle(css).await()

    @GrantTMAddElement
    public suspend inline fun addElement(tagName: String, attributes: dynamic): HTMLElement =
        TM.addElement(tagName, attributes).await()

    @GrantTMAddElement
    public suspend inline fun addElement(parentNode: Node, tagName: String, attributes: dynamic): HTMLElement =
        TM.addElement(parentNode, tagName, attributes).await()

    // values
    @GrantGMSetValue
    public suspend inline fun setValue(name: String, value: dynamic) {
        TM.setValue(name, value).await()
    }

    @Suppress("UNCHECKED_CAST")
    @GrantGMGetValue
    public suspend inline fun <T> getValue(name: String, default: T): T = TM.getValue(name, default).await() as T

    @GrantGMGetValue
    public suspend inline fun <T> getValue(name: String): T? = TM.getValue<T>(name).await()

    @GrantGMDeleteValue
    public suspend inline fun deleteValue(name: String) {
        TM.deleteValue(name).await()
    }

    @GrantGMListValues
    public suspend inline fun listValues(): Array<String> = TM.listValues().await()

    /**
     * TODO
     *
     * @return the id of the registered listener, to be used with [removeValueChangeListener]
     */
    @GrantTMAddValueChangeListener
    public suspend inline fun addValueChangeListener(
        name: String,
        noinline listener: (name: String, oldValue: dynamic, newValue: dynamic, isRemote: Boolean) -> Unit,
    ): Double = TM.addValueChangeListener(name, listener).await()

    @GrantTMRemoveValueChangeListener
    public suspend inline fun removeValueChangeListener(listenerId: Double) {
        TM.removeValueChangeListener(listenerId).await()
    }

    // log
    /**
     * Logs the given [message] to the console.
     *
     * The difference between this and doing `console.log` is that this will be logged as if it was sent from the
     * site the script is running at.
     */
    @GrantTMLog
    public suspend inline fun log(message: dynamic) {
        TM.log(message).await()
    }

    // resource
    /**
     * Returns the text content of the `@resource` with the given [name].
     */
    @GrantTMGetResourceText
    public suspend inline fun getResourceText(name: String): String = TM.getResourceText(name).await()

    /**
     * Returns a Base64 encoded URI of the contents of the `@resource` with the given [name].
     */
    @GrantGMGetResourceUrl
    public suspend inline fun getResourceUrl(name: String): String = TM.getResourceUrl(name).await()

    // menu commands
    /**
     * TODO
     * @return the id of the registered menu command, to be used with [unregisterMenuCommand]
     */
    @GrantGMRegisterMenuCommand
    public suspend inline fun registerMenuCommand(
        name: String,
        accessKey: Char,
        noinline onSelect: (event: Event) -> Unit,
    ): Double = TM.registerMenuCommand(name, onSelect, accessKey).await()

    @GrantTMUnregisterMenuCommand
    public suspend inline fun unregisterMenuCommand(menuCommandId: Double) {
        TM.unregisterMenuCommand(menuCommandId).await()
    }

    // tabs
    @GrantGMOpenInTab
    public suspend inline fun openInTab(url: String): TMOpenInTabResult = TM.openInTab(url).await()

    @GrantGMOpenInTab
    public suspend inline fun openInTab(url: String, options: TMOpenInTabOptions): TMOpenInTabResult =
        TM.openInTab(url, options).await()

    @GrantGMOpenInTab
    public suspend inline fun openInTab(url: String, loadInBackground: Boolean): TMOpenInTabResult =
        TM.openInTab(url, loadInBackground).await()

    // https://stackoverflow.com/a/61639813

    /**
     * Returns a JavaScript object that is persistent as long as the current tab is open.
     */
    @GrantTMGetTab
    public suspend inline fun getTabObject(): dynamic = TM.getTabObject().await()

    @GrantTMSaveTab
    public suspend inline fun saveTabObject(tab: dynamic) {
        TM.saveTabObject(tab).await()
    }

    @GrantTMGetTabs
    public suspend inline fun getTabObjects(): dynamic = TM.getTabObjects().await()

    // notification
    @GrantGMNotification
    public suspend inline fun notification(
        details: TMNotificationDetails,
        noinline onDone: () -> Unit,
    ) {
        TM.notification(details, onDone).await()
    }

    @GrantGMNotification
    public suspend inline fun notification(
        text: String,
        title: String? = null,
        image: String? = null,
        noinline onClick: (() -> Unit)? = null,
    ) {
        TM.notification(text, title, image, onClick).await()
    }

    // clipboard
    @GrantGMSetClipboard
    public suspend inline fun setClipboard(data: String) {
        TM.setClipboard(data).await()
    }

    @GrantGMSetClipboard
    public suspend inline fun setClipboard(data: String, info: String) {
        TM.setClipboard(data, info).await()
    }

    @GrantGMSetClipboard
    public suspend inline fun setClipboard(data: String, info: TMClipboardInfo) {
        TM.setClipboard(data, info).await()
    }

    // xmlHttpRequest
    @GrantGMXmlHttpRequest
    public suspend inline fun <C> xmlHttpRequest(details: TMXmlHttpRequestDetails<C>): TMXmlHttpRequestResponse<C> =
        TM.xmlHttpRequest(details).await()

    // download
    // TODO: is this actually the correct signature?..
    @GrantTMDownload
    public suspend inline fun download(): TMXmlHttpRequestResponse<Nothing?> = TM.download().await()
}