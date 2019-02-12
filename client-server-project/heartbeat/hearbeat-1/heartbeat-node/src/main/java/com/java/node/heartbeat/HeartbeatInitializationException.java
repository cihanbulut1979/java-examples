package com.java.node.heartbeat;

/**
 * Title:        Heartbeat Phase 2
 * Description:  Heartbeat Implementation Using Pub/Sub JMS.
 * Copyright:    Copyright (c) 2002
 * Company:      finetix Inc.
 * @author Nicholas Whitehead
 * @version 1.0
 */

public class HeartbeatInitializationException extends Exception {

  public HeartbeatInitializationException() {
    super();
  }
  public HeartbeatInitializationException(String message) {
    super(message);
  }

}