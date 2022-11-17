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

package net.ormr.userskripter.engines.tampermonkey

public external interface TMXmlHttpRequestDetails<C> {
    // one of GET, HEAD, POST
    public var method: String
    public var url: String

    public var headers: String?
    public var data: String?
    public var cookie: String?

    @JsName("binary")
    public var isBinary: Boolean

    @JsName("nocache")
    public var noCache: Boolean

    public var revalidate: Boolean

    public var timeout: Long

    public var context: C

    // one of arraybuffer, blob, json or stream
    public var responseType: String?

    public var overrideMimeType: String

    @JsName("anonymous")
    public var isAnonymous: Boolean

    @JsName("fetch")
    public var useFetch: Boolean

    public var user: String?

    public var password: String?

    @JsName("onabort")
    public var onAbort: (() -> Unit)?

    @JsName("onerror")
    public var onError: ((error: dynamic) -> Unit)?

    @JsName("onloadstart")
    public var onLoadStart: ((value: dynamic) -> Unit)?

    @JsName("onprogress")
    public var onProgress: ((value: dynamic) -> Unit)?

    @JsName("onreadystatechange")
    public var onReadyStateChange: ((value: dynamic) -> Unit)?

    @JsName("ontimeout")
    public var onTimeout: (() -> Unit)?

    @JsName("onload")
    public var onLoad: ((response: TMXmlHttpRequestResponse<C>) -> Unit)?
}