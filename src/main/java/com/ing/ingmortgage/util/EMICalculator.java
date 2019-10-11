package com.ing.ingmortgage.util;

public class EMICalculator {

	EMICalculator() {

	}

	public static Double calculatedEMI(Double principal, Double rateOfInterest, Integer tenure) {
		Double emi;
		rateOfInterest = rateOfInterest / (12 * 100); // one month interest
		tenure = tenure * 12; // one month period
		emi = (principal * rateOfInterest * (Double) Math.pow(1 + rateOfInterest, tenure))
				/ (Double) (Math.pow(1 + rateOfInterest, tenure) - 1);

		return (emi);
	}
}
