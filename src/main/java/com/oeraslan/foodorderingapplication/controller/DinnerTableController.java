package com.oeraslan.foodorderingapplication.controller;

import com.oeraslan.foodorderingapplication.dto.DinnerTableReserveDto;
import com.oeraslan.foodorderingapplication.dto.DinnerTableResponseDto;
import com.oeraslan.foodorderingapplication.service.DinnerTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/dinnerTable")
@RequiredArgsConstructor
public class DinnerTableController {

    private final DinnerTableService dinnerTableService;

    @PostMapping("/create/{numberOfTables}")
    @ResponseStatus(HttpStatus.OK)
    public void createDinnerTable(@PathVariable int numberOfTables) {
        log.info("[{}][createDinnerTable] -> creating dinner table", this.getClass().getSimpleName());

        dinnerTableService.createDinnerTable(numberOfTables);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateDinnerTable(@PathVariable Long id, @RequestBody DinnerTableReserveDto dinnerTableReserveDto) {
        log.info("[{}][updateDinnerTable] -> updating dinner table", this.getClass().getSimpleName());

        dinnerTableService.updateDinnerTable(id, dinnerTableReserveDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteDinnerTable(@PathVariable Long id) {
        log.info("[{}][deleteDinnerTable] -> deleting dinner table", this.getClass().getSimpleName());

        dinnerTableService.deleteDinnerTable(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DinnerTableResponseDto> getDinnerTableById(@PathVariable Long id) {
        log.info("[{}][getDinnerTableById] -> getting dinner table", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(dinnerTableService.getDinnerTableById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllDinnerTables() {
        log.info("[{}][getAllDinnerTables] -> getting all dinner tables", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(dinnerTableService.getAllDinnerTables());
    }

    @GetMapping("/available")
    public ResponseEntity<Object> getAvailableDinnerTables() {
        log.info("[{}][getAvailableDinnerTables] -> getting available dinner tables", this.getClass().getSimpleName());

        return ResponseEntity.ok().body(dinnerTableService.getDinnerTablesNotReserved());
    }

}
