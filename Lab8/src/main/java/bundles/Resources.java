package bundles;

import java.util.ListResourceBundle;

public class Resources extends ListResourceBundle {
    private static final Object[][] contents = {
            {"enter","Войти"},
            {"registration","Регистрация"},
            {"exit","Выход"},
            {"flats","Квартиры"},
            {"logging","Вход"},
            {"login","Логин"},
            {"password","Пароль"},
            {"back","Назад"},
            {"register","Зарегистрировать"},
            {"settings","Настройки"},
            {"language","Язык"},
            {"change_user","Сменить пользователя"},
            {"help","Справка по командам"},
            {"info","Информация о коллекции"},
            {"show","Показать всю коллекцию"},
            {"add","Добавить работника"},
            {"clear","Очистить коллекцию"},
            {"remove_last","Удалить последнего"},
            {"average","Ср.знач. \"living space\""},
            {"max","Макс. по году постройки"},
            {"update","Обновить работника"},
            {"remove_by_id","Удалить по ID"},
            {"remove_at","Удалить по индексу"},
            {"execute_script","Выполнить скрипт"},
            {"filter","Фильтр по View"},
            {"list","Список квартир"},
            {"visualization","Визуализация"},
            {"name","Имя"},
            {"coordinates","Координаты"},
            {"area","Площадь"},
            {"number_of_rooms","Количество комнат"},
            {"living_space","Жилплощадь"},
            {"flat","Квартира"},
            {"view","Вид из окна"},
            {"transport","Транспорт"},
            {"house_name","Название дома"},
            {"house_year","Год постройки дома"},
            {"number_of_flats","Количество квартир на этаже"},
            {"type","Ввести"},
            {"nameErr","Имя должно быть непустой строкой"},
            {"xErr","Координата X должна быть числом"},
            {"yErr","Координата Y должна быть числом до 368"},
            {"areaErr","Жилплощадь должна быть положительным числом"},
            {"numberErr","Количество комнат должно быть целым положительным числом"},
            {"livingErr","Площадь жилых комнат должна быть положительным числом"},
            {"hnameErr","Название должно быть непустой строкой"},
            {"hyearErr","Год постройки дома должен быть целым положительным числом"},
            {"hnumberErr","Количество квартир на этаже должно быть целым положительным числом"},
            {"idErr","Ошибка! 'id' должен быть целым положительным числом.\n Повторите ввод команды."},
            {"indexErr","Ошибка! Индекс должен быть целым неотрицательным числом.\n Повторите ввод команды."},
            {"scriptErr","Путь должен быть непустой строкой"},
            {"viewErr","Ошибка. Вы ввели недопустимое значение 'view'.\n"},
            {"showButton","Показать"},
            {"id","ID"},
            {"creationDate","Дата\nсоздания"},
            {"user","Пользователь"},
            {"house","Данные о доме"},
            {"loginTitle", "Попробуй угадай пароль!"},
            {"removeLower", "Удалить наименьшее"},
            {"addifless", "Добавить если меньше"},
            {"table", "таблица"},
            {"visual", "визуализация"},
            {"salary", "Зарплата"},
            {"startAt", "Начало в"},
            {"creation", "Создание"},
            {"position", "Должность"},
            {"status", "Статус"},
            {"height", "Рост"},
            {"eye", "Глаза"},
            {"hair", "Волосы"},
            {"country", "Страна"}

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}