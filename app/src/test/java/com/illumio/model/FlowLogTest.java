package com.illumio.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlowLogTest {
  private FlowLog flowLog;

  @BeforeEach
  void setUpEachMethod() {
    flowLog = new FlowLog();
  }

  @Test
  void addLog_allField_pass() {
    assertTrue(flowLog.addLog(22, "TCP", "test_tag1"));
  }

  @Test
  void addLog_emptyTag_fail() {
    assertFalse(flowLog.addLog(22, "TCP", ""));
  }

  @Test
  void getTagCountMap_emptyEntry() {
    Map<String, Integer> tagCount = flowLog.getTagCountMap();
    assertTrue(tagCount.isEmpty());
  }

  @Test
  void getTagCountMap_singleEntry() {
    flowLog.addLog(22, "TCP", "test_tag1");
    Map<String, Integer> tagCount = flowLog.getTagCountMap();
    assertEquals(1, tagCount.size());
    for (Map.Entry<String, Integer> entry : tagCount.entrySet()) {
      assertEquals("test_tag1", entry.getKey());
      assertEquals(1, entry.getValue());
    }
  }

  @Test
  void getTagCountMap_multipleEntry() {
    flowLog.addLog(22, "TCP", "test_tag1");
    flowLog.addLog(22, "UDP", "test_tag1");
    Map<String, Integer> tagCount = flowLog.getTagCountMap();
    assertEquals(1, tagCount.size());
    for (Map.Entry<String, Integer> entry : tagCount.entrySet()) {
      assertEquals("test_tag1", entry.getKey());
      assertEquals(2, entry.getValue());
    }
  }
}