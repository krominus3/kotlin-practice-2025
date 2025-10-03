package com.example.myapplication.errors.presentation

import com.example.myapplication.errors.presentation.model.ErrorsUiModel

object MockData {
    fun getErrors(): List<ErrorsUiModel> = listOf(
        ErrorsUiModel(
            code = "400",
            title = "Bad Request",
            imageUrl = "https://http.cat/status/400.jpg",
            description = "Сервер не может обработать запрос из-за синтаксической ошибки в запросе клиента",
            seeAlso = "Проверьте синтаксис запроса и параметры",
            source = "RFC 7231, Section 6.5.1"
        ),
        ErrorsUiModel(
            code = "401",
            title = "Unauthorized",
            imageUrl = "https://http.cat/status/401.jpg",
            description = "Требуется аутентификация для доступа к запрашиваемому ресурсу",
            seeAlso = "Учетные данные не предоставлены или неверны",
            source = "RFC 7235, Section 3.1"
        ),
        ErrorsUiModel(
            code = "403",
            title = "Forbidden",
            imageUrl = "https://http.cat/status/403.jpg",
            description = "Сервер понял запрос, но отказывается его авторизовать",
            seeAlso = "Проверьте права доступа к ресурсу",
            source = "RFC 7231, Section 6.5.3"
        ),
        ErrorsUiModel(
            code = "404",
            title = "Not Found",
            imageUrl = "https://http.cat/status/404.jpg",
            description = "Сервер не может найти запрашиваемый ресурс",
            seeAlso = "Проверьте правильность URL-адреса",
            source = "RFC 7231, Section 6.5.4"
        ),
        ErrorsUiModel(
            code = "405",
            title = "Method Not Allowed",
            imageUrl = "https://http.cat/status/405.jpg",
            description = "Метод, указанный в запросе, не поддерживается для целевого ресурса",
            seeAlso = "Используйте допустимый HTTP-метод для данного ресурса",
            source = "RFC 7231, Section 6.5.5"
        ),
        ErrorsUiModel(
            code = "408",
            title = "Request Timeout",
            imageUrl = "https://http.cat/status/408.jpg",
            description = "Сервер ожидал запрос в течение установленного времени, но не получил его",
            seeAlso = "Повторите запрос с тем же самым запросом",
            source = "RFC 7231, Section 6.5.7"
        ),
        ErrorsUiModel(
            code = "429",
            title = "Too Many Requests",
            imageUrl = "https://http.cat/status/429.jpg",
            description = "Пользователь отправил слишком много запросов за заданный период времени",
            seeAlso = "Подождите перед отправкой следующего запроса",
            source = "RFC 6585, Section 4"
        ),
        ErrorsUiModel(
            code = "500",
            title = "Internal Server Error",
            imageUrl = "https://http.cat/status/500.jpg",
            description = "Сервер столкнулся с непредвиденной ситуацией, которая не позволяет ему выполнить запрос",
            seeAlso = "Попробуйте повторить запрос позже",
            source = "RFC 7231, Section 6.6.1"
        ),
        ErrorsUiModel(
            code = "502",
            title = "Bad Gateway",
            imageUrl = "https://http.cat/status/502.jpg",
            description = "Сервер, действуя как шлюз или прокси, получил недопустимый ответ от вышестоящего сервера",
            seeAlso = "Проверьте настройки шлюза и вышестоящего сервера",
            source = "RFC 7231, Section 6.6.3"
        ),
        ErrorsUiModel(
            code = "503",
            title = "Service Unavailable",
            imageUrl = "https://http.cat/status/503.jpg",
            description = "Сервер в настоящее время не может обрабатывать запросы из-за временной перегрузки или технического обслуживания",
            seeAlso = "Повторите попытку после задержки, указанной в заголовке Retry-After",
            source = "RFC 7231, Section 6.6.4"
        ),
        ErrorsUiModel(
            code = "504",
            title = "Gateway Timeout",
            imageUrl = "https://http.cat/status/504.jpg",
            description = "Сервер, действуя как шлюз или прокси, не дождался ответа от вышестоящего сервера",
            seeAlso = "Проверьте доступность вышестоящего сервера",
            source = "RFC 7231, Section 6.6.5"
        ),
        ErrorsUiModel(
            code = "505",
            title = "HTTP Version Not Supported",
            imageUrl = "https://http.cat/status/505.jpg",
            description = "Сервер не поддерживает версию HTTP протокола, используемую в запросе",
            seeAlso = "Используйте поддерживаемую версию HTTP протокола",
            source = "RFC 7231, Section 6.6.6"
        ),
        ErrorsUiModel(
            code = "511",
            title = "Network Authentication Required",
            imageUrl = "https://http.cat/status/511.jpg",
            description = "Требуется аутентификация в сети для получения доступа к ресурсу",
            seeAlso = "Пройдите аутентификацию в сети (например, через портал захвата)",
            source = "RFC 6585, Section 6"
        ),
        ErrorsUiModel(
            code = "418",
            title = "I'm a teapot",
            imageUrl = "https://http.cat/status/418.jpg",
            description = "Сервер отказывается заваривать кофе, потому что он является чайником",
            seeAlso = "Это код ошибки был первоапрельской шуткой в RFC 2324",
            source = "RFC 2324, Hyper Text Coffee Pot Control Protocol"
        ),
        ErrorsUiModel(
            code = "451",
            title = "Unavailable For Legal Reasons",
            imageUrl = "https://http.cat/status/451.jpg",
            description = "Доступ к ресурсу ограничен по юридическим причинам",
            seeAlso = "Обратитесь к юридическому уведомлению для получения дополнительной информации",
            source = "RFC 7725"
        )
    )
}