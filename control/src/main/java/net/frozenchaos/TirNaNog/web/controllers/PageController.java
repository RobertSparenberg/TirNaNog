package net.frozenchaos.TirNaNog.web.controllers;


import net.frozenchaos.TirNaNog.explorer.OwnConfigService;
import net.frozenchaos.TirNaNog.web.pages.PageItem;
import net.frozenchaos.TirNaNog.web.pages.PageItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    private static final String PAGE = "adminPage";

    private final PageItemRepository pageItemRepository;
    private final String moduleName;

    @Autowired
    public PageController(OwnConfigService ownConfigService, PageItemRepository pageItemRepository) {
        this.pageItemRepository = pageItemRepository;
        moduleName = ownConfigService.getOwnConfig().getName();
    }

    @RequestMapping("/")
    public String view(ModelMap model) {
        model.put("moduleName", moduleName);
        return PAGE;
    }

    @RequestMapping("/admin")
    public String viewAdmin(ModelMap model) {
        model.put("moduleName", moduleName);
        return PAGE;
    }

    @RequestMapping("/pages/items/{pageItemId}")
    public String getPageItem(ModelMap model, @PathVariable String pageItemId) {
        PageItem pageItem = pageItemRepository.findById(Long.valueOf(pageItemId));
        model.put("model", pageItem);
        return pageItem.getClass().getSimpleName();
    }
}