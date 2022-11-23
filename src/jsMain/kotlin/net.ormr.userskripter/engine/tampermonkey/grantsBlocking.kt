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

package net.ormr.userskripter.engine.tampermonkey

@RequiresOptIn(message = "Specify 'GM_addStyle' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMAddStyleBlocking

@RequiresOptIn(message = "Specify 'GM_addElement' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMAddElementBlocking

@RequiresOptIn(message = "Specify 'GM_addValueChangeListener' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMAddValueChangeListenerBlocking

@RequiresOptIn(message = "Specify 'GM_removeValueChangeListener' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMRemoveValueChangeListenerBlocking

@RequiresOptIn(message = "Specify 'GM_log' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMLogBlocking

@RequiresOptIn(message = "Specify 'GM_getResourceText' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMGetResourceTextBlocking

@RequiresOptIn(message = "Specify 'GM_unregisterMenuCommand' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMUnregisterMenuCommandBlocking

@RequiresOptIn(message = "Specify 'GM_download' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMDownloadBlocking

@RequiresOptIn(message = "Specify 'GM_getTab' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMGetTabBlocking

@RequiresOptIn(message = "Specify 'GM_saveTab' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMSaveTabBlocking

@RequiresOptIn(message = "Specify 'GM_getTabs' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMGetTabsBlocking