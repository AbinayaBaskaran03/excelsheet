package com.excelsheet.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_excel_keas")
public class ExcelEntity {
	
		
		@Id                                                                                       
		@GeneratedValue(generator = "UUID")                                                       
		@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")              
		@Column	(name="id",updatable = false,nullable = false)
		@Type(type = "uuid-char")                                                                  
	    private UUID id;
		
		@Column(name = "name")
	    private String name;
		
		@Column(name = "address")
	    private String address;

		@Column(name = "dob")
	    private Date dob  ;
		
		@Column(name = "email")
	    private String email;
		
		@Column(name = "phone")
	    private String phone;
		
		@Column(name = "status")
	    private String status;
		


}
