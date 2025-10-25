package com.example.myapplication.errors.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.Errors
import com.example.myapplication.ErrorsDetails
import com.example.myapplication.errors.presentation.MockData
import com.example.myapplication.errors.presentation.model.ErrorsListViewState
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.errors.presentation.viewModel.ErrorsListViewModel
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import com.example.myapplication.uikit.FullscreenError
import com.example.myapplication.uikit.FullscreenLoading
import org.koin.androidx.compose.koinViewModel

//@Composable
//fun ErrorsListScreen(topLevelBackStack: TopLevelBackStack<Route>) {
//    val errors = remember { MockData.getErrors() }
//
//    LazyColumn {
//        errors.forEach { errors ->
//            item(key = errors.code) {
//                ErrorsListItem(errors) { topLevelBackStack.addTopLevel(ErrorsDetails(it)) }
//            }
//        }
//    }
//}

@Composable
fun ErrorsListScreen(){
    val viewModel = koinViewModel<ErrorsListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    ErrorsListScreenContent(
        state.state,
        viewModel::onErrorClick,
        viewModel::onRetryClick,
        viewModel::onSettingsClick
    )
}

@Composable
private fun ErrorsListScreenContent(
    state: ErrorsListViewState.State,
    onErrorsClick: (ErrorsUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onSettingsClick() }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        },
        contentWindowInsets = WindowInsets(left = 0.dp),
    ) {
        Box(Modifier.padding(it))

        when (state) {
            ErrorsListViewState.State.Loading -> {
                FullscreenLoading()
            }

            is ErrorsListViewState.State.Fault -> {
                FullscreenError(
                    retry = { onRetryClick() },
                    text = state.fault
                )
            }

            is ErrorsListViewState.State.Success -> {
                LazyColumn {
                    state.data.forEach { error ->
                        item {
                            ErrorsListItem(error) { onErrorsClick(it) }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ErrorsListItem(errors: ErrorsUiModel, onErrorsClick: (ErrorsUiModel) -> Unit){
    Column(
        modifier = Modifier
            .clickable { onErrorsClick(errors) }
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text (
            text = errors.code,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = errors.title,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = errors.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorsListPreview() {
    ErrorsListScreen()
}