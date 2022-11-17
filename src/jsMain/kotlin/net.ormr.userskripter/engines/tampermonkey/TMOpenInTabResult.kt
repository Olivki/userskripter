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

public external interface TMOpenInTabResult {
    @JsName("closed")
    public val isClosed: Boolean

    @JsName("onclose")
    public var onClose: (() -> Unit)?

    public fun close()

    // TODO: this seems to also be available, but the docs don't mention it, so maybe it should be made nullable
    //       property instead?
    public fun focus()
}