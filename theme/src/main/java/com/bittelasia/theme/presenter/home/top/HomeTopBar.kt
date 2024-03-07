package com.bittelasia.theme.presenter.home.top

import android.graphics.Typeface
import android.widget.TextClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.core_datastore.model.STB
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.R
import com.bittelasia.theme.components.HomePageDivider
import com.bittelasia.theme.components.TextFormat
import com.bittelasia.theme.presenter.home.main.ThemeBackground

@Composable
fun HomeTop(
    zones: Zones?,
    weather: WeatherData?,
    customerData: CustomerData?,
    configData: ConfigData?
) {
    if (zones != null) {
        ThemeBackground(
            zone = zones,
            modifier = Modifier
                .height(170.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    val fullName =
                        if (customerData?.firstname != "")
                            customerData?.firstname +" "+ customerData?.lastname
                        else ""
                    LogoAndGuest(
                        guestName = fullName,
                        logo = configData!!.logo,
                        color = zones.textColor
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row {
                        CalendarSection(
                            alignment1 = Alignment.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            color = zones.textColor
                        )
                        HomePageWeather(
                            alignment1 = Alignment.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            color = zones.textColor,
                            weather = weather
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LogoAndGuest(
    logo: String,
    guestName: String?,
    room: String = STB.ROOM,
    color: String
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(end = 20.dp, start = 35.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(.26f)
                    .wrapContentSize()
            )
            Box(
                modifier = Modifier
                    .weight(.74f)
                    .wrapContentSize()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(105.dp)
                        .wrapContentHeight()
                        .padding(top = 7.dp)
                )
            }
        }
        GuestSection(
            guestTxt = guestName,
            roomTxt = "ROOM NO: $room",
            textColor = color
        )
    }
}


@Composable
fun GuestSection(
    guestTxt: String?,
    roomTxt: String = "",
    textColor: String,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.255f),
            contentAlignment = Alignment.CenterStart
        ) {
            TextFormat(
                value = stringResource(id = R.string.welcome),
                color = textColor,
                textStyle = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(.033f))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            (if (guestTxt != "") guestTxt else "Guest")?.let {
                TextFormat(
                    value = it,
                    color = textColor,
                    textStyle = TextStyle(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            TextFormat(
                value = roomTxt,
                color = textColor,
                textStyle = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
    }
}


@Composable
fun CalendarSection(
    color: String,
    alignment1: Alignment,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = horizontalAlignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.255f),
            contentAlignment = alignment1
        ) {
            TextFormat(
                value = "DATE & TIME",
                color = color,
                textStyle = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(.033f))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 45.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = horizontalAlignment
        ) {
            Calendar(color = color, size = 35f, format = "hh:mm a")
            Calendar(color = color, size = 20f, format = "EEE | dd MMMM yyy")
        }
    }
}


@Composable
fun HomePageWeather(
    alignment1: Alignment,
    horizontalAlignment: Alignment.Horizontal,
    color: String,
    weather: WeatherData?
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = horizontalAlignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.255f),
            contentAlignment = alignment1
        ) {
            TextFormat(
                value = "WEATHER",
                color = color,
                textStyle = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(.033f))
        Row {
            HomePageDivider(color = "#d3ccac") //Todo
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 65.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = horizontalAlignment
            ) {
/*                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data()
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(75.dp)
                        .wrapContentHeight()
                        .padding(top = 7.dp)
                )*/
                Image(
                    painter = painterResource(id = R.drawable.baseline_live_tv_24),
                    contentDescription = null,
                    modifier = Modifier
                        .width(75.dp)
                        .wrapContentHeight()
                        .padding(top = 7.dp)
                )
                TextFormat(
                    value = weather?.tempMin?.plus(" °C") ?: "N/A",
                    color = color,
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}

@Composable
fun Calendar(
    color: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
    size: Float,
    format: String,
) {
    val resolver = LocalFontFamilyResolver.current
    val face: Typeface = remember(resolver, style) {
        resolver.resolve(
            fontWeight = FontWeight.Bold
        )
    }.value as Typeface

    AndroidView(
        factory = { context ->
            TextClock(context).apply {
                format12Hour?.let {
                    this.format12Hour = format
                }
                timeZone?.let {
                    this.timeZone = it
                }
                textSize.let {
                    this.textSize = size
                }
                typeface = face
                setTextColor(color.toColorInt())
            }
        }, modifier = modifier
    )
}