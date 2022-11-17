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
import net.ormr.userskripter.engine.greasemonkey.PromiseGreaseMonkey as GM

@ScriptEngineGreaseMonkey
public object GreaseMonkey {
    public inline val info: GMInfo
        get() = GM.info

    @GrantGMSetValue
    public suspend inline fun setValue(name: String, value: String) {
        GM.setValue(name, value).await()
    }

    @GrantGMSetValue
    public suspend inline fun setValue(name: String, value: Int) {
        GM.setValue(name, value).await()
    }

    @GrantGMSetValue
    public suspend inline fun setValue(name: String, value: Boolean) {
        GM.setValue(name, value).await()
    }

    @Suppress("UNCHECKED_CAST")
    @GrantGMGetValue
    public suspend inline fun <T> getValue(name: String, default: T): T =
        GM.getValue(name, default).await() as T // this should be safe, but should probably verify it

    @GrantGMDeleteValue
    public suspend inline fun deleteValue(name: String) {
        GM.deleteValue(name).await()
    }

    @GrantGMListValues
    public suspend inline fun listValues(): Array<String> = GM.listValues().await()

    // resource
    @GrantGMGetResourceUrl
    public suspend inline fun getResourceUrl(resourceName: String): String = GM.getResourceUrl(resourceName).await()

    // other
    @GrantGMNotification
    public suspend inline fun notification(text: String, title: String, image: String, noinline onClick: () -> Unit) {
        GM.notification(text, title, image, onClick).await()
    }

    @GrantGMNotification
    public suspend inline fun notification(options: GMNotificationOptions) {
        GM.notification(options).await()
    }

    @GrantGMOpenInTab
    public suspend inline fun openInTab(url: String, openInBackground: Boolean = true) {
        GM.openInTab(url, openInBackground).await()
    }

    @GrantGMRegisterMenuCommand
    public suspend inline fun registerMenuCommand(
        caption: String,
        accessKey: Char,
        noinline onSelect: () -> Unit,
    ) {
        GM.registerMenuCommand(caption, onSelect, accessKey).await()
    }

    @GrantGMSetClipboard
    public suspend inline fun setClipboard(text: String) {
        GM.setClipboard(text).await()
    }

    @GrantGMXmlHttpRequest
    public suspend inline fun <C> xmlHttpRequest(details: GMXmlHttpRequestDetails<C>) {
        GM.xmlHttpRequest(details).await()
    }
}