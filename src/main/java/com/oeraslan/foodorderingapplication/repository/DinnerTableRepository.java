package com.oeraslan.foodorderingapplication.repository;

import com.oeraslan.foodorderingapplication.repository.entity.DinnerTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinnerTableRepository extends JpaRepository<DinnerTable, Long> {
}
