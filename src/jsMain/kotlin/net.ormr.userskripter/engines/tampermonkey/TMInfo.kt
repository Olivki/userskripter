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

public external interface TMInfo {
    @JsName("script")
    public val scriptMetadata: ScriptMetadata

    // 'native', 'browser' or 'disabled'
    public val downloadMode: String

    public val isIncognito: Boolean

    public val scriptHandler: String

    @JsName("version")
    public val scriptHandlerVersion: String

    @JsName("scriptMetaStr")
    public val scriptMetadataRaw: String?

    public val scriptSource: String

    @JsName("scriptUpdateURL")
    public val scriptUpdateUrl: String?

    public val scriptWillUpdate: Boolean

    public interface ScriptMetadata {
        public val name: String

        @JsName("name_i18n")
        public val nameI18N: dynamic

        public val namespace: String?

        @JsName("antifeatures")
        public val antiFeatures: dynamic

        public val author: String?

        public val blockers: Array<dynamic>

        public val copyright: String?

        public val description: String

        @JsName("description_i18n")
        public val descriptionI18N: dynamic

        public val evilness: Int

        public val matches: Array<String>

        public val includes: Array<String>

        public val excludes: Array<String>

        public val requires: Array<String>

        public val grant: Array<String>

        public val header: String

        public val homepage: String?

        @JsName("updateURL")
        public val updateUrl: String?

        @JsName("downloadURL")
        public val downloadUrl: String?

        @JsName("supportURL")
        public val supportUrl: String?

        public val icon: String?

        public val icon64: String?

        public val options: dynamic

        public val position: Int

        public val resources: Array<TMResourceObject>

        public val sync: dynamic

        public val unwrap: Boolean

        public val uuid: String

        public val version: String

        public val webRequest: dynamic
    }
}