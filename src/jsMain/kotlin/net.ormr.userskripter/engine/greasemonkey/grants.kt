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

@RequiresOptIn(message = "Specify 'GM.setValue' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMSetValue

@RequiresOptIn(message = "Specify 'GM.getValue' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMGetValue

@RequiresOptIn(message = "Specify 'GM.deleteValue' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMDeleteValue

@RequiresOptIn(message = "Specify 'GM.listValues' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMListValues

@RequiresOptIn(message = "Specify 'GM.getResourceUrl' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMGetResourceUrl

@RequiresOptIn(message = "Specify 'GM.notification' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMNotification

@RequiresOptIn(message = "Specify 'GM.openInTab' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMOpenInTab

@RequiresOptIn(message = "Specify 'GM.registerMenuCommand' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMRegisterMenuCommand

@RequiresOptIn(message = "Specify 'GM.setClipboard' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMSetClipboard

@RequiresOptIn(message = "Specify 'GM.xmlHttpRequest' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMXmlHttpRequest

@RequiresOptIn(message = "Specify 'unsafeWindow' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantUnsafeWindow
