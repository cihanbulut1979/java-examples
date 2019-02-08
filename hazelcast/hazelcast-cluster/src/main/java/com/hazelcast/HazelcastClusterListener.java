
package com.hazelcast;

import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import com.hazelcast.core.MigrationEvent;
import com.hazelcast.core.MigrationListener;
import com.hazelcast.partition.PartitionLostEvent;
import com.hazelcast.partition.PartitionLostListener;

final class HazelcastClusterListener extends Observable
		implements MigrationListener, PartitionLostListener, MembershipListener {
	private static final Logger log = LoggerFactory.getLogger(HazelcastClusterListener.class);
	private final HazelcastInstance hzInstance;

	public HazelcastClusterListener(HazelcastInstance hazelcastClusterServiceBean) {
		this.hzInstance = hazelcastClusterServiceBean;
		awaitClusterSafety(100);
		awaitMemberSafety(100);
	}

	@Override
	public void migrationStarted(MigrationEvent migrationevent) {

		if (migrationevent.getNewOwner().localMember()) {
			onIncomingStarted(migrationevent);
		}
	}

	public void awaitClusterSafety(long sleep) {
		boolean intr = false;
		while (!hzInstance.getPartitionService().isClusterSafe()) {

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				intr = true;
			}
		}

		if (intr)
			Thread.currentThread().interrupt();
	}

	public void awaitMemberSafety(long sleep) {
		boolean intr = false;
		while (!hzInstance.getPartitionService().isLocalMemberSafe()) {

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				intr = true;
			}
		}

		if (intr)
			Thread.currentThread().interrupt();
	}

	private void onIncomingStarted(MigrationEvent event) {
		log.debug(">> Detected fireOnMigrationStart << ");
	}

	@Override
	public void migrationFailed(MigrationEvent migrationevent) {
		if (migrationevent.getNewOwner().localMember()) {
			onIncomingEnd(migrationevent, false);
		}
	}

	private void onIncomingEnd(MigrationEvent migrationevent, boolean success) {
		if (success) {
			log.debug("Incoming migration detected for partition => " + migrationevent.getPartitionId());
			setChanged();
			notifyObservers(migrationevent);
		} else
			log.warn("Partition migration failed " + migrationevent);
	}

	@Override
	public void migrationCompleted(MigrationEvent migrationevent) {
		if (migrationevent.getNewOwner().localMember()) {
			onIncomingEnd(migrationevent, true);
		}

	}

	// detects that the owner failed before the backup node completes the sync
	// process and issues a partition lost event.
	@Override
	public void partitionLost(PartitionLostEvent event) {
		log.warn("" + event);
	}

	@Override
	public void memberAdded(MembershipEvent membershipEvent) {
		log.info("Cluster member added signalled:: " + membershipEvent);
		while (!hzInstance.getPartitionService().isMemberSafe(membershipEvent.getMember())) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}
		log.info("Member balance ready. Resuming..");
	}

	@Override
	public void memberRemoved(MembershipEvent membershipEvent) {
		log.warn("Cluster member remove signalled:: " + membershipEvent);
		setChanged();
		notifyObservers(membershipEvent.getMember());
	}

	@Override
	public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {

	}

}