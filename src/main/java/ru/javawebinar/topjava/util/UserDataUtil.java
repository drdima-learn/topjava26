package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDataUtil {
    public static final List<User> users = Arrays.asList(
            new User(null, "User", "user@gmail.com", "1234", 1899, true, new ArrayList<>(Arrays.asList(Role.USER))),
            new User(null, "Administrator", "admin@gmail.com", "1234", 1547, true, new ArrayList<>(Arrays.asList(Role.USER, Role.ADMIN)))
    );
}
