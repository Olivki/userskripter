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

import net.ormr.userskripter.settings.SettingsGroup
import net.ormr.userskripter.settings.properties.Property
import net.ormr.userskripter.settings.properties.getValue
import net.ormr.userskripter.settings.properties.setValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

public class SettingPropertyDelegate<T : Any> internal constructor(
    internal val property: Property<T>,
) : ReadWriteProperty<SettingsGroup, T> {
    override fun getValue(thisRef: SettingsGroup, property: KProperty<*>): T = this.property.getValue()

    override fun setValue(thisRef: SettingsGroup, property: KProperty<*>, value: T) {
        this.property.setValue(value)
    }
}