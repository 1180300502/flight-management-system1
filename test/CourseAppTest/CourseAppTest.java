package CourseAppTest;

import static org.junit.Assert.*;

import org.junit.Test;

import CourseApp.Teacher;

public class CourseAppTest {

	@Test
	public void test() {
		Teacher t =new Teacher("liulili",123456,42.5);
		assertEquals(t.toString(),"Ãû×Ö£ºliuliliID£º123456ÄêÁä£º42.5");	
	}

}
