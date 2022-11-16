package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealRestController mealController;

    @GetMapping
    public String getMeals(Model model,
                           @RequestParam(value = "startDate", required = false) String startDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "startTime", required = false) String startTime,
                           @RequestParam(value = "endTime", required = false) String endTime
    ) {
        log.info("meals");
        model.addAttribute("meals",
                mealController.getBetween(
                        parseLocalDate(startDate),
                        parseLocalTime(startTime),
                        parseLocalDate(endDate),
                        parseLocalTime(endTime)));

        return "meals";
    }

    @GetMapping("/delete")
    public String deleteMeal(@RequestParam int id) {
        mealController.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String showMealUpdateForm(Model model, @RequestParam("id") int id) {
        model.addAttribute("action", "update");
        model.addAttribute("meal", mealController.get(id));
        return "mealForm";
    }

    @GetMapping("/create")
    public String showMealCreateForm(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @PostMapping("/update")
    public String updateMeal(@ModelAttribute("meal") Meal meal) {
        log.info("/meals/update   meal={}", meal);
        if (meal.isNew()) {
            mealController.create(meal);
        } else {
            mealController.update(meal, meal.getId());
        }
        return "redirect:/meals";
    }
}
