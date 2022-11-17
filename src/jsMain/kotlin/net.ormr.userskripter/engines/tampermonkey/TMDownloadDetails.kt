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

public external interface TMDownloadDetails {
    public var url: String

    /**
     * The file name.
     *
     * For security reasons the extension needs to be whitelisted at Tampermonkeys options page.
     */
    @JsName("name")
    public var fileName: String

    public var headers: String?

    @JsName("saveAs")
    public var showSaveAsDialog: Boolean

    @JsName("onerror")
    public var onError: ((data: ErrorResponse) -> Unit)?

    @JsName("onload")
    public var onLoad: ((data: TMXmlHttpRequestResponse<Nothing?>) -> Unit)?

    @JsName("ontimeout")
    public var onTimeout: (() -> Unit)?

    public interface ErrorResponse {
        // documentation says it should basically be an enum, but from my testing it returns strings that do not match
        // the provided ones
        public var error: String
        // documentation says this should exist, but from my testing it doesn't
        public var details: dynamic
    }
}