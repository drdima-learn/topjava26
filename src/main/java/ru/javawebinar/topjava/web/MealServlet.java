package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepository = new InMemoryMealRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.debug("action=" + action);
        if (action == null) {
            log.info("get all");
            request.setAttribute("meals", MealsUtil.getWithExceeded(mealRepository.getAll(), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            log.info("delete");
            Integer id = Integer.parseInt(request.getParameter("id"));
            mealRepository.delete(id);
            response.sendRedirect("meals");

        } else {
            Integer id = getId(request);
            final Meal meal = id == null ? new Meal(LocalDateTime.now(), "", 1000) : mealRepository.get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/form.jsp").forward(request, response);
        }
    }

    private Integer getId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id == null) {
            return null;
        }
        return Integer.parseInt(id);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = getId(req);
        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        Meal meal = new Meal(id, LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRepository.save(meal);
        resp.sendRedirect("meals");
    }
}
