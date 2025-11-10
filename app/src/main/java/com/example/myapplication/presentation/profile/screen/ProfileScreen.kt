package com.example.myapplication.presentation.profile.screen

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.terrakok.modo.stack.forward
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.profile.utils.SystemBroadcastReceiver
import com.example.myapplication.presentation.profile.viewModel.ProfileViewModel
import com.example.myapplication.ui.theme.Typography
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onEditProfile: () -> Unit) {
    val navigation = onEditProfile

    val viewModel = koinViewModel<ProfileViewModel>()
    val state = viewModel.viewState
    val context = LocalContext.current

    InitReceiver(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile)) },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { navigation() }
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = state.photoUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).size(128.dp),
                error = painterResource(R.drawable.ic_launcher_foreground)
            )

            Text(text = state.name, style = Typography.headlineLarge)
            Text(text = "Старший разработчик", style = Typography.labelMedium)

            Button(onClick = {
                enqueueDownload("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf", context)
            }) {
                Text(text = stringResource(R.string.resume))
            }
        }
    }
}

@Composable
private fun InitReceiver(context: Context) {
    SystemBroadcastReceiver(
        systemAction = DownloadManager.ACTION_DOWNLOAD_COMPLETE,
        onSystemEvent = { intent ->
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L) ?: return@SystemBroadcastReceiver
            if (id != -1L) openDownloaded(context, id)
        }
    )
}

private fun enqueueDownload(url: String, context: Context) {
    val fileName = "resume.pdf"

    val request = DownloadManager.Request(Uri.parse(url)).apply {
        setTitle("resume.pdf")
        setDescription("Downloading...")
        setMimeType("application/pdf")
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        addRequestHeader("User-Agent", "Mozilla/5.0")
        addRequestHeader("Accept", "application/pdf")
    }

    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}

private fun openDownloaded(context: Context, downloadId: Long) {
    try {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        if (cursor.moveToFirst()) {
            val status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                val uriString = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    val uri = Uri.parse(uriString)
                    setDataAndType(uri, "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Если не получается открыть PDF, показываем диалог для выбора приложения
                    val openIntent = Intent(Intent.ACTION_VIEW).apply {
                        val uri = Uri.parse(uriString)
                        setDataAndType(uri, "application/pdf")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(Intent.createChooser(openIntent, "Open PDF with"))
                }
            }
        }
        cursor.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}