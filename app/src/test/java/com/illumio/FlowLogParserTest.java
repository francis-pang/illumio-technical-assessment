package com.illumio;

import com.illumio.model.FlowLog;
import com.illumio.model.LookUpTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FlowLogParserTest {
  @Mock
  private LookUpTable mockLookUpTable;

  private FlowLogParser flowLogParser;

  @Test
  void parseFlowLogFile_singleEntryFile() {
    // Mockito set up
    Mockito.when(mockLookUpTable.getTag(22, "TCP")).thenReturn("SVP_1");

    flowLogParser = new FlowLogParser("src/test/resources/flow-log-single.txt", mockLookUpTable);
    FlowLog flowLog = flowLogParser.parseFlowLogFile();
    Map<String, Integer> tagCount = flowLog.getTagCountMap();

    assertEquals(1, tagCount.get("SVP_1"));
  }

  @Test
  void parseFlowLogFile_invalidProtocolFile() {
    flowLogParser = new FlowLogParser("src/test/resources/flow-log-invalid-protocol.txt", mockLookUpTable);
    FlowLog flowLog = flowLogParser.parseFlowLogFile();
    Map<String, Integer> tagCount = flowLog.getTagCountMap();

    assertTrue(tagCount.isEmpty());
  }
}