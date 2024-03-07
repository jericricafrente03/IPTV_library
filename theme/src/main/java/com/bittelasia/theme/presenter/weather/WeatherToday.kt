package com.bittelasia.theme.presenter.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.R
import com.bittelasia.theme.components.TextFormat
import com.bittelasia.theme.components.capitalizeWord
import com.bittelasia.theme.presenter.home.main.ThemeBackground


@Composable
fun WeatherTodayContent(
    weather: WeatherData?,
    zone: Zones?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (zone != null) {
        ThemeBackground(zone = zone, modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    TextFormat(
                        value = weather?.tempMin?.plus(" °C") ?: "Not available",
                        color = zone.textColor,
                        textStyle = TextStyle(
                            fontSize = 80.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                    )
                    TextFormat(
                        value = capitalizeWord(weather?.description ?: "Not available"),
                        color = zone.textColor,
                        textStyle = TextStyle(
                            fontSize = 70.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(50.dp))
/*                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(getDrawableResource(weather?.icon))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp),
                    contentScale = ContentScale.Inside
                )*/
                Image(
                    painter = painterResource(id = R.drawable.baseline_live_tv_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp),
                    contentScale = ContentScale.Inside
                )

            }
        }
    }
}



