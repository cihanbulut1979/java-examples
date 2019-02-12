package org.akka.essentials.grid.worker;

import org.akka.essentials.grid.StartWorker;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.kernel.Bootable;

public class WorkerActorSystem implements Bootable {

	private ActorSystem system;
	private ActorRef registerRemoteWorkerActor;

	public WorkerActorSystem(String config, int port) {
		// load the configuration
		system = ActorSystem.create("WorkerSys", ConfigFactory.load().getConfig(config));

		// get the reference to the remote server actor
		registerRemoteWorkerActor = system
				.actorFor("akka://WorkServerSys@127.0.0.1:2552/user/RegisterRemoteWorkerActor");

		// create the worker address message
		Address myAddr = new Address("akka", "WorkerSys", "127.0.0.1", port);
		// send a message to server to register this worker instance
		registerRemoteWorkerActor.tell(new StartWorker(myAddr.toString()));
	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

	public void startup() {
		// TODO Auto-generated method stub

	}

}
