package P1test;

import static org.junit.Assert.*;

import org.junit.Test;

import P1.EntryState;

public class EntryStateTest {

	@Test
	public void test() {
		EntryState state=new EntryState();
		state.checkRep();
		state.assign();
		assertEquals(state.toString(),"assigned");
		state.cancel();
		assertEquals(state.toString(),"cancel");
		state.complete();
		assertEquals(state.toString(),"complete");
		state.hold();
		assertEquals(state.toString(),"hold");
		state.run();
		assertEquals(state.toString(),"run");
	}

}
