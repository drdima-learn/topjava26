package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {

    private AtomicInteger counter = new AtomicInteger(1);
    private Map<Integer, Meal> map;

    @Override
    public List<Meal> getAll() {
        if (map == null) {
            map = new ConcurrentHashMap<>();
            init();
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public Meal get(Integer id) {
        return map.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.getAndIncrement());
        }
        map.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(Integer id) {
        Meal meal = map.remove(id);
        return meal.getId() == id;
    }



    private void init() {
        MealsUtil.meals.forEach(m -> save(m));
    }
}
