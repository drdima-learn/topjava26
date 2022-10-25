package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.Collection;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            InMemoryUserRepository inMemoryUserRepository = appCtx.getBean(InMemoryUserRepository.class);
            User user = inMemoryUserRepository.get(2);
            System.out.println("get 2" + user);
//            inMemoryUserRepository.delete(2);
//            user = inMemoryUserRepository.get(2);
//            System.out.println("get 2, after delete 2 " + user);
            System.out.println("-------------");
            System.out.println("getAll() + " + inMemoryUserRepository.getAll());
            System.out.println("getByEmail() + " + inMemoryUserRepository.getByEmail("admin@gmail.com"));


//            System.out.println("///////////////////");
//            InMemoryUserRepository inMemoryUserRepository2 = appCtx.getBean(InMemoryUserRepository.class);
//            System.out.println("get 2 from inMemoryUserRepository2   " + inMemoryUserRepository2.get(2));
//
//
//            System.out.println("*****************");
//            InMemoryUserRepository repository = new InMemoryUserRepository();
//            User user1 = repository.get(2);
//            System.out.println(user1);


            System.out.println("\n\n\n**********");
            InMemoryMealRepository inMemoryMealRepository = appCtx.getBean(InMemoryMealRepository.class);
            Meal meal = inMemoryMealRepository.get(1, 1);
            System.out.println(meal);
            Collection<Meal> meals = inMemoryMealRepository.getAll(2);
            System.out.println(meals);
            System.out.println(inMemoryMealRepository.delete(4, 3));
            meals = inMemoryMealRepository.getAll(3);
            System.out.println(meals);

            System.out.println("```````````````");
            System.out.println(inMemoryMealRepository.get(10,3));

            System.out.println("@@@@@@@@@@@@@@");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.get(1));
            System.out.println(mealRestController.getAll());


        }
    }
}
