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

package net.ormr.userskripter.settings.properties

import net.ormr.userskripter.settings.SettingsRepository

public sealed class Property<T : Any> {
    public abstract val key: String

    public abstract val description: String

    public abstract val defaultValue: T

    public val changeListeners: MutableList<PropertyChangeListener<T>> = mutableListOf()

    public abstract val validator: ((T) -> Unit)?

    public abstract fun encodeToString(value: T): String

    public abstract fun decodeFromString(string: String): T
}

public fun <T : Any> Property<T>.getValue(): T = SettingsRepository.getValue(this)

public fun <T : Any> Property<T>.getValueOrThrow(): T = SettingsRepository.getValueOrThrow(this)

public fun <T : Any> Property<T>.getValueOrNull(): T? = SettingsRepository.getValueOrNull(this)

public fun <T : Any> Property<T>.setValue(value: T) {
    SettingsRepository.setValue(this, value)
}

public fun <T : Any> Property<T>.hasValue(): Boolean = getValueOrNull() != null