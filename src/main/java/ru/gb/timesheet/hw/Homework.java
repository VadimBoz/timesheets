package ru.gb.timesheet.hw;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Homework {

    /**
     * 1. В LoggingAspect добавить логирование типов и значений аргументов.
     * Например (пример вывода): TimesheetService.findById(Long = 3)
     * Эту информацию можно достать из joinPoint.getArgs()
     *
     * 2. * Создать аспект, который аспектирует методы, помеченные аннотацией Recover, и делает следующее:
     * 2.1 Если в процессе исполнения метода был exception (любой),
     * то его нужно залогировать ("Recovering TimesheetService#findById after Exception[RuntimeException.class, "exception message"]")
     * и вернуть default-значение наружу Default-значение: для примитивов значение по умолчанию, для ссылочных типов - null.
     * Для void-методов возвращать не нужно.
     *
     * 3. **** В аннотацию Recover добавить атрибут Class<?>[] noRecoverFor, в которое можно записать список классов исключений,
     * которые НЕ нужно отлавливать.
     * Это вхождение должно учитывать иерархию классов.
     *
     * Пример:
     * @Recover(noRecoverFor = {NoSuchElementException.class, IllegalStateException.class})
     * public Timesheet getById(Long id) {...}
     *
     */

//    @Retention(RetentionPolicy.RUNTIME)
//    @Target(ElementType.METHOD)
//    public @interface Recover { // recover - восстанавливать
//    Class<?>[] noRecoverFor() default {};













//   1. Переделать строки RoleName в сущность Role:
//            1.1 Создать отдельную таблицу Role(id, name)
//1.2 Связать User <-> Role отношением ManyToMany
//2. После п.1 подправить формирование ролей в MyCustomUserDetailsService
//
//    В SecurityFilterChain настроить:
//            3.1 Стандартная форма логина
//    3.2 Страницы с проектами доступы пользователям с ролью admin
//    3.2 Страницы с таймшитами доступы пользователям с ролью user
//    3.3 REST-ресурсы доступны пользователям с ролью rest
//
//    **** Для rest-ресурсов НЕ показывать форму логина.
//    Т.е. если пользователь не авторизован, то его НЕ редиректит на форму логина, а сразу показывается 401.
//    Для авто
//






    /**
     * 1. Создать класс Employee - Сотрудник с полями
     * 1.1 Идентификтаор
     * 1.2 Имя (и фамилия)
     * 1.3 ...
     * 1.4 Создать контроллер-сервис-репозиторий для этой сущности
     *
     * 2. В timesheet добавить поле employee типа Employee (или employeeId тип Long)
     * 3. Создать ресурс /employees/{id}/timesheets - получить все таймшиты по сотруднику
     *
     * 4. ** Связываем Project <-> Employee отношением ManyToMany
     * 4.1 Т.е. на проекте может быть несколько сотрдников; один сотрудник может быть на нескольких проектах.
     */

    // POST /timesheets
    // {projectId: 1, employeeId: 3}


    /**
 * ВАЖНО!
 * Перед выполнением ДЗ предполагается, что ДЗ №3 выполнено (т.е. в проекте есть сущность Project).
 * Если это не реализовано - нужно сначала завершить ДЗ №3.
 *
 * 1. Сделать страницу project-page.html по аналогии с timesheet-page.html
 * 2. В timesheets-page в колонку "Проекты" добавить ссылку на проект
 * Для этого необходимо:
 * 2.1 в TimesheetPageDto добавить поле projectId (для создания ссылки)
 * 2.2 в timesheets-page в колонку "проекты" сделать гиперссылку (по аналогии с колонкой "перейти")
 */



  /*
   * 1. Повторить все, что на семниаре
   * 2. В объект timesheet в поле createdAt должно подставляться текущее время на стороне сервера!
   * Т.е. не клиент присылает, а севрер устанавливает. (сервисном слое)
   * 3. Создать отдельный контроллер для проектов (поле Timesheet.project)
   * 3.1 Создать класс Project с полями id, name
   * 3.2 Создать CRUD-контроллер для класса Project, сервис и репозиторий
   * 3.3 В ресурсе Timesheet поле project изменить на projectId
   * 3.4 При создании Timesheet проверять, что project с идентификатором projectId существует
   *
   * * 4. Создать ресурс /projects/{id}/timesheets - загрузить таймашиты для конкретного проекта
   * ** 5. Создать ресурс /timesheets?createdAtAfter=2024-07-04
   *       - ручка для получения всех таймшитов, которые созданы ПОСЛЕ указанного параметра.
   *       Аналогично createdAtBefore
   */

}
