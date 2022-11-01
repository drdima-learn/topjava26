package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MealTestData {

    public static final int MEAL_ID_100_003 = 100_003;
    public static final int MEAL_ID_100_004 = 100_004;
    public static final int MEAL_ID_100_005 = 100_005;
    public static final int MEAL_ID_100_006 = 100_006;
    public static final int MEAL_ID_100_007 = 100_007;
    public static final int MEAL_ID_100_008 = 100_008;
    public static final int MEAL_ID_100_009 = 100_009;
    public static final int MEAL_ID_100_010 = 100_010;
    public static final int MEAL_ID_100_011 = 100_011;
    public static final int NOT_FOUND = 100;

    public static final Meal MEAL_100_003 = new Meal(MEAL_ID_100_003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_100_004 = new Meal(MEAL_ID_100_004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_100_005 = new Meal(MEAL_ID_100_005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_100_006 = new Meal(MEAL_ID_100_006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_100_007 = new Meal(MEAL_ID_100_007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_100_008 = new Meal(MEAL_ID_100_008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_100_009 = new Meal(MEAL_ID_100_009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    //admin meals
    public static final Meal MEAL_100_010 = new Meal(MEAL_ID_100_010, LocalDateTime.of(2015, Month.JUNE, 01, 14, 0), "Админ ланч", 510);
    public static final Meal MEAL_100_011 = new Meal(MEAL_ID_100_011, LocalDateTime.of(2015, Month.JUNE, 01, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> MEALS_USER = Arrays.asList(MEAL_100_009, MEAL_100_008, MEAL_100_007,
            MEAL_100_006, MEAL_100_005, MEAL_100_004, MEAL_100_003);

    public static final List<Meal> MEALS_ADMIN = Arrays.asList(MEAL_100_011, MEAL_100_010);

    public static final List<Meal> MEALS_USER_BETWEEN = Arrays.asList(MEAL_100_004, MEAL_100_003);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2022, Month.OCTOBER, 31, 21, 40), "New launch", 510);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_100_003);
        updated.setDateTime(LocalDateTime.of(2020, Month.OCTOBER, 31, 05, 44));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }
}
