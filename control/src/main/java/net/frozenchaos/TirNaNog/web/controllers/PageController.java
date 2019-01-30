package net.frozenchaos.TirNaNog.web.controllers;


import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    private static final String PAGE = "page";

    private final String moduleName;

    @Autowired
    public PageController(OwnConfigService ownConfigService) {
        moduleName = ownConfigService.getOwnConfig().getName();
    }

    @RequestMapping("/")
    public String view(ModelMap model) {
        model.put("moduleName", moduleName);
        return PAGE;
    }
}