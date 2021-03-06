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
public class PageController {
    private final String moduleName;
    private final OwnConfigService ownConfigService;
    private final ModuleConfigRepository moduleConfigRepository;
    private final PageRepository pageRepository;

    @Autowired
    public PageController(OwnConfigService ownConfigService, ModuleConfigRepository moduleConfigRepository, PageRepository pageRepository) {
        this.ownConfigService = ownConfigService;
        this.moduleConfigRepository = moduleConfigRepository;
        this.pageRepository = pageRepository;
        moduleName = ownConfigService.getOwnConfig().getName();
    }

    @RequestMapping("/")
    public String view(ModelMap model) {
        model.put("moduleName", moduleName);
        return "viewPage";
    }

    @RequestMapping("/pages/page/{pageName}")
    public String getPage(ModelMap model, @PathVariable String pageName) {
        model.put("moduleName", moduleName);
        model.put("page", pageRepository.findByName(pageName));
        return "viewPage";
    }
}