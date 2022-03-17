package debug;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventManagerTest {

	@Test
	public void test() {
		assertEquals(EventManager.book(1, 10, 20),1); 
		assertEquals(EventManager.book(1, 1, 7),1);
		assertEquals(EventManager.book(1, 10, 22),2);
		assertEquals(EventManager.book(1, 5, 15),3);
		assertEquals(EventManager.book(1, 5, 12),4);
		assertEquals(EventManager.book(1, 7, 10),4);		
	}

}
