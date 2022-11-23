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

@RequiresOptIn(message = "Specify 'GM_setValue' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMSetValueBlocking

@RequiresOptIn(message = "Specify 'GM_getValue' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMGetValueBlocking

@RequiresOptIn(message = "Specify 'GM_deleteValue' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMDeleteValueBlocking

@RequiresOptIn(message = "Specify 'GM_listValues' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMListValuesBlocking

@RequiresOptIn(message = "Specify 'GM_getResourceUrl' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMGetResourceUrlBlocking

@RequiresOptIn(message = "Specify 'GM_notification' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMNotificationBlocking

@RequiresOptIn(message = "Specify 'GM_openInTab' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMOpenInTabBlocking

@RequiresOptIn(message = "Specify 'GM_registerMenuCommand' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMRegisterMenuCommandBlocking

@RequiresOptIn(message = "Specify 'GM_setClipboard' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMSetClipboardBlocking

@RequiresOptIn(message = "Specify 'GM_xmlHttpRequest' for 'grant' in userskripter.metadata in Gradle")
@Retention(AnnotationRetention.BINARY)
public annotation class GrantGMXmlHttpRequestBlocking
