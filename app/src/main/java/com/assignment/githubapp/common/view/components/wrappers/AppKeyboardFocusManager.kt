package com.assignment.githubapp.common.view.components.wrappers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.assignment.githubapp.common.KeyBoardManager

@Composable
fun AppKeyboardFocusManager() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    DisposableEffect(key1 = context) {
        val keyboardManager = KeyBoardManager(context)
        keyboardManager.attachKeyboardDismissListener {
            focusManager.clearFocus()
        }
        onDispose {
            keyboardManager.release()
        }
    }
}