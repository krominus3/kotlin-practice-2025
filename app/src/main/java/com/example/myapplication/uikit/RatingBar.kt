package com.example.myapplication.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    maxRating: Int = 10,
    onRatingChanged: (Float) -> Unit
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 1..maxRating) {
            IconButton(
                onClick = { onRatingChanged(i.toFloat()) },
                modifier = Modifier.size(35.dp)
            ) {
                Icon(
                    imageVector = if (i <= rating) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    }
}