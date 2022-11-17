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

@file:Suppress("PropertyName")

package net.ormr.userskripter.settings

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*
import kotlinx.serialization.serializer
import net.ormr.userskripter.UserskriptComponent
import net.ormr.userskripter.settings.delegates.KSerializerPropertyDelegateProvider
import net.ormr.userskripter.settings.delegates.RawStringPropertyDelegateProvider
import net.ormr.userskripter.settings.delegates.SettingPropertyDelegateProvider
import net.ormr.userskripter.settings.properties.Property
import net.ormr.userskripter.settings.properties.PropertyChangeListener
import net.ormr.userskripter.utils.Maybe
import net.ormr.userskripter.utils.MutableListSerializer
import net.ormr.userskripter.utils.MutableMapSerializer
import net.ormr.userskripter.utils.MutableSetSerializer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public abstract class SettingsGroup(public val groupKey: String) : UserskriptComponent() {
    public var parent: SettingsGroup? = null
        private set

    internal val _properties: MutableMap<String, Property<*>> = hashMapOf()
    public val properties: Map<String, Property<*>>
        get() = _properties

    public fun getPropertyOrNull(key: String): Property<*>? = _properties[key]

    public fun getFullPathKey(): String = generateSequence(parent) { it.parent }
        .map { it.groupKey }
        .toList()
        .asReversed()
        .joinToString(separator = "/") + groupKey

    @PublishedApi
    internal fun createKey(key: String): String = "${userskript.id}/settings/${getFullPathKey()}/$key"

    protected fun <T : SettingsGroup> group(group: () -> T): ReadOnlyProperty<SettingsGroup, T> =
        GroupProperty(this, group)

    protected fun rawString(
        key: String,
        description: String,
        default: String,
        validator: ((String) -> Unit)? = null,
    ): RawStringPropertyDelegateProvider = RawStringPropertyDelegateProvider(key, description, default, validator)

    protected inline fun <reified T> maybe(
        key: String? = null,
        description: String,
        default: Maybe<T>,
        valueSerializer: KSerializer<T> = serializer(),
        noinline validator: ((Maybe<T>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Maybe<T>> =
        delegate(key, description, default, Maybe.serializer(valueSerializer), validator)

    protected inline fun <reified T : Enum<T>> enum(
        key: String? = null,
        description: String,
        default: T,
        serializer: KSerializer<T> = serializer(),
        noinline validator: ((T) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<T> = delegate(key, description, default, serializer, validator)

    protected fun string(
        key: String? = null,
        description: String,
        default: String,
        validator: ((String) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<String> = delegate(key, description, default, String.serializer(), validator)

    protected fun boolean(
        key: String? = null,
        description: String,
        default: Boolean,
        validator: ((Boolean) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Boolean> =
        delegate(key, description, default, Boolean.serializer(), validator)

    protected fun char(
        key: String? = null,
        description: String,
        default: Char,
        validator: ((Char) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Char> = delegate(key, description, default, Char.serializer(), validator)

    protected fun byte(
        key: String? = null,
        description: String,
        default: Byte,
        validator: ((Byte) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Byte> = delegate(key, description, default, Byte.serializer(), validator)

    protected fun short(
        key: String? = null,
        description: String,
        default: Short,
        validator: ((Short) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Short> = delegate(key, description, default, Short.serializer(), validator)

    protected fun int(
        key: String? = null,
        description: String,
        default: Int,
        validator: ((Int) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Int> = delegate(key, description, default, Int.serializer(), validator)

    protected fun long(
        key: String? = null,
        description: String,
        default: Long,
        validator: ((Long) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Long> = delegate(key, description, default, Long.serializer(), validator)

    protected fun float(
        key: String? = null,
        description: String,
        default: Float,
        validator: ((Float) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Float> = delegate(key, description, default, Float.serializer(), validator)

    protected fun double(
        key: String? = null,
        description: String,
        default: Double,
        validator: ((Double) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Double> = delegate(key, description, default, Double.serializer(), validator)

    protected inline fun <reified T> list(
        key: String? = null,
        description: String,
        default: List<T>,
        valueSerializer: KSerializer<T> = serializer(),
        noinline validator: ((List<T>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<List<T>> =
        delegate(key, description, default, ListSerializer(valueSerializer), validator)

    protected inline fun <reified T> set(
        key: String? = null,
        description: String,
        default: Set<T>,
        valueSerializer: KSerializer<T> = serializer(),
        noinline validator: ((Set<T>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Set<T>> =
        delegate(key, description, default, SetSerializer(valueSerializer), validator)

    // contrary to what Kotlin says, <E> should not be removed, as the resolution will fail if it's removed.
    @Suppress("RemoveExplicitTypeArguments")
    @OptIn(ExperimentalSerializationApi::class)
    protected inline fun <reified T : Any, reified E : T?> array(
        key: String? = null,
        description: String,
        default: Array<E>,
        valueSerializer: KSerializer<E> = serializer<E>(),
        noinline validator: ((Array<E>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Array<E>> =
        delegate(key, description, default, ArraySerializer<T, E>(valueSerializer), validator)

    protected inline fun <reified K, reified V> map(
        key: String? = null,
        description: String,
        default: Map<K, V>,
        keySerializer: KSerializer<K> = serializer(),
        valueSerializer: KSerializer<V> = serializer(),
        noinline validator: ((Map<K, V>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<Map<K, V>> =
        delegate(key, description, default, MapSerializer(keySerializer, valueSerializer), validator)

    protected inline fun <reified T> mutableList(
        key: String? = null,
        description: String,
        default: MutableList<T>,
        valueSerializer: KSerializer<T> = serializer(),
        noinline validator: ((MutableList<T>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<MutableList<T>> =
        delegate(key, description, default, MutableListSerializer(valueSerializer), validator)

    protected inline fun <reified T> mutableSet(
        key: String? = null,
        description: String,
        default: MutableSet<T>,
        valueSerializer: KSerializer<T> = serializer(),
        noinline validator: ((MutableSet<T>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<MutableSet<T>> =
        delegate(key, description, default, MutableSetSerializer(valueSerializer), validator)

    protected inline fun <reified K, reified V> mutableMap(
        key: String? = null,
        description: String,
        default: MutableMap<K, V>,
        keySerializer: KSerializer<K> = serializer(),
        valueSerializer: KSerializer<V> = serializer(),
        noinline validator: ((MutableMap<K, V>) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<MutableMap<K, V>> =
        delegate(key, description, default, MutableMapSerializer(keySerializer, valueSerializer), validator)

    protected inline fun <reified T : Any> generic(
        key: String? = null,
        description: String,
        default: T,
        serializer: KSerializer<T> = serializer(),
        noinline validator: ((T) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<T> = delegate(key, description, default, serializer, validator)

    protected inline fun <reified T : Any> delegate(
        key: String?,
        description: String,
        default: T,
        serializer: KSerializer<T> = serializer(),
        noinline validator: ((T) -> Unit)? = null,
    ): KSerializerPropertyDelegateProvider<T> =
        KSerializerPropertyDelegateProvider(key, description, default, serializer, validator)

    protected fun <P : SettingPropertyDelegateProvider<T>, T : Any> P.onChange(
        listener: PropertyChangeListener<T>,
    ): P = apply {
        changeListeners += listener
    }

    private class GroupProperty<T : SettingsGroup>(
        parent: SettingsGroup,
        groupFactory: () -> T,
    ) : ReadOnlyProperty<SettingsGroup, T> {
        private var parent: SettingsGroup? = parent
        private var groupFactory: (() -> T)? = groupFactory
        private var group: T? = null
            set(value) {
                value?.parent = parent!!
                field = value
            }

        override fun getValue(thisRef: SettingsGroup, property: KProperty<*>): T = when (val group = group) {
            null -> {
                val createdGroup = groupFactory!!()
                this.group = createdGroup
                this.parent = null
                this.groupFactory = null
                createdGroup
            }
            else -> group
        }
    }
}