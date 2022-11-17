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

import org.w3c.dom.XMLDocument

public external interface TMXmlHttpRequestResponse<C> {
    public val finalUrl: String
    public val readyState: Short
    public val status: Int
    public val statusText: String
    public val responseHeaders: String
    public val response: dynamic

    @JsName("responseXML")
    public val responseXml: XMLDocument?
    public val responseText: String

    public val context: C
}