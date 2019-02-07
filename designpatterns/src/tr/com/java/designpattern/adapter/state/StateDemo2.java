package tr.com.java.designpattern.adapter.state;

//1. The "wrapper"
class FSM1 {
	// 2. States array
	private State1[] states = { new A1(), new B1(), new C1() };

	// 3. Current state
	private int current = 0;

	// 4. Delegation and pass the this pointer
	public void on() {
		states[current].on(this);
	}

	public void off() {
		states[current].off(this);
	}

	public void ack() {
		states[current].ack(this);
	}

	public void changeState(int index) {
		current = index;
	}
}

// 5. The State base class
abstract class State1 {
	// 6. Default behavior
	public void on(FSM1 fsm) {
		System.out.println("error");
	}

	public void off(FSM1 fsm) {
		System.out.println("error");
	}

	public void ack(FSM1 fsm) {
		System.out.println("error");
	}
}

class A1 extends State1 {
	public void on(FSM1 fsm) {
		System.out.println("A + on  = C");
		fsm.changeState(2);
	}

	public void off(FSM1 fsm) {
		System.out.println("A + off = B");
		fsm.changeState(1);
	}

	public void ack(FSM1 fsm) {
		System.out.println("A + ack = A");
		fsm.changeState(0);
	}
}

class B1 extends State1 {
	public void on(FSM1 fsm) {
		System.out.println("B + on  = A");
		fsm.changeState(0);
	}

	public void off(FSM1 fsm) {
		System.out.println("B + off = C");
		fsm.changeState(2);
	}
}

// 7. Only override some messages
class C1 extends State1 {
	// 8. "call back to" the wrapper class
	public void on(FSM1 fsm) {
		System.out.println("C + on  = B");
		fsm.changeState(1);
	}
}

public class StateDemo2 {
	public static void main(String[] args) {
		FSM1 fsm = new FSM1();
		int[] msgs = { 2, 1, 2, 1, 0, 2, 0, 0 };
		for (int i = 0; i < msgs.length; i++)
			if (msgs[i] == 0)
				fsm.on();
			else if (msgs[i] == 1)
				fsm.off();
			else if (msgs[i] == 2)
				fsm.ack();
	}
}