package P1test;

import static org.junit.Assert.*;

import org.junit.Test;

import P1.TimesLot;

public class TimesLotTest {
	
	@Test
	public void test() {
		TimesLot t=new TimesLot("2019-10-01 10:10","2019-10-01 10:30");
		assertTrue(TimesLot.istime("2019-12-01 10:30"));
		assertTrue(t.contiantime("2019-10-01 10:12"));
		assertTrue(TimesLot.comparetimestring("2019-10-01 10:30", "2019-10-01 10:10"));
		assertFalse(TimesLot.contiantime("2019-10-01 10:10", "2019-10-01 10:30", "2019-12-01 10:30"));
		assertEquals(t.getstarttime(),"2019-10-01 10:10");
		assertEquals(t.getovertime(),"2019-10-01 10:30");
		assertEquals(t.toString(),"2019-10-01 10:10-2019-10-01 10:30");
		
	}

}
