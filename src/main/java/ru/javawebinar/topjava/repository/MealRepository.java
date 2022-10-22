package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal);
    boolean delete(Integer id);
    Meal get(Integer id);
    List<Meal> getAll();






}
