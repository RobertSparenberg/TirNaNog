package net.frozenchaos.TirNaNog.web.controllers;

import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.data.FunctionRepository;
import net.frozenchaos.TirNaNog.data.ModuleConfig;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import net.frozenchaos.TirNaNog.web.pages.Page;
import net.frozenchaos.TirNaNog.web.pages.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PageRestController {
    private final OwnConfigService ownConfigService;
    private final ModuleConfigRepository moduleConfigRepository;
    private final FunctionRepository functionRepository;
    private final PageRepository pageRepository;

    @Autowired
    public PageRestController(OwnConfigService ownConfigService, ModuleConfigRepository moduleConfigRepository,
                              FunctionRepository functionRepository,
                              PageRepository pageRepository) {
        this.ownConfigService = ownConfigService;
        this.moduleConfigRepository = moduleConfigRepository;
        this.functionRepository = functionRepository;
        this.pageRepository = pageRepository;
    }

    @RequestMapping("/links")
    public List<String> getPages() {
        List<String> pages = new ArrayList<>();
        for(Page page : pageRepository.findAll()) {
            pages.add(page.getName());
        }
        return pages;
    }

    @RequestMapping("/pages/{pageName}")
    public Page getPage(@PathVariable String pageName) {
        return pageRepository.findByName(pageName);
    }

    @RequestMapping("/admin/links")
    public List<String> getAdminPages() {
        List<String> pages = new ArrayList<>();
        pages.add(ownConfigService.getOwnConfig().getName());
        for(ModuleConfig module : moduleConfigRepository.findAll()) {
            pages.add(module.getName());
        }
        return pages;
    }

    @RequestMapping("/admin/pages/{pageName}")
    public ModuleConfig getAdminPage(@PathVariable String pageName) {
        ModuleConfig ownConfig = ownConfigService.getOwnConfig();
        if(pageName.equals(ownConfig.getName())) {
            return ownConfig;
        }
        return moduleConfigRepository.findByName(pageName);
    }

    @RequestMapping("admin/automation")
    public Iterable<Function> getFunctions() {
        return functionRepository.findAll();
    }
}
