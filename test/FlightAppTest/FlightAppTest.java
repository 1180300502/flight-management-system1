package FlightAppTest;

import static org.junit.Assert.*;

import org.junit.Test;

import FlightApp.FlightEntry;
import FlightApp.FlightLocation;
import FlightApp.Plane;
import P1.Location;
import P1.TimesLot;

public class FlightAppTest {

	@Test
	public void test() {
		FlightEntry f=new FlightEntry("plane");
		Plane p=new Plane("asr","a55",50,3.5);
		TimesLot t=new TimesLot("2019-10-01 10:10","2019-10-01 10:30");
		FlightLocation l1=new FlightLocation("beijing");
		FlightLocation l2=new FlightLocation("tianjin");
		f.setFlightEntry(p, t, l1, l2);
		assertEquals(f.getstartlocations(),l1);
		assertEquals(f.getoverlocations(),l2);
		assertEquals(f.gettimelots(),t);
		assertEquals(p.getname(),"asr");
		assertEquals(p.toString(),"飞机名：asr机型：a55座位数：50年龄：3.5");
	}

}
