package net.frozenchaos.TirNaNog.web.controllers;

import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.data.FunctionRepository;
import net.frozenchaos.TirNaNog.data.ModuleConfigRepository;
import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import net.frozenchaos.TirNaNog.web.pages.Page;
import net.frozenchaos.TirNaNog.web.pages.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/admin")
public class AdminPageRestController {
    private final OwnConfigService ownConfigService;
    private final ModuleConfigRepository moduleConfigRepository;
    private final FunctionRepository functionRepository;
    private final PageRepository pageRepository;

    @Autowired
    public AdminPageRestController(OwnConfigService ownConfigService, ModuleConfigRepository moduleConfigRepository,
                                   FunctionRepository functionRepository,
                                   PageRepository pageRepository) {
        this.ownConfigService = ownConfigService;
        this.moduleConfigRepository = moduleConfigRepository;
        this.functionRepository = functionRepository;
        this.pageRepository = pageRepository;
    }

    @RequestMapping("/automation")
    public Iterable<Function> getFunctions() {
        return functionRepository.findAll();
    }

    @RequestMapping(value = "/pages/page/views/save_order", method = RequestMethod.POST)
    public void savePageOrder(@RequestBody List<String> pageNames) {
        Iterable<Page> existingPages = pageRepository.getPagesOrdered();
        List<Page> newPageList = new ArrayList<>(pageNames.size());
        for(String pageName : pageNames) {
            Page page = null;
            for(Page existingPage : existingPages) {
                if(existingPage.getName().equals(pageName)) {
                    page = existingPage;
                    break;
                }
            }
            if(page == null) {
                page = new Page(pageName);
            }
            page.setOrder(newPageList.size() + 1);
            newPageList.add(page);
        }
        for(Page existingPage : existingPages) {
            if(!newPageList.contains(existingPage)) {
                pageRepository.delete(existingPage);
            }
        }
        pageRepository.save(newPageList);
    }

//    @RequestMapping(value = "/pages/page/views/view/{originalPageName}/save", method = RequestMethod.POST)
//    public void savePage(@PathVariable String originalPageName, @RequestBody Page page) {
//        originalPageName = originalPageName.replace('_', ' ');
//        Page originalPage = pageRepository.findByName(originalPageName);
//        if(originalPage != null) {
//            if(!originalPageName.equals(page.getName()) && !StringUtils.isEmpty(page.getName())) {
//                if(pageRepository.findByName(page.getName()) == null) {
//                    originalPage.setName(page.getName());
//                }
//            }
//            originalPage.setRows(page.getRows());
//            pageRepository.save(originalPage);
//        }
//    }

    @RequestMapping(value = "/pages/page/views/view/{pageName}", method = RequestMethod.POST)
    public void savePage(@RequestBody Page page, @PathVariable String pageName) {
        Page originalPage = pageRepository.findByName(pageName.replace('_', ' '));
        pageRepository.delete(originalPage);
        pageRepository.save(page);
    }
}
