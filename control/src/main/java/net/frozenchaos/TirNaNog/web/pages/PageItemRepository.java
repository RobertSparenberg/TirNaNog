package net.frozenchaos.TirNaNog.web.pages;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PageItemRepository extends CrudRepository<PageItem, Integer> {
    @Query("SELECT pageItem FROM PageItem pageItem WHERE pageItem.id = ?1")
    PageItem findById(long id);
}
