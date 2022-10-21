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
        if (action != null) {
            if (action.equals("add")) {
                request.getRequestDispatcher("/form.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                Integer id = Integer.parseInt(request.getParameter("id"));
                mealRepository.delete(id);
                response.sendRedirect("meals");

            }
        } else {
            log.debug("forward to meals");

            request.setAttribute("meals", MealsUtil.createTos(mealRepository.getAll()));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        log.debug("doPost action=" + action);

        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        log.debug("doPost meal = " + meal);
        mealRepository.add(meal);
        resp.sendRedirect("meals");

    }
}
