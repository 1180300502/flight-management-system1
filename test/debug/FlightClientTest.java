package debug;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class FlightClientTest {

	@Test
	public void test() {
		Plane p=new Plane();
		p.setPlaneAge(3.6);
		p.setPlaneNo("111");
		p.setPlaneType("A350");
		p.setSeatsNum(120);
		Flight f=new Flight();
		Flight g=new Flight();
		Calendar cal1 =Calendar.getInstance(),cal2 = Calendar.getInstance(),cal3 = Calendar.getInstance(),cal4 = Calendar.getInstance(),cal5 = Calendar.getInstance();
		cal1.set(2020, 1, 1);
		cal2.set(2020, 1, 1,8,0);
		cal3.set(2020, 1, 1,10,0);
		cal4.set(2020, 1, 1,9,50);
		cal5.set(2020, 1, 1,10,40);
		f.setArrivalAirport("bj");
		f.setArrivalTime(cal3);
		f.setDepartAirport("cq");
		f.setDepartTime(cal2);
		f.setFlightDate(cal1);
		f.setFlightNo("123");
		f.setPlane(p);
		g.setArrivalAirport("bj");
		g.setArrivalTime(cal5);
		g.setDepartAirport("cq");
		g.setDepartTime(cal4);
		g.setFlightDate(cal1);
		g.setFlightNo("123");
		g.setPlane(p);
		
		List<Plane> a = Arrays.asList(p);
		List<Flight> b = Arrays.asList(f,g);
		
		FlightClient fl=new FlightClient();
		assertTrue(fl.planeAllocation(a, b));
	}

}