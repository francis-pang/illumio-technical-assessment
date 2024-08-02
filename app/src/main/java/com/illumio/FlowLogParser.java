package com.illumio;

import com.illumio.model.FlowLog;
import com.illumio.model.LookUpTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class FlowLogParser {
  private static final Logger logger = LogManager.getLogger(FlowLogParser.class);
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
        logger.debug("splitData={}", Arrays.toString(splitData));
        // Assume file has no dirty data, and all formatted correctly
        // Sample: 2 123456789010 eni-1235b8ca123456789 172.31.16.139 172.31.16.21 20641 22 6 20 4249 1418530010 1418530070 ACCEPT OK
        String dstPortString = splitData[6];
        int dstPort = Integer.valueOf(dstPortString);
        // Need to map from  Assigned Internet Protocol Numbers https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml
        String rawProtocol = splitData[7];
        logger.debug("dstPortString={}, rawProtocol={}", dstPortString, rawProtocol);
        String protocol = ProtocolNumberConvertor.convertToProtocolString(rawProtocol);
        if (protocol.isBlank()) {
          logger.warn("Unable to locate a tag for the protocol. rawProtocol={}. SKip processing",
              rawProtocol);
          continue;
        }
        String tag = lookUpTable.getTag(dstPort, protocol);
        // TODO: To document that we won't stop the program for this error
        if (tag == null || tag.isBlank()) {
          logger.warn("Unable to locate a tag for the entry. dstPortString={}, rawProtocol={}, protocol={}",
              dstPortString, rawProtocol, protocol);
          continue;
        }
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
