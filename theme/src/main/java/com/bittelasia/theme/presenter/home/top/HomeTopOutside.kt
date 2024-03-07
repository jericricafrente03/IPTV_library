package com.bittelasia.theme.presenter.home.top

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.components.TextFormat
import com.bittelasia.theme.presenter.home.main.ThemeBackground

@Composable
fun HomeTopOutside(
    zones: Zones?,
    weather: WeatherData?,
    customerData: CustomerData?,
    configData: ConfigData?,
    pageTitleTxt: String?
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
                    LogoAndGuestOutSide(
                        guestName = fullName,
                        logo = configData!!.logo,
                        color = zones.textColor,
                        pageTitle = pageTitleTxt
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
private fun LogoAndGuestOutSide(
    logo: String?,
    guestName: String,
    color: String,
    pageTitle: String?
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
        GuestSectionOutside(
            guestTxt = guestName,
            pageTitleTxt = pageTitle,
            textColor = color,
        )
    }
}


@Composable
private fun GuestSectionOutside(
    guestTxt: String?,
    pageTitleTxt: String?,
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
            contentAlignment = Alignment.CenterStart,
        ) {
            (if (guestTxt != "") guestTxt else "Welcome Guest")?.let {
                TextFormat(
                    value = it,
                    color = textColor,
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    )
                )
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(.033f))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            pageTitleTxt?.let { title ->
                TextFormat(
                    value = title,
                    color = textColor,
                    textStyle = TextStyle(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
