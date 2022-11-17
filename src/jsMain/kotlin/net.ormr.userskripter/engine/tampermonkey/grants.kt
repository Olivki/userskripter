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

@RequiresOptIn(message = "Specify 'GM.addStyle' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMAddStyle

@RequiresOptIn(message = "Specify 'GM.addElement' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMAddElement

@RequiresOptIn(message = "Specify 'GM.addValueChangeListener' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMAddValueChangeListener

@RequiresOptIn(message = "Specify 'GM.removeValueChangeListener' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMRemoveValueChangeListener

@RequiresOptIn(message = "Specify 'GM.log' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMLog

@RequiresOptIn(message = "Specify 'GM.getResourceText' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMGetResourceText

@RequiresOptIn(message = "Specify 'GM.unregisterMenuCommand' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMUnregisterMenuCommand

@RequiresOptIn(message = "Specify 'GM.download' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMDownload

@RequiresOptIn(message = "Specify 'GM.getTab' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMGetTab

@RequiresOptIn(message = "Specify 'GM.saveTab' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMSaveTab

@RequiresOptIn(message = "Specify 'GM.getTabs' for 'grant' in userskripter.metadata in Gradle")
public annotation class GrantTMGetTabs