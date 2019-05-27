package net.frozenchaos.TirNaNog.web.pages;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PageRepository extends CrudRepository<Page, Integer> {
    @Query("SELECT page FROM Page page WHERE page.name = ?1")
    Page findByName(String pageName);

    @Query("SELECT page FROM Page page ORDER BY page.pageOrder")
    List<Page> getPagesOrdered();
}
