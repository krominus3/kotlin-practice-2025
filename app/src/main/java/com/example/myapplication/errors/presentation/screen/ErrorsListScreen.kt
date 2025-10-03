package com.example.myapplication.errors.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.errors.presentation.MockData
import com.example.myapplication.errors.presentation.model.ErrorsUiModel

@Composable
fun ErrorsListScreen() {
    val errors = remember { MockData.getErrors() }

    LazyColumn {
        errors.forEach { errors ->
            item(key = errors.code) {
                ErrorsListItem(errors)
            }
        }
    }
}

@Composable
fun ErrorsListItem(errors: ErrorsUiModel){
    Column(
        modifier = Modifier
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