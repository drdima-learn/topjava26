package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAll();

    Meal add(Meal meal);

    boolean delete(Integer id);
}
