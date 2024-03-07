package com.bittelasia.theme.presenter.home.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.bittelasia.network.domain.model.home.App
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.presenter.home.main.ThemeBackground
import kotlinx.coroutines.delay

@Composable
fun HomeBottom(
    modifier: Modifier = Modifier,
    appData: List<App>?,
    zones: Zones?,
    onMenuSelected: ((menuItem: App) -> Unit)? = null
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(Unit) {
        delay(500)
        focusRequester.requestFocus()
    }

    if (zones != null) {
        ThemeBackground(
            zone = zones,
            modifier = modifier
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                appData?.let { data ->
                    itemsIndexed(data) { index, item ->
                        IconItem(
                            menuItem = item,
                            zone = zones,
                            modifier = Modifier
                                .then(if (index == 0) Modifier.focusRequester(focusRequester) else Modifier)
                        ) {
                            onMenuSelected?.invoke(it)
                        }
                    }
                }
            }
        }
    }
}
