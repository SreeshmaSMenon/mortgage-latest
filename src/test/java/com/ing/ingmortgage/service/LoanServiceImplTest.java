package com.ing.ingmortgage.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ing.ingmortgage.repository.LoanRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceImplTest {

	@InjectMocks
	private LoanServiceImpl loanServiceImpl;

	@Mock
	private LoanRepository loanRepository;

	@Before
	public void setup() {

	}
	/*
	 * @Test public void testGetProductsByCategory() {
	 * 
	 * Mockito.when(loanRepository.findByLoanIdAndLoanStatus(loanId,
	 * loanStatus)).thenReturn(products);
	 * 
	 * ProductResponse actualProductResponse =
	 * productServiceImpl.getProductsByCategory(category.getCategoryId());
	 * 
	 * Assert.assertEquals(productResponse.getStatusMessage(),
	 * actualProductResponse.getStatusMessage()); }
	 * 
	 * @Test(expected = CommonException.class) public void
	 * testGetProductsByCategory1() { products = new ArrayList<Product>();
	 * Mockito.when(productRepository.findProductByCategoryId(category.getCategoryId
	 * ())).thenReturn(products);
	 * 
	 * ProductResponse actualProductResponse =
	 * productServiceImpl.getProductsByCategory(category.getCategoryId());
	 * 
	 * Assert.assertEquals(productResponse.getStatusMessage(),
	 * actualProductResponse.getStatusMessage()); }
	 */

}
