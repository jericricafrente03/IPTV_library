package com.bittelasia.theme.presenter.home

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bittelasia.network.domain.model.home.App
import com.bittelasia.network.domain.model.home.ConfigData
import com.bittelasia.network.domain.model.home.CustomerData
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.theme.presenter.home.bottom.HomeBottom
import com.bittelasia.theme.presenter.home.bottom.IconItem
import com.bittelasia.theme.presenter.home.main.HomeBackground
import com.bittelasia.theme.presenter.home.main.ThemeBackground
import com.bittelasia.theme.presenter.home.top.CalendarSection
import com.bittelasia.theme.presenter.home.top.HomePageWeather
import com.bittelasia.theme.presenter.home.top.HomeTop
import com.bittelasia.theme.presenter.home.top.LogoAndGuest
import com.bittelasia.theme.presenter.homenavigation.HomeNavigation
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val themes by viewModel.themesState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.initHomeAPI()
    }

    when (val s = uiState) {
        is HomeUiState.Ready -> {
            when (val x = themes) {
                is ThemesUiState.Ready -> {
                    Home(
                        app = s.app,
                        config = s.config,
                        weather = s.weather,
                        cs = s.customer,
                        topThemes = x.topTheme,
                        centerThemes = x.welcomeText,
                        bottomThemes = x.appTheme
                    )
                }

                is ThemesUiState.Loading -> Log.v("meme", "ThemesUiState data loading ->")
            }
        }

        is HomeUiState.Loading -> Log.v("meme", "HomeUiState data loading ->")
        is HomeUiState.Error -> Log.v("meme", "data error ->")
    }


}


@Composable
fun Home(
    app: List<App>?,
    config: ConfigData?,
    weather: WeatherData?,
    cs: CustomerData?,
    topThemes: Zones?,
    centerThemes: Zones?,
    bottomThemes: Zones?
) {


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (mainBG,
            topBar,
            welcomeMessage,
            bottomBar) = createRefs()
        val topPadding = createGuidelineFromTop(.305f)
        val bottomPadding = createGuidelineFromBottom(.27f)

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestinations = navBackStackEntry?.destination
        val bottomBarDestination = app?.any {
            it.displayName == currentDestinations?.route
        }
        if (!bottomBarDestination!! && currentDestinations?.route == "home") {
            HomeBackground(
                modifier = Modifier.constrainAs(mainBG) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }, imageUrl = config?.bg
            )
            HomeBottom(
                modifier = Modifier.constrainAs(bottomBar) {
                    top.linkTo(bottomPadding)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(139.dp)
                }, appData = app,
                zones = bottomThemes
            ) { nav ->
                if (nav.displayName == "TV") {
//                    context.startActivity(Intent(context, TVPlayerActivity::class.java))
                } else {
                    navController.navigate(nav.displayName)
                }
            }
        }
        HomeNavigation(
            navController = navController,
            zones = topThemes,
            weather = weather,
            customerData = cs,
            configData = config
        )
    }

}


@Composable
fun WelcomeMessage(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(Color.Magenta)) {

    }
}

