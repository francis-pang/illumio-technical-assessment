package com.illumio.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class FlowLog {
  private static final Logger logger = LogManager.getLogger(FlowLog.class);

  private Map<String, Integer> tagCount;
  private Map<PortProtocol, Integer> lookUpCount;

  public FlowLog() {
    this.tagCount = new HashMap<>();
    this.lookUpCount = new HashMap<>();
  }

  public boolean addLog(int port, String protocol, String tag) {
    if (tag == null || tag.isBlank()) {
      logger.info("Tag is invalid. Tag={}.", (tag == null) ? "NULL" : tag);
      return false;
    }
    PortProtocol portProtocol = new PortProtocol(port, protocol);
    this.tagCount.compute(tag, (k,v) -> (v == null) ? 1 : v + 1);
    this.lookUpCount.compute(portProtocol, (k, v) -> (v == null) ? 1 : v + 1);
    return true;
  }

  public Map<String, Integer> getTagCountMap() {
    return this.tagCount;
  }

  public Map<PortProtocol, Integer> getPortProtolCombinationCountMap() {
    return this.lookUpCount;
  }
}
