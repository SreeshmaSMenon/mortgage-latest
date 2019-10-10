package com.ing.ingmortgage;

public class Test {
	public static void main(String args[]) {
		Double beginningBalance=3000000.00;
		String interestRate="8";
		Double monthlyInterest = (beginningBalance * (Double.parseDouble(interestRate) / 100)) / 12;
		System.out.println(monthlyInterest);
	}
}
