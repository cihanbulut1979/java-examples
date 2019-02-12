package WorkerActorSystem;

import org.akka.essentials.grid.worker.WorkerActorSystem;

public class Worker1 {
	public static void main(String[] args) {

		// takes in two arguments - config name used in the application.conf and
		// the port on which the worker is running
		new WorkerActorSystem("WorkerSys1", 2553);

	}
}
