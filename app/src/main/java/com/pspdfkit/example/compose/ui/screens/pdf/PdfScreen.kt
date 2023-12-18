/*
 *   Copyright © 2023 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

package com.pspdfkit.example.compose.ui.screens.pdf

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.pspdfkit.configuration.activity.PdfActivityConfiguration
import com.pspdfkit.configuration.activity.UserInterfaceViewMode
import com.pspdfkit.configuration.theming.ThemeMode
import com.pspdfkit.example.compose.R
import com.pspdfkit.example.compose.utils.isDarkThemeOn
import com.pspdfkit.jetpack.compose.DefaultListeners
import com.pspdfkit.jetpack.compose.DocumentView
import com.pspdfkit.jetpack.compose.ExperimentalPSPDFKitApi
import com.pspdfkit.jetpack.compose.getDefaultDocumentManager
import com.pspdfkit.jetpack.compose.rememberDocumentState
import org.koin.androidx.compose.getViewModel
import java.io.File

/** [PdfScreen] acts as a pdf container which displays PDF. */
@Composable
fun PdfScreen(id: String, navigateTo: () -> Unit) {
    val context = LocalContext.current
    val pdfVM = getViewModel<PdfScreenViewModel>()
    val doc by pdfVM.getDocument(id).collectAsState(initial = emptyList())
    val theme by context.isDarkThemeOn().collectAsState(initial = 0)
    doc.firstOrNull()?.let { pdf ->
        val f = File(pdf.path)
        PdfUI(pdf = f, context = context, theme = theme, isDark = isSystemInDarkTheme(), navigateTo = navigateTo)
    }
}

@OptIn(ExperimentalPSPDFKitApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PdfUI(pdf: File, context: Context, theme: Int, isDark: Boolean, navigateTo: () -> Unit) {
    val updatedTheme = if (theme == 0) { if (isDark) { R.style.PSPDFCompose_Theme_Dark } else { R.style.PSPDFCompose_Theme_Light } } else if (theme == 1) { R.style.PSPDFCompose_Theme_Light } else { R.style.PSPDFCompose_Theme_Dark }

    val pdfActivityConfiguration = PdfActivityConfiguration
        .Builder(context)
        .setUserInterfaceViewMode(UserInterfaceViewMode.USER_INTERFACE_VIEW_MODE_AUTOMATIC)
        .themeMode(ThemeMode.DEFAULT)
        .themeDark(R.style.PSPDFCompose_Theme_Dark)
        .theme(updatedTheme)
        .build()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = pdf.nameWithoutExtension) }, navigationIcon = {
            IconButton(onClick = {
                navigateTo.invoke()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }) {
        Column(modifier = Modifier.padding(it)) {
            var documentState = rememberDocumentState(
                pdf.toUri(),
                pdfActivityConfiguration
            )

            DocumentView(
                documentState,
                documentManager = getDefaultDocumentManager(
                    documentListener = DefaultListeners.documentListeners(onDocumentLoaded = {
                        Toast.makeText(context, "document Loaded", Toast.LENGTH_LONG).show()
                    }),
                    annotationListener = DefaultListeners.annotationListeners(
                        onAnnotationSelected = { annotation, _ ->
                            Toast.makeText(context, "${annotation.type} selected", Toast.LENGTH_LONG).show()
                        },
                        onAnnotationDeselected = { annotation, _ ->
                            Toast.makeText(context, "${annotation.type} deselected", Toast.LENGTH_LONG).show()
                        }
                    )
                )
            )
        }
    }
}
