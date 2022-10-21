package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {

    private AtomicInteger counter = new AtomicInteger(1);
    private Map<Integer, Meal> map = new ConcurrentHashMap<>();

    @Override
    public List<Meal> getAll() {
        if (map.isEmpty()) {
            init();
        }
        return new ArrayList<Meal>( map.values() );
    }

    @Override
    public Meal add(Meal meal) {
        if (isNew(meal)){
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

    private boolean isNew(Meal meal) {
        return meal.getId() == null;
    }

    private void init() {
        MealsUtil.meals.forEach(m -> add(m));
    }
}
