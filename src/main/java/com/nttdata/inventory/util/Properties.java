package com.nttdata.inventory.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter()
public class Properties {
	
	@Value("${lostpercentage.validation}")
	private double lostPercentage;
	
	@Value("${yearsDepression.validation}")
	private int yearsDepression;
	

}
