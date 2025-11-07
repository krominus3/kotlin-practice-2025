package com.example.myapplication.errors.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.errors.presentation.MockData
import com.example.myapplication.errors.presentation.model.ErrorsListFilter
import com.example.myapplication.errors.presentation.model.ErrorsListViewState
import com.example.myapplication.errors.presentation.model.ErrorsSettingState
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.errors.presentation.viewModel.ErrorsListViewModel
import com.example.myapplication.errors.presentation.viewModel.ErrorsSettingsViewModel
import com.example.myapplication.uikit.FullscreenError
import com.example.myapplication.uikit.FullscreenLoading
import com.example.myapplication.uikit.Spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun ErrorsListScreen(){
    val viewModel = koinViewModel<ErrorsListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    ErrorsListScreenContent(
        state,
        viewModel::onErrorClick,
        viewModel::onRetryClick,
        viewModel::onSettingsClick,
        viewModel::onFilterChange,



    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorsListScreenContent(
    state: ErrorsListViewState,
    onErrorsClick: (ErrorsUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onFilterChange: (ErrorsListFilter) -> Unit = {},


) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val settingsViewModel = koinViewModel<ErrorsSettingsViewModel>()
    val settingsState by settingsViewModel.viewState.collectAsStateWithLifecycle()


    Scaffold(
        floatingActionButton = {
            BadgedBox(
                badge = {
                    if (settingsState.descendingSort) {
                        Badge()
                    }
                }
            ) {
                FloatingActionButton(onClick = { onSettingsClick() }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { ErrorsListFilters(state, onFilterChange) },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (state.listState) {
                ErrorsListViewState.State.Loading -> {
                    FullscreenLoading()
                }

                is ErrorsListViewState.State.Fault -> {
                    FullscreenError(
                        retry = { onRetryClick() },
                        text = state.listState.fault
                    )
                }

                is ErrorsListViewState.State.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        state.listState.data.forEach { error ->
                            item {
                                ErrorsListItem(error) { onErrorsClick(it) }
                            }
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

@Composable
private fun ErrorsListFilters(
    state: ErrorsListViewState,
    onFilterChange: (ErrorsListFilter) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = Spacing.small)
    ) {
        state.filters.forEach { filter ->
            FilterChip(
                selected = filter == state.currentFilter,
                label = { Text(text = filter.text) },
                onClick = { onFilterChange(filter) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorsListPreview() {
    ErrorsListScreenContent(
        ErrorsListViewState(ErrorsListViewState.State.Success(MockData.getErrors()))
    )
}