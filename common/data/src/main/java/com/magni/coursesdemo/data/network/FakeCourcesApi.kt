package com.magni.coursesdemo.data.network

import com.magni.coursesdemo.data.R
import kotlinx.coroutines.delay

class FakeCourcesApi() : CourcesApi {
    override suspend fun getCourses(): CoursesResponse {
        // Simulate network delay
        delay(1000)

        // Return complete mock data
        val packageName = "com.magni.coursesdemo"
        return CoursesResponse(
            courses = listOf(
                CourceData(
                    id = 100,
                    title = "Java-разработчик с нуля",
                    text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
                    price = "999",
                    rate = "4.9",
                    startDate = "2024-05-22",
                    hasLike = false,
                    publishDate = "2024-02-02",
                    imageUrl = "android.resource://${packageName}/${R.mipmap.course_1}"
                ),
                CourceData(
                    id = 101,
                    title = "3D-дженералист",
                    text = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
                    price = "12 000",
                    rate = "3.9",
                    startDate = "2024-09-10",
                    hasLike = false,
                    publishDate = "2024-01-20",
                    imageUrl = "android.resource://${packageName}/${R.mipmap.course_2}"
                ),
                CourceData(
                    id = 102,
                    title = "Python Advanced. Для продвинутых",
                    text = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения на языке Kotlin. Преподаватели на вебинарах покажут пример того, как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения",
                    price = "1 299",
                    rate = "4.3",
                    startDate = "2024-10-12",
                    hasLike = true,
                    publishDate = "2024-08-10",
                    imageUrl = "android.resource://${packageName}/${R.mipmap.course_3}"
                ),
                CourceData(
                    id = 103,
                    title = "Системный аналитик",
                    text = "Освоите навыки системной аналитики с нуля за 9 месяцев. Будет очень много практики на реальных проектах, чтобы вы могли сразу стартовать в IT.",
                    price = "1 199",
                    rate = "4.5",
                    startDate = "2024-04-15",
                    hasLike = false,
                    publishDate = "2024-01-13",
                    imageUrl = null
                ),
                CourceData(
                    id = 104,
                    title = "Аналитик данных",
                    text = "В этом уроке вы узнаете, кто такой аналитик данных и какие задачи он решает. А главное — мы расскажем, чему вы научитесь по завершении программы обучения профессии «Аналитик данных».",
                    price = "899",
                    rate = "4.7",
                    startDate = "2024-06-20",
                    hasLike = false,
                    publishDate = "2024-03-12",
                    imageUrl = null
                )
            )
        )
    }
}