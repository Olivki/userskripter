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

package net.ormr.userskripter.operations

import net.ormr.userskripter.utils.ALWAYS
import org.kodein.di.DirectDI
import org.w3c.dom.DocumentReadyState
import kotlin.time.Duration

public class Operations(
    public val operations: List<Entry>,
    public val interval: Duration,
    public val exceptionHandler: OperationsExceptionHandler?,
) {
    public class Entry(
        public val description: String,
        public val deferUntil: (suspend (DocumentReadyState) -> Boolean)?,
        public val condition: suspend () -> Boolean = ALWAYS,
        public val action: suspend DirectDI.() -> Unit,
    )
}