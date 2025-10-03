package com.example.myapplication.errors.presentation.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.myapplication.errors.presentation.MockData
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.errors.presentation.model.SeeAlsoModel
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import com.example.myapplication.uikit.RatingBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorsDetailsDialog(
    error: ErrorsUiModel,
    topLevelBackStack: TopLevelBackStack<Route>
) {
    ModalBottomSheet(
        onDismissRequest = { topLevelBackStack.removeLast() },

    ) {
        ErrorsDetailsContent(error)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ErrorsDetailsContent(errors: ErrorsUiModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        var rating by remember { mutableFloatStateOf(0f) }

        val context = LocalContext.current
        Icon(
            Icons.Default.Share,
            null,
            Modifier.clickable {
                shareText(context, "Error ${errors.code} ${errors.title} cat meme")
            }
        )

        Text (
            text = errors.code,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = errors.title,
            style = MaterialTheme.typography.bodyMedium,
        )


        GlideImage(
            model = errors.imageUrl,
            contentDescription = "Кот - мем с ошибкой ${errors.code}",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )

        Text(
            text = "Rate the meme",
            style = MaterialTheme.typography.titleMedium,

        )

        RatingBar(
            rating,
            modifier = Modifier.fillMaxWidth(),
        ) {
            rating = it
        }

        Text(text = if (rating > 0f) "Your assessment: $rating" else "You haven't appreciated the meme yet")

        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = errors.description,
            style = MaterialTheme.typography.bodyMedium,
        )

        if (!errors.seeAlso.isNullOrEmpty()) {
            Text(
                text = "See Also",
                style = MaterialTheme.typography.titleMedium,
            )

            Column {
                errors.seeAlso.forEach { seeAlsoItem ->
                    SeeAlsoListItem(seeAlsoItem)
                }
            }
        }

        errors.source?.let { source ->

        Text(
            text = "Source",
            style = MaterialTheme.typography.titleMedium,
        )


            Text(
                text = source,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

    }
}

fun shareText(context: Context, text: String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plan"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Share with"))
}

@Composable
fun SeeAlsoListItem(seeAlso: SeeAlsoModel) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = seeAlso.text,
            style = MaterialTheme.typography.bodyMedium
        )

        seeAlso.url?.let { url ->
            Text(
                text = url,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorDetailDialogPreview() {
    ErrorsDetailsContent(
        MockData.getErrors().first()
    )
}