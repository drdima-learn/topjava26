package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest {
    @Test
    public void getWithMeal() {
        User user = service.getWithMeal(USER_ID);
        USER_MATCHER_WITH_MEALS.assertMatch(user, getUserWithMeal());
    }

    @Test
    public void getWithMealEmpty() {
        User user = service.getWithMeal(GUEST_ID);
        USER_MATCHER_WITH_MEALS.assertMatch(user, getGuestWithMeal());
    }

    @Test
    public void getNotFoundWithMeal() {
        assertThrows(NotFoundException.class, () -> service.getWithMeal(NOT_FOUND));
    }

    @Test
    public void getAllWithMeal() {
        List<User> all = service.getAllWithMeal();
        USER_MATCHER_WITH_MEALS.assertMatch(all, getAdminWithMeal(), getGuestWithMeal(), getUserWithMeal());
    }
}
