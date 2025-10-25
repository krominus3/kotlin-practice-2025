package com.example.myapplication.errors.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.errors.presentation.model.ErrorsSettingState
import com.example.myapplication.errors.presentation.viewModel.ErrorsSettingsViewModel
import com.example.myapplication.uikit.Spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsSettingsDialog() {
    val viewModel = koinViewModel<ErrorsSettingsViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    NewsSettingsDialog(
        state = state,
        onNewsFirstCheckedChange = viewModel::onSortCheckedChange,
        onBack = viewModel::onBack,
        onSaveClick = viewModel::onSaveClicked,
    )
}

@Composable
fun NewsSettingsDialog(
    state: ErrorsSettingState,
    onNewsFirstCheckedChange: (Boolean) -> Unit = {},
    onBack: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onBack() }
    ) {
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .background(color = MaterialTheme.colorScheme.background)
                .padding(all = Spacing.medium)
        ) {
            Text(
                text = "Настройки сортировки",
                style = MaterialTheme.typography.titleMedium,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = state.sortFromEnd,
                    onCheckedChange = { onNewsFirstCheckedChange(it) }
                )
                Spacer(Modifier.width(width = Spacing.medium))
                Text(text = "По коду ошибки\n(по убыванию)")
            }
            Spacer(Modifier.width(width = Spacing.medium))

            TextButton(
                onClick = onSaveClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsSettingsDialogPreview() {
    NewsSettingsDialog(state = ErrorsSettingState(sortFromEnd = true))
}