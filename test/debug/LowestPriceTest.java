package debug;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LowestPriceTest {

	@Test
	public void test() {
		LowestPrice l= new LowestPrice();
		List<Integer> a = Arrays.asList(2,5);
		List<Integer> b = Arrays.asList(3,2);
		List<List<Integer>> c=Arrays.asList(Arrays.asList(3,0,5),Arrays.asList(1,2,10));
		assertEquals(l.shoppingOffers(a, c, b),14);
		List<Integer> a1 = Arrays.asList(2,3,4);
		List<Integer> b1 = Arrays.asList(1,2,1);
		List<List<Integer>> c1=Arrays.asList(Arrays.asList(1,1,0,4),Arrays.asList(2,2,1,9));
		assertEquals(l.shoppingOffers(a1, c1, b1),11);
	}

}
