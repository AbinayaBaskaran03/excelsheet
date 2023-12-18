package com.excelsheet.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelsheet.entity.ExcelEntity;

@Repository
public interface ExcelRep extends JpaRepository<ExcelEntity , UUID>{


}
