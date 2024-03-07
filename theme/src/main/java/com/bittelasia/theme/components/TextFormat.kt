package com.bittelasia.theme.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TextFormat(
    value: String,
    color: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
) {
    Text(
        text = value,
        modifier = modifier,
        style = textStyle,
        color = parseColorFromString(color)
    )
}

fun parseColorFromString(colorString: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}