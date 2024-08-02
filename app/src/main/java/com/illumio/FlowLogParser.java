package com.illumio;

import com.illumio.model.FlowLog;
import com.illumio.model.LookUpTable;

import java.io.*;

public class FlowLogParser {
  private File flowLogFile;
  private LookUpTable lookUpTable;

  public FlowLogParser(String flowLogFilePath, LookUpTable lookUpTable) {
    this.flowLogFile = new File(flowLogFilePath);
    this.lookUpTable = lookUpTable;
  }

  public FlowLog parseFlowLogFile() {
    if (!flowLogFile.exists()) {
      // exception
    }
    if (!flowLogFile.isFile()) {
      // exception
    }

    if (!flowLogFile.canRead()) {
      // exception
    }

    FlowLog flowLog = new FlowLog();
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(flowLogFile))) {
      String line;
      while((line = bufferedReader.readLine()) != null) {
        String[] splitData = line.split(" ");
        // Assume file has no dirty data, and all formatted correctly
        // Sample: 2 123456789010 eni-1235b8ca123456789 172.31.16.139 172.31.16.21 20641 22 6 20 4249 1418530010 1418530070 ACCEPT OK
        String dstPortString = splitData[6];
        int dstPort = Integer.valueOf(dstPortString);
        // Need to map from  Assigned Internet Protocol Numbers https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml
        String rawProtocol = splitData[7];
        String protocol = ProtocolNumberConvertor.convertToProtocolString(rawProtocol);
        String tag = lookUpTable.getTag(dstPort, protocol);
        flowLog.addLog(dstPort, protocol, tag);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return flowLog;
  }
}
