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

package net.ormr.userskripter.settings.delegates

import kotlinx.serialization.KSerializer
import net.ormr.userskripter.settings.SettingsGroup
import net.ormr.userskripter.settings.properties.KSerializerPropertyAdapter
import kotlin.reflect.KProperty

public class KSerializerPropertyDelegateProvider<T : Any> @PublishedApi internal constructor(
    private val key: String?,
    private val description: String,
    private val default: T,
    private val serializer: KSerializer<T>,
    private val validator: ((T) -> Unit)?,
) : SettingPropertyDelegateProvider<T>() {
    override fun provideDelegate(thisRef: SettingsGroup, property: KProperty<*>): SettingPropertyDelegate<T> {
        val createdKey = thisRef.createKey(key ?: property.name)
        val settingProperty = KSerializerPropertyAdapter(createdKey, description, default, serializer, validator)
        settingProperty.changeListeners.addAll(changeListeners)
        thisRef._properties[createdKey] = settingProperty
        return SettingPropertyDelegate(settingProperty)
    }
}