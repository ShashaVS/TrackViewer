package com.shashavs.trackviewer.ui.main

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    /*val items = listOf(
        TabPage(
            title = stringResource(id = R.string.timer),
            icon = painterResource(id = R.drawable.ic_timer),
            destination = Route.Dashboard
        ),
        TabPage(
            title = stringResource(id = R.string.history),
            icon = painterResource(id = R.drawable.ic_history),
            destination = Route.History
        ),
        TabPage(
            title = stringResource(id = R.string.goals),
            icon = painterResource(id = R.drawable.ic_check_circle_outline),
            destination = Route.Goals
        ),
        TabPage(
            title = stringResource(id = R.string.reports),
            icon = painterResource(id = R.drawable.ic_bar_chart),
            destination = Route.Reports
        ),
        TabPage(
            title = stringResource(id = R.string.more),
            icon = painterResource(id = R.drawable.ic_more_vert),
            destination = Route.More
        )
    )*/

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
//                    Text(getTopBarTitle(currentTabPage.destination))    // TODO check
                }
            )
        },

    ) { innerPadding ->
//        AppNavigation(navController, innerPadding)
    }
}