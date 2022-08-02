package com.vyacheslavivanov.ethereumprice.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.getSystemService
import com.vyacheslavivanov.ethereumprice.R


fun saveTextToClipboard(context: Context, label: String, text: String) {
    val clip = ClipData.newPlainText(label, text)
    val clipboardManager: ClipboardManager? = context.getSystemService()

    clipboardManager?.let {
        it.setPrimaryClip(clip)
        Toast.makeText(context, R.string.copied_to_clipboard_toast, Toast.LENGTH_SHORT).show()
    }
}
