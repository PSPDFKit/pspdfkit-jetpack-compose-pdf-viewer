/*
 *   Copyright © 2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

package com.pspdfkit.example.compose.utils

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * extensions to store dark mode and dynamic mode as int and fetch as state
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val isDarkKey = intPreferencesKey("isDarkKey")
val isDynamicKey = intPreferencesKey("isDynamicKey")

fun Context.isDarkThemeOn() = dataStore.data
    .map { preferences ->
        // No type safety.
        preferences[isDarkKey] ?: 0
    }

fun Context.isThemeDynamic() = dataStore.data
    .map { preferences ->
        // No type safety.
        preferences[isDynamicKey] ?: 0
    }

@Composable
fun isSystemInDarkThemeCustom(): Boolean {
    val context = LocalContext.current
    val prefs = runBlocking { context.dataStore.data.first() }
    return when (
        context.isDarkThemeOn()
            .collectAsState(initial = prefs[isDarkKey] ?: 0).value
    ) {
        2 -> true
        1 -> false
        else -> isSystemInDarkTheme()
    }
}

@Composable
fun isThemeDynamic(): Boolean {
    val context = LocalContext.current
    val prefs = runBlocking { context.dataStore.data.first() }
    return when (
        context.isThemeDynamic()
            .collectAsState(initial = prefs[isDynamicKey] ?: 0).value
    ) {
        0 -> true
        else -> false
    }
}
