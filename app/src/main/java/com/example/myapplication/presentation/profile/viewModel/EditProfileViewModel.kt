package com.example.myapplication.presentation.profile.viewModel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.myapplication.domain.repository.IProfileRepository
import com.example.myapplication.presentation.profile.model.state.EditProfileState
import com.example.myapplication.presentation.profile.notification.NotificationReceiver
import androidx.core.net.toUri
import java.util.Calendar
import java.util.regex.Pattern

class EditProfileViewModel(
    private val repository: IProfileRepository
): ViewModel() {

    private val mutableState = MutableEditProfileState()
    val viewState = mutableState as EditProfileState

    var favoriteClassTime by mutableStateOf("")
        private set

    var isTimeValid by mutableStateOf(true)
        private set

    var showTimeError by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            repository.getProfile()?.let {
                mutableState.name = it.name
                mutableState.url = it.url
                mutableState.photoUri = it.photoUri.toUri()
                favoriteClassTime = it.favoriteClassTime ?: ""
                isTimeValid = validateTime(favoriteClassTime)
            }
        }
        mutableState.isNeedToShowPermission = true
    }

    fun onNameChanged(name: String) {
        mutableState.name = name
    }

    fun onUrlChanged(url: String) {
        mutableState.url = url
    }

    fun onTimeChanged(newTime: String) {
        favoriteClassTime = newTime
        isTimeValid = validateTime(newTime)
        showTimeError = newTime.isNotEmpty() && !isTimeValid
    }

    private fun validateTime(time: String): Boolean {
        if (time.isEmpty()) return true

        val pattern = Pattern.compile("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")
        return pattern.matcher(time).matches()
    }

    fun onDoneClicked(context: Context) {
        // Проверяем валидность перед сохранением
        if (favoriteClassTime.isNotEmpty() && !isTimeValid) {
            showTimeError = true
            return
        }

        viewModelScope.launch {
            // Сохраняем профиль вместе с временем пары
            repository.setProfile(
                photoUri = mutableState.photoUri.toString(),
                name = viewState.name,
                url = viewState.url,
                favoriteClassTime = favoriteClassTime
            )

            // Устанавливаем уведомление, если время валидно и не пустое
            if (favoriteClassTime.isNotEmpty() && isTimeValid) {
                scheduleNotification(context)
            } else {
                Toast.makeText(context, "Профиль сохранен", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scheduleNotification(context: Context) {
        try {
            // Проверяем разрешения для Android 12+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                if (!alarmManager.canScheduleExactAlarms()) {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    Toast.makeText(context, "Разрешите точные будильники в настройках", Toast.LENGTH_LONG).show()
                    return
                }
            }

            // Проверяем время
            val timeParts = favoriteClassTime.split(":")
            if (timeParts.size != 2) {
                Toast.makeText(context, "Неверный формат времени", Toast.LENGTH_SHORT).show()
                return
            }

            val hours = timeParts[0].toInt()
            val minutes = timeParts[1].toInt()

            // Проверяем валидность времени (24-часовой формат)
            if (hours !in 0..23 || minutes !in 0..59) {
                Toast.makeText(context, "Неверное время. Используйте формат 00:00-23:59", Toast.LENGTH_SHORT).show()
                return
            }

            Log.d("Notification", "Setting alarm for: $hours:$minutes (24h format)")

            // Устанавливаем время в 24-часовом формате
            val calendar = Calendar.getInstance().apply {
                // Явно устанавливаем время в 24-часовом формате
                set(Calendar.HOUR_OF_DAY, hours)  // ВАЖНО: HOUR_OF_DAY вместо HOUR
                set(Calendar.MINUTE, minutes)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                Log.d("Notification", "Calendar set to: ${get(Calendar.HOUR_OF_DAY)}:${get(Calendar.MINUTE)}")
            }

            // Проверяем, не установили ли мы время в прошлом
            val timeDifference = calendar.timeInMillis - System.currentTimeMillis()
            Log.d("Notification", "Time difference: ${timeDifference / (1000 * 60)} minutes")

            if (timeDifference <= 0) {
                // Если время уже прошло, устанавливаем на завтра
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                Log.d("Notification", "Time passed, moved to tomorrow: ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}")
            }

            // Создаем Intent для BroadcastReceiver
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra(NotificationReceiver.EXTRA_CLASS_NAME, "пара по мобильной разработке")
                putExtra(NotificationReceiver.EXTRA_STUDENT_NAME, viewState.name.ifEmpty { "Студент" })
            }

            // Создаем PendingIntent
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                generateNotificationId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Получаем AlarmManager
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // Устанавливаем будильник
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }

            val finalTimeDiff = calendar.timeInMillis - System.currentTimeMillis()
            val hoursUntil = finalTimeDiff / (1000 * 60 * 60)
            val minutesUntil = (finalTimeDiff % (1000 * 60 * 60)) / (1000 * 60)

            val timeText = when {
                hoursUntil > 0 -> "через ${hoursUntil}ч ${minutesUntil}мин"
                minutesUntil > 0 -> "через ${minutesUntil}мин"
                else -> "сейчас"
            }

            val dayText = if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                "сегодня"
            } else {
                "завтра"
            }

            Toast.makeText(context, "Напоминание $dayText в $favoriteClassTime ($timeText)", Toast.LENGTH_LONG).show()
            Log.d("Notification", "Alarm successfully set!")

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Notification", "Error: ${e.message}")
            Toast.makeText(context, "Ошибка: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun generateNotificationId(): Int {
        return (System.currentTimeMillis() % Integer.MAX_VALUE).toInt()
    }

    fun onImageSelected(uri: Uri?) {
        uri?.let { mutableState.photoUri = it }
    }

    fun onPermissionClosed() {
        mutableState.isNeedToShowPermission = false
    }

    fun onAvatarClicked() {
        mutableState.isNeedToShowSelect = true
    }

    fun onSelectDismiss() {
        mutableState.isNeedToShowSelect = false
    }

    fun onTimeFieldFocused() {
        showTimeError = false
    }

    private class MutableEditProfileState : EditProfileState {
        override var photoUri: Uri by mutableStateOf(Uri.EMPTY)
        override var name by mutableStateOf("")
        override var url by mutableStateOf("")
        override var isNeedToShowPermission by mutableStateOf(false)
        override var isNeedToShowSelect: Boolean by mutableStateOf(false)
    }
}