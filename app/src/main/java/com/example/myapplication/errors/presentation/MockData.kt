package com.example.myapplication.errors.presentation

import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.errors.presentation.model.SeeAlsoModel

object MockData {
    fun getErrors(): List<ErrorsUiModel> = listOf(
        ErrorsUiModel(
            code = "400",
            title = "Bad Request",
            imageUrl = "https://http.cat/400.jpg",
            description = "Сервер не может обработать запрос из-за синтаксической ошибки в запросе клиента",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.400"),
                SeeAlsoModel("MDN Web Docs", "https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400")
            ),
            source = "RFC 7231, Section 6.5.1"
        ),
        ErrorsUiModel(
            code = "401",
            title = "Unauthorized",
            imageUrl = "https://http.cat/401.jpg",
            description = "Требуется аутентификация для доступа к запрашиваемому ресурсу",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.401"),
                SeeAlsoModel("403 Forbidden", "https://http.cat/status/403")
            ),
            source = "RFC 7235, Section 3.1"
        ),
        ErrorsUiModel(
            code = "403",
            title = "Forbidden",
            imageUrl = "https://http.cat/403.jpg",
            description = "Сервер понял запрос, но отказывается его авторизовать",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.403"),
                SeeAlsoModel("401 Unauthorized", "https://http.cat/status/401")
            ),
            source = "RFC 7231, Section 6.5.3"
        ),
        ErrorsUiModel(
            code = "404",
            title = "Not Found",
            imageUrl = "https://http.cat/404.jpg",
            description = "Сервер не может найти запрашиваемый ресурс",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.404"),
                SeeAlsoModel("410 Gone", "https://http.cat/status/410")
            ),
            source = "RFC 7231, Section 6.5.4"
        ),
        ErrorsUiModel(
            code = "405",
            title = "Method Not Allowed",
            imageUrl = "https://http.cat/405.jpg",
            description = "Метод, указанный в запросе, не поддерживается для целевого ресурса",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.405"),
                SeeAlsoModel("Allow Header", "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Allow")
            ),
            source = "RFC 7231, Section 6.5.5"
        ),
        ErrorsUiModel(
            code = "408",
            title = "Request Timeout",
            imageUrl = "https://http.cat/408.jpg",
            description = "Сервер ожидал запрос в течение установленного времени, но не получил его",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.408"),
                SeeAlsoModel("504 Gateway Timeout", "https://http.cat/status/504")
            ),
            source = "RFC 7231, Section 6.5.7"
        ),
        ErrorsUiModel(
            code = "429",
            title = "Too Many Requests",
            imageUrl = "https://http.cat/429.jpg",
            description = "Пользователь отправил слишком много запросов за заданный период времени",
            seeAlso = listOf(
                SeeAlsoModel("RFC 6585", "https://datatracker.ietf.org/doc/html/rfc6585#section-4"),
                SeeAlsoModel("Retry-After Header", "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Retry-After")
            ),
            source = "RFC 6585, Section 4"
        ),
        ErrorsUiModel(
            code = "500",
            title = "Internal Server Error",
            imageUrl = "https://http.cat/500.jpg",
            description = "Сервер столкнулся с непредвиденной ситуацией, которая не позволяет ему выполнить запрос",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.500"),
                SeeAlsoModel("502 Bad Gateway", "https://http.cat/status/502")
            ),
            source = "RFC 7231, Section 6.6.1"
        ),
        ErrorsUiModel(
            code = "502",
            title = "Bad Gateway",
            imageUrl = "https://http.cat/502.jpg",
            description = "Сервер, действуя как шлюз или прокси, получил недопустимый ответ от вышестоящего сервера",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.502"),
                SeeAlsoModel("504 Gateway Timeout", "https://http.cat/status/504")
            ),
            source = "RFC 7231, Section 6.6.3"
        ),
        ErrorsUiModel(
            code = "503",
            title = "Service Unavailable",
            imageUrl = "https://http.cat/503.jpg",
            description = "Сервер в настоящее время не может обрабатывать запросы из-за временной перегрузки или технического обслуживания",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.503"),
                SeeAlsoModel("Retry-After Header", "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Retry-After")
            ),
            source = "RFC 7231, Section 6.6.4"
        ),
        ErrorsUiModel(
            code = "504",
            title = "Gateway Timeout",
            imageUrl = "https://http.cat/504.jpg",
            description = "Сервер, действуя как шлюз или прокси, не дождался ответа от вышестоящего сервера",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.504"),
                SeeAlsoModel("408 Request Timeout", "https://http.cat/status/408")
            ),
            source = "RFC 7231, Section 6.6.5"
        ),
        ErrorsUiModel(
            code = "505",
            title = "HTTP Version Not Supported",
            imageUrl = "https://http.cat/505.jpg",
            description = "Сервер не поддерживает версию HTTP протокола, используемую в запросе",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.505"),
                SeeAlsoModel("HTTP/2", "https://httpwg.org/specs/rfc9113.html")
            ),
            source = "RFC 7231, Section 6.6.6"
        ),
        ErrorsUiModel(
            code = "413",
            title = "Payload Too Large",
            imageUrl = "https://http.cat/413.jpg",
            description = "Размер запроса превышает лимит, установленный сервером",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.413"),
                SeeAlsoModel("414 URI Too Long", "https://http.cat/status/414")
            ),
            source = "RFC 7231, Section 6.5.11"
        ),
        ErrorsUiModel(
            code = "414",
            title = "URI Too Long",
            imageUrl = "https://http.cat/414.jpg",
            description = "Длина URI превышает допустимый предел сервера",
            seeAlso = listOf(
                SeeAlsoModel("RFC 9110", "https://httpwg.org/specs/rfc9110.html#status.414"),
                SeeAlsoModel("413 Payload Too Large", "https://http.cat/status/413")
            ),
            source = "RFC 7231, Section 6.5.12"
        ),
        ErrorsUiModel(
            code = "418",
            title = "I'm a teapot",
            imageUrl = "https://http.cat/418.jpg",
            description = "Сервер отказывается заваривать кофе, потому что он является чайником",
            seeAlso = listOf(
                SeeAlsoModel("RFC 2324", "https://datatracker.ietf.org/doc/html/rfc2324"),
                SeeAlsoModel("HTCPCP", "https://en.wikipedia.org/wiki/Hyper_Text_Coffee_Pot_Control_Protocol")
            ),
            source = "RFC 2324, Section 2.3.2"
        )
    )
}