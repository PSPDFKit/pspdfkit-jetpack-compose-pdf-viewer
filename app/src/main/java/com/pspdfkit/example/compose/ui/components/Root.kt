/*
 *   Copyright © 2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

package com.pspdfkit.example.compose.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pspdfkit.example.compose.ui.theme.ComposeTheme
import com.pspdfkit.example.compose.utils.isSystemInDarkThemeCustom
import com.pspdfkit.example.compose.utils.isThemeDynamic

/** Works as a root container that contains all compose UI */
@Composable
fun Root() {
    ComposeTheme(isSystemInDarkThemeCustom(), isThemeDynamic()) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) { Navigation() }
    }
}
