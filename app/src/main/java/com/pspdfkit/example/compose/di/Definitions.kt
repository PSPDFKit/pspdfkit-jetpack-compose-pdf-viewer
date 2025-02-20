/*
 *   Copyright © 2023-2024 PSPDFKit GmbH. All rights reserved.
 *
 *   The PSPDFKit Sample applications are licensed with a modified BSD license.
 *   Please see License for details. This notice may not be removed from this file.
 */

package com.pspdfkit.example.compose.di

import android.content.Context
import androidx.room.Room
import com.pspdfkit.example.compose.data.local.AppDatabase
import com.pspdfkit.example.compose.data.local.HistoryDao
import com.pspdfkit.example.compose.models.HistoryTable

/** [getDb] provides [AppDatabase] instance. */
fun getDb(context: Context): AppDatabase {
    return synchronized(context) {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database-pspdf-compose"
        ).fallbackToDestructiveMigration().build()
    }
}

/** Provides [HistoryDao] from database to manipulate entries from [HistoryTable]. */
fun getHistoryTableDao(appDatabase: AppDatabase) = appDatabase.historyDao()
