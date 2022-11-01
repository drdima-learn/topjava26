package ru.javawebinar.topjava.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.DublicateDateTimeException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtils.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_100_003, USER_ID);
        assertMatch(meal, MEAL_100_003);
    }


    @Test
    public void getForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_100_003, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_100_003, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_100_003, USER_ID));
    }

    @Test
    public void deleteForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_100_003, ADMIN_ID));
    }

    @Ignore
    @Test
    public void getBetweenInclusive() {
        LocalDateTime startDateTime = LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, Month.JANUARY, 30, 14, 0);
        List<Meal> mealsUserBetween = service.getBetweenInclusive(startDateTime.toLocalDate(), endDateTime.toLocalDate(), USER_ID);
        assertMatch(mealsUserBetween, MEALS_USER_BETWEEN);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEALS_USER);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID_100_003, USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateForeignMeal() {
        Meal updated = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));

    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void createDuplicateDateTime() {
        Meal meal = MealTestData.getNew();
        meal.setDateTime(MEAL_100_003.getDateTime());
        assertThrows(DublicateDateTimeException.class, () -> service.create(meal, USER_ID));
    }
}