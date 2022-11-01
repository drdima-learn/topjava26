package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
    public static void assertMatch(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

//    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal>... expected) {
//        assertMatch(actual, Arrays.asList(expected));
//    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertNotIn(Iterable<Meal> actual, Iterable<Meal> expected){
        assertThat(actual).usingRecursiveComparison().isNotIn(expected);
    }
    public static void assertNotIn(Meal actual, Meal expected){
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
