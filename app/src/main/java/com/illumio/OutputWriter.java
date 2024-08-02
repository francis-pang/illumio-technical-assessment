package com.illumio;

import com.illumio.model.FlowLog;
import com.illumio.model.PortProtocol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

public class OutputWriter {
  private File outputFile;

  public OutputWriter(String outputFilePath) {
    this.outputFile = new File(outputFilePath);
  }

  public void outputResultToFile(FlowLog flowLog) {
    try(PrintWriter printWriter = new PrintWriter(outputFile)) {
      writeTagCount(printWriter, flowLog);
      writePortProtocolCombinationCount(printWriter, flowLog);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void writePortProtocolCombinationCount(PrintWriter printWriter,
                                                 FlowLog flowLog) {
    Map<PortProtocol, Integer> portProtocolCount = flowLog.getPortProtolCombinationCountMap();
    printWriter.println("Port. Protocol. Count");
    for (Map.Entry<PortProtocol, Integer> entry : portProtocolCount.entrySet()) {
      PortProtocol portProtocol = entry.getKey();
      printWriter.write(portProtocol.port());
      printWriter.write('.');
      printWriter.write(' ');

      printWriter.write(portProtocol.protocol());
      printWriter.write(' ');

      int count = entry.getValue();
      printWriter.write(count);
      printWriter.println();
    }
  }

  private void writeTagCount(PrintWriter printWriter, FlowLog flowLog) {
    Map<String, Integer> tagCount = flowLog.getTagCountMap();
    printWriter.println("Tag: Count");
    for (Map.Entry<String, Integer> entry : tagCount.entrySet()) {
      String tag = entry.getKey();
      printWriter.write(tag);
      printWriter.write(' ');
      int count = entry.getValue();
      printWriter.write(count);
      printWriter.println();
    }
  }
}
