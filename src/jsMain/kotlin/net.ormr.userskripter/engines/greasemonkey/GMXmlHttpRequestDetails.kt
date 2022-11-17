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

package net.ormr.userskripter.engines.greasemonkey

public external interface GMXmlHttpRequestDetails<C> {
    public var url: String
    @JsName("binary")
    public var isBinary: Boolean
    public var context: C
    public var data: String?
    public var method: String?
    public var overrideMimeType: String?
    public var user: String?
    public var password: String?
    // TODO: this is actually just an enum, but idk if we can properly represent that easily in Kotlin
    public var responseType: String
    @JsName("synchronous")
    public var isSynchronous: Boolean
    @JsName("upload")
    public var eventHandler: EventHandler<C>

    public interface EventHandler<C> {
        @JsName("onabort")
        public var onAbort: ((response: ResponseObject<C>) -> Unit)?

        @JsName("onerror")
        public var onError: ((response: ResponseObject<C>) -> Unit)?

        @JsName("onload")
        public var onLoad: ((response: ResponseObject<C>) -> Unit)?

        @JsName("onprogress")
        public var onProgress: ((response: ResponseObject<C>) -> Unit)?

        @JsName("onreadystatechange")
        public var onReadyStateChange: ((response: ResponseObject<C>) -> Unit)?

        @JsName("ontimeout")
        public var onTimeout: ((response: ResponseObject<C>) -> Unit)?
    }

    public interface ResponseObject<C> {
        public val readyState: Short
        public val responseHeaders: String
        public val responseText: String
        public val status: Short
        public val statusText: String
        public val context: C?
    }
}