package com.bittelasia.theme.presenter.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.theme.R
import com.bittelasia.theme.components.CustomDivider
import com.bittelasia.theme.components.TextFormat
import com.bittelasia.theme.components.capitalizeWord
import com.bittelasia.theme.components.customDate

@Composable
fun DailyWeatherItem(
    weatherUpdate: WeatherData?,
    color: String,
) {
    val context = LocalContext.current
    Box(modifier = Modifier.padding(horizontal = 5.dp)) {
        Box(
            modifier = Modifier
                .width(170.dp)
                .wrapContentHeight()
        ) {
            Row {
                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    TextFormat(
                        value = customDate(
                            oldDate = weatherUpdate?.date,
                            oldFormat = "yyyy-MM-dd",
                            newFormat = "EEEE"
                        ),
                        color = color,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_live_tv_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                    )
/*                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(getDrawableResource(drawableResId = weatherUpdate?.icon))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                    )*/
                    TextFormat(
                        value = weatherUpdate?.tempMin.plus(" °C"),
                        color = color,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 23.sp
                        )
                    )
                    TextFormat(
                        value = capitalizeWord(weatherUpdate?.description),
                        color = color,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
                CustomDivider(color = "#000000")
            }
        }
    }
}
