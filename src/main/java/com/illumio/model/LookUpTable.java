package com.illumio.model;

import java.util.HashMap;
import java.util.Map;

public class LookUpTable {
  Map<PortProtocol, String> map;

  public LookUpTable() {
    map = new HashMap<>();
  }

  public boolean add(int port, String protocol, String tag) {
    PortProtocol portProtocol = new PortProtocol(port, protocol);
    map.put(portProtocol, tag);
    return true;
  }

  public String getTag(int port, String protocol) {
    PortProtocol portProtocol = new PortProtocol(port, protocol);
    return map.get(portProtocol);
  }
}
