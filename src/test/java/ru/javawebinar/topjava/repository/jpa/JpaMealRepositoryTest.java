package ru.javawebinar.topjava.repository.jpa;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaMealRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(JpaMealRepositoryTest.class);
    private static final Map<String, Long> testsMap = new HashMap<>();

    class TestStaticstics extends TestWatcher {

        private LocalDateTime dateTimeFrom;

        @Override
        protected void starting(Description description) {
            dateTimeFrom = LocalDateTime.now();
        }

        @Override
        protected void finished(Description description) {
            LocalDateTime dateTimeTo = LocalDateTime.now();
            Duration duration = Duration.between(dateTimeFrom, dateTimeTo);
            long milliSeconds = duration.toMillis();
            log.info("Test Finished by " + milliSeconds + " milliseconds");
            testsMap.put(description.getMethodName(), milliSeconds);
        }


    }

    @Rule
    public TestStaticstics testStaticstics = new TestStaticstics();




    @Autowired
    private MealRepository repository;

    @AfterClass
    public static void afterClass() throws Exception {
        log.info("All tests finished ");
        log.info(testsMap.toString());
    }

    @Test
    public void save() {
        Meal created = repository.save(getNew(), USER_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.get(newId, USER_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updated = repository.save(getUpdated(), USER_ID);
        //MEAL_MATCHER.assertMatch(updated, getUpdated());
    }

    @Test
    public void updateNotOwn() {
        Meal updated = repository.save(getUpdated(), ADMIN_ID);
        MEAL_MATCHER.assertMatch(updated, null);
        MEAL_MATCHER.assertMatch(repository.get(MEAL1_ID, USER_ID), meal1);
    }

    @Test
    public void delete() {
        boolean result = repository.delete(MEAL1_ID, USER_ID);
        Assert.assertTrue(result);
        Meal meal = repository.get(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(meal, null);
    }

    @Test
    public void deleteNotOwn() {
        boolean result = repository.delete(MEAL1_ID, ADMIN_ID);
        Assert.assertFalse(result);
        Meal meal = repository.get(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
    }

    @Test
    public void get() {
        Meal meal = repository.get(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
    }

    @Test
    public void getNotOwn() {
        Meal meal = repository.get(MEAL1_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(meal, null);
    }

    @Test
    public void getAll() {
        List<Meal> meals = repository.getAll(USER_ID);
        MEAL_MATCHER.assertMatch(meals, MealTestData.meals);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> meals = repository.getBetweenHalfOpen(START_DATE_TIME, END_DATE_TIME, USER_ID);
        MEAL_MATCHER.assertMatch(meals, meals_start_end_datetime);
    }

    @Test
    public void getBetweenHalfOpenNotOwn() {
        List<Meal> meals = repository.getBetweenHalfOpen(START_DATE_TIME, END_DATE_TIME, ADMIN_ID);
        MEAL_MATCHER.assertMatch(meals, Collections.EMPTY_LIST);
    }
}