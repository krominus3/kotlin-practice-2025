package com.example.myapplication


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.errors.presentation.screen.ErrorsDetailsDialog
import com.example.myapplication.errors.presentation.screen.ErrorsListScreen
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import org.koin.java.KoinJavaComponent.inject


interface TopLevelRoute: Route {
    val icon: ImageVector
}

data class ErrorsDetails(val errors: ErrorsUiModel) : Route

data object Errors: TopLevelRoute {
    override val icon: ImageVector = Icons.Default.Home
}

data object ReadMore: TopLevelRoute {
    override val icon: ImageVector = Icons.AutoMirrored.Default.List
}

@Composable
fun MainScreen() {
    val topLevelBackStack by inject<TopLevelBackStack<Route>>(clazz = TopLevelBackStack::class.java)
    val dialogStrategy = remember { DialogSceneStrategy<Route>() }

    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Errors, ReadMore).forEach { route ->
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
            sceneStrategy = dialogStrategy,
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Errors> {
                    ErrorsListScreen(topLevelBackStack)
                }
                entry<ReadMore> {
                    ContentGreen("ReadMore") { }
                }
                entry<ErrorsDetails>(
                    metadata = DialogSceneStrategy.dialog(DialogProperties())
                ) {
                    ErrorsDetailsDialog(it.errors)
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