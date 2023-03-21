# ApiGitHub
# Разработка android-приложения для просмотра GitHub репозиториев
## Функциональные требования
- :ballot_box_with_check: Авторизация пользователя (personal access token)
- :ballot_box_with_check:  Просмотр списка репозиториев пользователя (первые 10)
- :black_square_button: Просмотр детальной информации выбранного репозитория
    - :ballot_box_with_check:  статистика (forks, stars, watchers)
    - :ballot_box_with_check:  ссылка на web страницу репозитория
    - :ballot_box_with_check:  лицензия
    - :ballot_box_with_check: readme
## Технические требования
- :ballot_box_with_check: Реализация на Kotlin
- :ballot_box_with_check: Использовать XML Layouts для UI
- :ballot_box_with_check: Использовать Kotlin Gradle DSL
- :ballot_box_with_check: Использовать Retrofit для работы с REST API
- :ballot_box_with_check: Использовать RecyclerView для отображения списка
- :ballot_box_with_check: Использовать ConstraintLayout для экрана детальной информации
- :ballot_box_with_check: Использовать Android Navigation Component для переходов между экранами
- :ballot_box_with_check: Использовать View Binding для связывания верстки с кодом
- :ballot_box_with_check: Экраны делать с помощью Fragment (подход Single Activity)
- :ballot_box_with_check: Использовать Coroutines для асинхронности и многопоточности
- :ballot_box_with_check: Использовать Kotlinx.Serialization для парсинга json
- :ballot_box_with_check: Использовать ViewModel для реализации логики экранов
- :ballot_box_with_check: Использовать LiveData / StateFlow для обновления данных на UI
- :black_square_button: Использовать Dagger Hilt для внедрения зависимостей
- :ballot_box_with_check: Сохранять токен авторизации в хранилище устройства - SharedPreferences
- :ballot_box_with_check: Корректно обрабатывать ситуации "загрузка данных", "ошибка загрузки", "пустой список"
- :ballot_box_with_check: Корректно обрабатывать смену конфигурации
- :ballot_box_with_check: При перезапуске приложения авторизация должна сохраняться
- :ballot_box_with_check: Использовать локализацию для всех строк, показываемых пользователю
- :ballot_box_with_check: Использовать векторную графику везде, где это возможно
- :ballot_box_with_check: Обеспечить поддержку Android API 21
