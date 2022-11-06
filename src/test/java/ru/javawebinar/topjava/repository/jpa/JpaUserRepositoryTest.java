package ru.javawebinar.topjava.repository.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaUserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void save() {
        User user = getNew();
        User newUser = repository.save(user);
        user.setId(newUser.getId());

        USER_MATCHER.assertMatch(newUser, user);
    }

    @Test
    public void update() {
        User user = getUpdated();
        User updated = repository.save(user);


        USER_MATCHER.assertMatch(updated, user);
    }

    @Test
    public void updateNotFound() {
        User user = getUpdated();
        user.setId(NOT_FOUND);
        User updated = repository.save(user);


        USER_MATCHER.assertMatch(updated, null);
    }



    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getByEmail() {
    }

    @Test
    public void getAll() {
    }
}