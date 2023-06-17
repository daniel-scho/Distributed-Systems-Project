package com.fh.pdfgenerator;

import dto.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

@SpringBootTest
@ContextConfiguration(classes = PdfGeneratorApplication.class)
class PdfGeneratorApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	public void testCalculateSumOfKWH() {
		Customer customer = new Customer();
		customer.chargedKWH = Arrays.asList(10.5, 7.8, 15.2);

		// Calculate the expected sum
		double expectedSum = 10.5 + 7.8 + 15.2;

		// Get the actual sum from the calculateSumOfKWH method
		String actualSum = customer.calculateSumOfKWH();

		// Assert that the actual sum matches the expected sum
		Assertions.assertEquals(String.valueOf(expectedSum), actualSum);
	}
}
