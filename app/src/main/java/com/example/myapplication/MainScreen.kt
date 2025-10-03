package com.example.myapplication


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack

interface TopLevelRoute: Route {
    val icon: ImageVector
}

data object Errors: TopLevelRoute {
    override val icon: ImageVector = Icons.Default.Home
}

data object News: TopLevelRoute {
    override val icon: ImageVector = Icons.AutoMirrored.Default.List
}

@Composable
fun MainScreen() {
    val topLevelBackStack = remember { TopLevelBackStack<Route>(Errors) }

    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Errors, News).forEach { route ->
                NavigationBarItem(
                    icon = { Icon(route.icon, null) },
                    selected = topLevelBackStack.topLevelKey == route,
                    onClick = {
                        topLevelBackStack.addTopLevel(route)
                    }
                )
            }
        }
    }) { padding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            modifier = Modifier.padding(padding),
            entryProvider = entryProvider {
                entry<Errors> {
                    ContentWhite("Errors")
                }
                entry<News> {
                    ContentGreen("News") { }
                }
            }
        )
    }
}

@Composable
fun ContentWhite(text: String){
    Text(
        text = text,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}

@Composable
fun ContentGreen(text: String, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Text(text)
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}