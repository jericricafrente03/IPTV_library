package com.bittelasia.theme.presenter.home.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bittelasia.core_datastore.model.STB
import com.bittelasia.network.domain.model.home.Zones

@Composable
fun ThemeBackground(
    modifier: Modifier = Modifier,
    zone: Zones,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(modifier = modifier, contentAlignment = contentAlignment) {
        zone.url?.let { HomeBackground(imageUrl = it) }
        content()
    }
}

@Composable
fun HomeBackground(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds
){
    val myBaseURL = STB.HOST+":"+STB.PORT
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .data(myBaseURL.plus(imageUrl))
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = contentScale
    )
}