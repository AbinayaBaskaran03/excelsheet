package com.excelsheet.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelsheet.entity.Details;

@Repository
public interface DetailsRepository extends JpaRepository<Details , UUID>{

	List<Details> findByName(String name);

}
