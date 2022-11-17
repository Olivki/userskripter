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

@file:Suppress("FunctionName")

package net.ormr.userskripter.js

@JsName("Symbol")
public external interface SymbolInstance {
    public val description: String

    public fun valueOf(): dynamic

    override fun toString(): String
}

public external object Symbol {
    public val asyncIterator: SymbolInstance

    public val hasInstance: SymbolInstance

    public val isConcatSpreadable: SymbolInstance

    public val iterator: SymbolInstance

    public val match: SymbolInstance

    public val matchAll: SymbolInstance

    public val replace: SymbolInstance

    public val search: SymbolInstance

    public val split: SymbolInstance

    public val species: SymbolInstance

    public val toPrimitive: SymbolInstance

    public val toStringTag: SymbolInstance

    public val unscopables: SymbolInstance

    @JsName("for")
    public fun of(key: String): SymbolInstance

    public fun keyFor(symbol: SymbolInstance): String
}

public external fun Symbol(): SymbolInstance

public external fun Symbol(description: String): SymbolInstance