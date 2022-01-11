package com.neosoft.Poc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@Column
	private String fname;
	
	@NotNull
	@Column
	private String surname;
	
	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	private Date DOB;
	
	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	private Date joiningDate;
	
	@NotNull
	@Column
	@Pattern(regexp="(^$|[0-9]{10})")
	private String mobileNo;
	
	@NotNull
	@Column
	@Pattern(regexp = ("^[0-9]{6}"))
	private String pincode;
	
	@NotNull
	@Column
    private boolean deleted;
	
}
