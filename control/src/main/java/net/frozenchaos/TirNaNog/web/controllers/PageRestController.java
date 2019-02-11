package net.frozenchaos.TirNaNog.web.controllers;

import net.frozenchaos.TirNaNog.data.FunctionRepository;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import net.frozenchaos.TirNaNog.web.pages.Page;
import net.frozenchaos.TirNaNog.web.pages.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
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

    @RequestMapping("/pages/links")
    public List<String> getPages() {
        List<String> pages = new ArrayList<>();
        for(Page page : pageRepository.getPagesOrdered()) {
            pages.add(page.getName());
        }
        return pages;
    }

    @RequestMapping(value = "/pages/page/{pageName}", method = RequestMethod.GET)
    public Page getPage(@PathVariable String pageName) {
        return pageRepository.findByName(pageName.replace('_', ' '));
    }
}
