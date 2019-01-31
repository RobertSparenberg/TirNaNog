package net.frozenchaos.TirNaNog.web.controllers;

import net.frozenchaos.TirNaNog.data.Record;
import net.frozenchaos.TirNaNog.data.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/records")
public class RecordRestController {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordRestController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Record> getAllRecords(@RequestParam String recordName) {
        return recordRepository.findByName(recordName);
    }
}
