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

@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package net.ormr.userskripter.engine.tampermonkey

import net.ormr.userskripter.engine.ScriptEngineTamperMonkey
import net.ormr.userskripter.engine.greasemonkey.GrantGMDeleteValueBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMGetResourceUrlBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMGetValueBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMListValuesBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMNotificationBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMOpenInTabBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMRegisterMenuCommandBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMSetClipboardBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMSetValueBlocking
import net.ormr.userskripter.engine.greasemonkey.GrantGMXmlHttpRequestBlocking
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import net.ormr.userskripter.engine.tampermonkey.NativeTamperMonkey as TM

@ScriptEngineTamperMonkey
public object BlockingTamperMonkey {
    public inline val info: TMInfo
        get() = TM.info

    @GrantTMAddStyleBlocking
    public inline fun addStyle(css: String): HTMLStyleElement = GM_addStyle(css)

    @GrantTMAddElementBlocking
    public inline fun addElement(tagName: String, attributes: dynamic): HTMLElement =
        GM_addElement(tagName, attributes)

    @GrantTMAddElementBlocking
    public inline fun addElement(parentNode: Node, tagName: String, attributes: dynamic): HTMLElement =
        GM_addElement(parentNode, tagName, attributes)

    // values
    @GrantGMSetValueBlocking
    public inline fun setValue(name: String, value: dynamic) {
        GM_setValue(name, value)
    }

    @GrantGMGetValueBlocking
    public inline fun <T> getValue(name: String, default: T): T = GM_getValue(name, default)

    @GrantGMGetValueBlocking
    public inline fun <T> getValue(name: String): T? = getValue(name, null)

    @GrantGMDeleteValueBlocking
    public inline fun deleteValue(name: String) {
        GM_deleteValue(name)
    }

    @GrantGMListValuesBlocking
    public inline fun listValues(): Array<String> = GM_listValues()

    /**
     * TODO
     *
     * @return the id of the registered listener, to be used with [removeValueChangeListener]
     */
    @GrantTMAddValueChangeListenerBlocking
    public inline fun addValueChangeListener(
        name: String,
        noinline listener: (name: String, oldValue: dynamic, newValue: dynamic, isRemote: Boolean) -> Unit,
    ): Double = GM_addValueChangeListener(name, listener)

    @GrantTMRemoveValueChangeListenerBlocking
    public inline fun removeValueChangeListener(listenerId: Double) {
        GM_removeValueChangeListener(listenerId)
    }

    // log
    /**
     * Logs the given [message] to the console.
     *
     * The difference between this and doing `console.log` is that this will be logged as if it was sent from the
     * site the script is running at.
     */
    @GrantTMLogBlocking
    public inline fun log(message: dynamic) {
        GM_log(message)
    }

    // resource
    /**
     * Returns the text content of the `@resource` with the given [name].
     */
    @GrantTMGetResourceTextBlocking
    public inline fun getResourceText(name: String): String = GM_getResourceText(name)

    /**
     * Returns a Base64 encoded URI of the contents of the `@resource` with the given [name].
     */
    @GrantGMGetResourceUrlBlocking
    public inline fun getResourceUrl(name: String): String = GM_getResourceUrl(name)

    // menu commands
    /**
     * TODO
     * @return the id of the registered menu command, to be used with [unregisterMenuCommand]
     */
    @GrantGMRegisterMenuCommandBlocking
    public inline fun registerMenuCommand(
        name: String,
        accessKey: Char,
        noinline onSelect: (event: Event) -> Unit,
    ): Double = GM_registerMenuCommand(name, onSelect, accessKey)

    @GrantTMUnregisterMenuCommandBlocking
    public inline fun unregisterMenuCommand(menuCommandId: Double) {
        GM_unregisterMenuCommand(menuCommandId)
    }

    // tabs
    @GrantGMOpenInTabBlocking
    public inline fun openInTab(url: String): TMOpenInTabResult = GM_openInTab(url)

    @GrantGMOpenInTabBlocking
    public inline fun openInTab(url: String, options: TMOpenInTabOptions): TMOpenInTabResult =
        GM_openInTab(url, options)

    @GrantGMOpenInTabBlocking
    public inline fun openInTab(url: String, loadInBackground: Boolean): TMOpenInTabResult =
        GM_openInTab(url, loadInBackground)

    // https://stackoverflow.com/a/61639813

    /**
     * Returns a JavaScript object that is persistent as long as the current tab is open.
     */
    @GrantTMGetTabBlocking
    public inline fun getTabObject(): dynamic = GM_getTabObject()

    @GrantTMSaveTabBlocking
    public inline fun saveTabObject(tab: dynamic) {
        GM_saveTabObject(tab)
    }

    @GrantTMGetTabsBlocking
    public inline fun getTabObjects(): dynamic = GM_getTabObjects()

    // notification
    @GrantGMNotificationBlocking
    public inline fun notification(
        details: TMNotificationDetails,
        noinline onDone: () -> Unit,
    ) {
        GM_notification(details, onDone)
    }

    @GrantGMNotificationBlocking
    public inline fun notification(
        text: String,
        title: String? = null,
        image: String? = null,
        noinline onClick: (() -> Unit)? = null,
    ) {
        GM_notification(text, title, image, onClick)
    }

    // clipboard
    @GrantGMSetClipboardBlocking
    public inline fun setClipboard(data: String) {
        GM_setClipboard(data)
    }

    @GrantGMSetClipboardBlocking
    public inline fun setClipboard(data: String, info: String) {
        GM_setClipboard(data, info)
    }

