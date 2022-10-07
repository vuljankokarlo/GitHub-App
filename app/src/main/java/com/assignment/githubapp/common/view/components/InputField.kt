package com.assignment.githubapp.common.view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import com.assignment.githubapp.ui.theme.LightGray
import com.assignment.githubapp.ui.theme.OpenSansRegular_10_14
import com.assignment.githubapp.ui.theme.OpenSansRegular_12_16
import com.assignment.githubapp.ui.theme.Turquoise
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun InputField(
    placeholder: String,
    text: String,
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    singleLine: Boolean = true,
    action: (String) -> Unit
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusLocalManager = LocalFocusManager.current
    val dividerColor = remember { mutableStateOf(Turquoise) }


    WindowInsets.ime

    val view = LocalView.current

    LaunchedEffect(Unit) {
        ViewCompat.setWindowInsetsAnimationCallback(view, null)
    }

    SideEffect {
        if ((errorMessage.isNotEmpty()))
            dividerColor.value = Color.Red
        else
            dividerColor.value = Turquoise
    }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        if (text.isNotEmpty()) {
            Text(
                text = placeholder.uppercase(),
                style = MaterialTheme.typography.OpenSansRegular_12_16,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                textStyle = MaterialTheme.typography.OpenSansRegular_12_16,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
                    .padding(bottom = 9.dp)
                    .onFocusEvent { fs ->
                        if (fs.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                    .onFocusChanged {
                        if (it.isFocused)
                            dividerColor.value = Turquoise
                        else {
                            dividerColor.value = LightGray
                        }
                    },
                value = text,
                singleLine = singleLine,
                onValueChange = action,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusLocalManager.clearFocus(true)
                }),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.OpenSansRegular_12_16,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                    innerTextField()
                }
            )
        }
        Divider(
            color = dividerColor.value,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        if (errorMessage.isNotEmpty())
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 16.sp,
                color = MaterialTheme.colors.error
            )
    }
}