package ru.javawebinar.topjava.repository.jdbc;

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
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtils.assertMatch;
import static ru.javawebinar.topjava.TestUtils.assertNotIn;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcMealRepositoryTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealRepository repository;

    @Test
    public void create() {
        Meal created = repository.save(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(repository.get(newId, USER_ID), newMeal);
    }

    @Test
    public void create2() {
        Meal meal = MealTestData.getNew();
        repository.save(meal, USER_ID);
        assertThat(meal.getId()).isEqualTo(100_012);
        assertMatch(meal.getId(), 100_012);
    }

    @Test
    public void createDuplicateDateTime() {
        Meal meal = MealTestData.getNew();
        meal.setDateTime(MEAL_100_003.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> repository.save(meal, USER_ID));
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        repository.save(updated, USER_ID);
        assertMatch(repository.get(updated.getId(), USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateForeignMeal() {
        Meal updated = MealTestData.getUpdated();
        Meal updatedFromDb = repository.save(updated, ADMIN_ID);
        assertMatch(updatedFromDb ,null);
        assertMatch(repository.get(updated.getId(), ADMIN_ID), null);
    }

    @Test
    public void delete() {
        boolean isDeleted = repository.delete(MEAL_100_003.getId(), USER_ID);
        assertMatch(isDeleted, true);

        Meal meal = repository.get(MEAL_100_003.getId(), USER_ID);
        assertMatch(meal, null);
    }

    @Test
    public void get() {
        Meal meal = repository.get(MEAL_ID_100_003, USER_ID);
        assertMatch(meal, MEAL_100_003);
    }

    @Test
    public void getAll() {
        List<Meal> mealsUser = repository.getAll(USER_ID);
        assertMatch(mealsUser, MEALS_USER);

        List<Meal> meals_admin = repository.getAll(ADMIN_ID);
        assertMatch(meals_admin, MEALS_ADMIN);

        assertNotIn(mealsUser, meals_admin);
    }

    @Test
    public void getBetweenHalfOpen() {
        LocalDateTime startDateTime = LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, Month.JANUARY, 30, 14, 0);
        List<Meal> mealsUserBetween = repository.getBetweenHalfOpen(startDateTime, endDateTime, USER_ID);
        assertMatch(mealsUserBetween, MEALS_USER_BETWEEN);

    }
}