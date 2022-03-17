package TrainAppTest;

import static org.junit.Assert.*;

import org.junit.Test;

import TrainApp.Train;
import TrainApp.TrainEntry;
import TrainApp.TrainLocation;

public class TrainAppTest {

	@Test
	public void test() {
		Train t =new Train("gao123",100);
		TrainLocation l1=new TrainLocation("haerbin");
		TrainLocation l2=new TrainLocation("chongqin");
		TrainLocation l3=new TrainLocation("beijing");
		assertEquals(t.toString(),"机型：gao123座位数：100");
		TrainEntry entry =new TrainEntry("train");
	}
	
}