    @GrantGMSetClipboardBlocking
    public inline fun setClipboard(data: String, info: TMClipboardInfo) {
        GM_setClipboard(data, info)
    }

    // xmlHttpRequest
    @GrantGMXmlHttpRequestBlocking
    public inline fun <C> xmlHttpRequest(details: TMXmlHttpRequestDetails<C>): TMXmlHttpRequestResponse<C> =
        GM_xmlHttpRequest(details)

    // download
    // TODO: implement download function
}

@ScriptEngineTamperMonkey
@GrantTMAddStyleBlocking
public external fun GM_addStyle(css: String): HTMLStyleElement

@ScriptEngineTamperMonkey
@GrantTMAddElementBlocking
public external fun GM_addElement(tagName: String, attributes: dynamic): HTMLElement

@ScriptEngineTamperMonkey
@GrantTMAddElementBlocking
public external fun GM_addElement(parentNode: Node, tagName: String, attributes: dynamic): HTMLElement

// values
@ScriptEngineTamperMonkey
@GrantGMSetValueBlocking
public external fun GM_setValue(name: String, value: dynamic)

@ScriptEngineTamperMonkey
@GrantGMGetValueBlocking
public external fun <T> GM_getValue(name: String, default: T): T

@ScriptEngineTamperMonkey
@GrantGMGetValueBlocking
public inline fun <T> GM_getValue(name: String): T? = GM_getValue(name, null)

@ScriptEngineTamperMonkey
@GrantGMDeleteValueBlocking
public external fun GM_deleteValue(name: String)

@ScriptEngineTamperMonkey
@GrantGMListValuesBlocking
public external fun GM_listValues(): Array<String>

/**
 * TODO
 *
 * @return the id of the registered listener, to be used with [GM_removeValueChangeListener]
 */
@ScriptEngineTamperMonkey
@GrantTMAddValueChangeListenerBlocking
public external fun GM_addValueChangeListener(
    name: String,
    listener: (name: String, oldValue: dynamic, newValue: dynamic, isRemote: Boolean) -> Unit,
): Double

@ScriptEngineTamperMonkey
@GrantTMRemoveValueChangeListenerBlocking
public external fun GM_removeValueChangeListener(listenerId: Double)

// log
/**
 * Logs the given [message] to the console.
 *
 * The difference between this and doing `console.log` is that this will be logged as if it was sent from the
 * site the script is running at.
 */
@ScriptEngineTamperMonkey
@GrantTMLogBlocking
public external fun GM_log(message: dynamic)

// resource
/**
 * Returns the text content of the `@resource` with the given [name].
 */
@ScriptEngineTamperMonkey
@GrantTMGetResourceTextBlocking
public external fun GM_getResourceText(name: String): String

/**
 * Returns a Base64 encoded URI of the contents of the `@resource` with the given [name].
 */
@ScriptEngineTamperMonkey
@GrantGMGetResourceUrlBlocking
public external fun GM_getResourceUrl(name: String): String

// menu commands
/**
 * TODO
 * @return the id of the registered menu command, to be used with [GM_unregisterMenuCommand]
 */
@ScriptEngineTamperMonkey
@GrantGMRegisterMenuCommandBlocking
public external fun GM_registerMenuCommand(
    name: String,
    onSelect: (event: Event) -> Unit,
    accessKey: Char,
): Double

@ScriptEngineTamperMonkey
@GrantTMUnregisterMenuCommandBlocking
public external fun GM_unregisterMenuCommand(menuCommandId: Double)

// tabs
@ScriptEngineTamperMonkey
@GrantGMOpenInTabBlocking
public external fun GM_openInTab(url: String): TMOpenInTabResult

@ScriptEngineTamperMonkey
@GrantGMOpenInTabBlocking
public external fun GM_openInTab(url: String, options: TMOpenInTabOptions): TMOpenInTabResult

@ScriptEngineTamperMonkey
@GrantGMOpenInTabBlocking
public external fun GM_openInTab(url: String, loadInBackground: Boolean): TMOpenInTabResult

// https://stackoverflow.com/a/61639813

/**
 * Returns a JavaScript object that is persistent as long as the current tab is open.
 */
@ScriptEngineTamperMonkey
@GrantTMGetTabBlocking
public external fun GM_getTabObject(): dynamic

@ScriptEngineTamperMonkey
@GrantTMSaveTabBlocking
public external fun GM_saveTabObject(tab: dynamic)

@ScriptEngineTamperMonkey
@GrantTMGetTabsBlocking
public external fun GM_getTabObjects(): dynamic

// notification
@ScriptEngineTamperMonkey
@GrantGMNotificationBlocking
public external fun GM_notification(
    details: TMNotificationDetails,
    onDone: () -> Unit = definedExternally,
)

@ScriptEngineTamperMonkey
@GrantGMNotificationBlocking
public external fun GM_notification(
    text: String,
    title: String? = definedExternally,
    image: String? = definedExternally,
    onClick: (() -> Unit)? = definedExternally,
)

// clipboard
@ScriptEngineTamperMonkey
@GrantGMSetClipboardBlocking
public external fun GM_setClipboard(data: String)

@ScriptEngineTamperMonkey
@GrantGMSetClipboardBlocking
public external fun GM_setClipboard(data: String, info: String)

@ScriptEngineTamperMonkey
@GrantGMSetClipboardBlocking
public external fun GM_setClipboard(data: String, info: TMClipboardInfo)

// xmlHttpRequest
@ScriptEngineTamperMonkey
@GrantGMXmlHttpRequestBlocking
public external fun <C> GM_xmlHttpRequest(details: TMXmlHttpRequestDetails<C>): TMXmlHttpRequestResponse<C>

// TODO: implement download function