package CourseAppTest;

import static org.junit.Assert.*;

import org.junit.Test;

import CourseApp.Teacher;

public class CourseAppTest {

	@Test
	public void test() {
		Teacher t =new Teacher("liulili",123456,42.5);
		assertEquals(t.toString(),"���֣�liuliliID��123456���䣺42.5");	
	}

}
