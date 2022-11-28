# photo-unsplash

Приложение для просмотра фотографий работающее с Unsplash API

![](lessonsProject/Screen_1.jpg)

Приложение для популярного сервиса с фотографиями Unsplash.
Позволяет смотреть фотографии, коллекции фотографий.
Позволяет лайкать и скачивать понравившиеся фотографии.

Навигация устроена на основе Jetpack Navigation.

При первом запуске отображается экран onboarding. Реализован ViewPager2 с RecyclerView Adapter.

Далее переходим на экран авторизации, где при помощи библиотеки OAuth 2 проходит аутентификация пользователя на портале Unsplash и получение access token.

В основной части три раздела в bottom navigation menu: «Фотографии»,
«Коллекции», «Пользователь».

Стек технологий: Dagger Hilt, Retrofit 2, Jetpack Navigation, Jetpack Paging 3, Room 
