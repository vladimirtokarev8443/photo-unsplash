# photo-unsplash

Приложение для просмотра фотографий работающее с Unsplash API

![Screen 1](https://github.com/vladimirtokarev8443/lessonsProject/blob/main/screen_1.jpg)    ![Screen 1](https://github.com/vladimirtokarev8443/lessonsProject/blob/main/screen_2.jpg)     ![Screen 1](https://github.com/vladimirtokarev8443/lessonsProject/blob/main/screen_3.jpg)

Приложение для популярного сервиса с фотографиями Unsplash.
Позволяет смотреть фотографии, коллекции фотографий.
Позволяет лайкать и скачивать понравившиеся фотографии.

Навигация устроена на основе Jetpack Navigation.

При первом запуске отображается экран onboarding. Реализован ViewPager2 с RecyclerView Adapter.

Далее переходим на экран авторизации, где при помощи библиотеки OAuth 2 проходит аутентификация пользователя на портале Unsplash и получение access token.

В основной части три раздела в bottom navigation menu: «Фотографии»,
«Коллекции», «Пользователь».

[Hilt, внедрение зависимостей для Retrofit 2](https://github.com/vladimirtokarev8443/photo-unsplash/blob/main/Inspiration/app/src/main/java/com/example/inspiration/di/data/NetworkingModule.kt)

Стек технологий: Dagger Hilt, Retrofit 2, Jetpack Navigation, Jetpack Paging 3, Room 
