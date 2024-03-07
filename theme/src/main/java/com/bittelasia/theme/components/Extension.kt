package com.bittelasia.theme.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale

const val defaultColor = "#000000"

fun removeHttpsFromIpAddress(ipAddress: String): String {
    val regex = Regex("^http://")
    Log.v("meme",ipAddress.replace(regex,""))
    return ipAddress.replace(regex, "")
}

fun customDate(oldDate: String?, oldFormat: String, newFormat: String): String {
    val parser = SimpleDateFormat(oldFormat, Locale.getDefault())
    val formatter = SimpleDateFormat(newFormat, Locale.getDefault())
    val date = oldDate?.let { parser.parse(it) }
    return date?.let { formatter.format(it) }!!
}

@Composable
fun HomePageDivider(color: String?, modifier: Modifier = Modifier){
    Divider(
        color = Color(android.graphics.Color.parseColor(color ?: "#000000")),
        modifier = modifier
            .fillMaxHeight()
            .width(2.dp)
    )
}

fun capitalizeWord(data: String?): String {
    val words = data?.split(" ")
    var newStr = ""
    words?.forEach { word ->
        newStr += word.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        } + " "
    }
    return newStr.trimEnd()
}

@Composable
fun CustomDivider(color: String?, modifier: Modifier = Modifier){
    Divider(
        color = Color(android.graphics.Color.parseColor(color ?: defaultColor)),
        modifier = modifier
            .height(80.dp)
            .width(1.dp)
    )
}