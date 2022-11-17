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

package net.ormr.userskripter.settings

import kotlinx.browser.window
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.overwriteWith
import net.ormr.userskripter.settings.properties.MissingPropertyValueException
import net.ormr.userskripter.settings.properties.Property
import net.ormr.userskripter.settings.properties.PropertyValidationFailedException

public object SettingsRepository {
    public var jsonFormat: Json = Json
        private set

    @MutateSettingsJsonFormat
    public fun newJsonFormat(builder: JsonBuilder.() -> Unit) {
        jsonFormat = Json(from = jsonFormat, builderAction = builder)
    }

    @OptIn(MutateSettingsJsonFormat::class)
    public inline fun addSerializerModule(crossinline builder: SerializersModuleBuilder.() -> Unit) {
        newJsonFormat {
            serializersModule = jsonFormat.serializersModule overwriteWith SerializersModule(builder)
        }
    }

    public fun <T : Any> getValue(property: Property<T>): T = getValueOrNull(property) ?: property.defaultValue

    public fun <T : Any> getValueOrThrow(property: Property<T>): T =
        getValueOrNull(property) ?: throw MissingPropertyValueException("No value found at key: ${property.key}")

    public fun <T : Any> getValueOrNull(property: Property<T>): T? =
        window.localStorage.getItem(property.key)?.let { property.decodeFromString(it) }

    public fun <T : Any> setValue(property: Property<T>, value: T) {
        if (property.changeListeners.isNotEmpty()) {
            val oldValue = getValue(property)
            if (oldValue != value) {
                for (listener in property.changeListeners) listener(property, oldValue, value)
            }
        }

        try {
            property.validator?.invoke(value)
        } catch (e: Exception) {
            throw PropertyValidationFailedException(e.message ?: "no message", e.cause)
        }

        window.localStorage.setItem(property.key, property.encodeToString(value))
    }

    public fun removeValue(property: Property<*>) {
        window.localStorage.removeItem(property.key)
    }

    public operator fun contains(property: Property<*>): Boolean = window.localStorage.getItem(property.key) != null
}