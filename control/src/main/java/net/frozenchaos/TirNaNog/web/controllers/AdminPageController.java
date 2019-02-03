package net.frozenchaos.TirNaNog.web.controllers;


import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import net.frozenchaos.TirNaNog.web.pages.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    private final String moduleName;
    private final OwnConfigService ownConfigService;
    private final ModuleConfigRepository moduleConfigRepository;
    private final PageRepository pageRepository;

    @Autowired
    public AdminPageController(OwnConfigService ownConfigService, ModuleConfigRepository moduleConfigRepository, PageRepository pageRepository) {
        this.ownConfigService = ownConfigService;
        this.moduleConfigRepository = moduleConfigRepository;
        this.pageRepository = pageRepository;
        moduleName = ownConfigService.getOwnConfig().getName();
    }

    @RequestMapping("")
    public String viewAdmin(ModelMap model) {
        model.put("moduleName", moduleName);
        return "admin/adminPage";
    }

    @RequestMapping("/pages/page/{pageName}")
    public String getAdminPage(ModelMap model, @PathVariable String pageName) {
        model.put("moduleName", moduleName);
        return "admin/" + pageName;
    }
}