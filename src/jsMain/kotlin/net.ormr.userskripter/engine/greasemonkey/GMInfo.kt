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

// TODO: we should probably actually disallow the usage of this class if another engine is specified, because this
//       does differ from TMInfo in some specs, so using this when using tampermonkey could lead to runtime errors
public external interface GMInfo {
    // TODO: give this a proper type
    @JsName("script")
    public val scriptMetadata: ScriptMetadata

    @JsName("scriptMetaStr")
    public val scriptMetadataRaw: String?

    public val scriptHandler: String

    @JsName("version")
    public val scriptHandlerVersion: String

    public interface ScriptMetadata {
        public val name: String

        public val description: String

        public val version: String

        public val namespace: String

        public val excludes: Array<String>

        public val includes: Array<String>

        public val matches: Array<String>

        public val resources: Resources

        public interface Resources
    }
}

public inline val GMInfo.ScriptMetadata.runAt: String
    get() = asDynamic()["run-at"].unsafeCast<String>()

public inline operator fun GMInfo.ScriptMetadata.Resources.get(key: String): GMResourceObject? {
    return asDynamic()[key]?.unsafeCast<GMResourceObject>()
}
