package net.frozenchaos.TirNaNog.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Integer> {
    @Query("SELECT record FROM Record record WHERE record.name = ?1 ORDER BY record.timestamp")
    public List<Record> findByName(String name);
}
