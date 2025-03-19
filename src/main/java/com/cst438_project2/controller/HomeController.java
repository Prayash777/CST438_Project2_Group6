package com.cst438_project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.cst438_project2.model.Categories;
import com.cst438_project2.service.TierListService;
import java.util.List;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @Autowired
    private TierListService tierListService;

    @GetMapping("/")
    public String home(Model model) {
        List<Categories> categories = tierListService.getCategories();
        return "index";
    }
}
