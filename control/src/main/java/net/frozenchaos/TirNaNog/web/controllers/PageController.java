package net.frozenchaos.TirNaNog.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
    private static final String PAGE = "admin";

    @Autowired
    public PageController() {
    }

    @RequestMapping(value = "/{page}")
    public String view(ModelMap model, @PathVariable String page) {
        List<Link> links = new ArrayList<>();
        model.put("links", links);
        return PAGE;
    }

    @RequestMapping(value = "/admin/{page}")
    public String adminView(ModelMap model, @PathVariable String page) {
        List<Link> links = new ArrayList<>();
        model.put("links", links);
        return PAGE;
    }
}