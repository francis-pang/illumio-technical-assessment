package com.illumio;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import com.illumio.model.PortProtocol;

public class Program {
  public static void main(String[] args) {
    Program program = new Program();
    // fix location for lookup table
    String lookUpCsvFilePath = "";

    // Parse the lookup table
    // Store it inside a data structure for look up
    Map<PortProtocol, String> lookUpTable = program.parseLookUpFile(lookUpCsvFilePath);

    // Set up the output data structure
    Map<String, Integer> tagCount = new HashMap<>();
    Map<PortProtocol, Integer> lookUpCount = new HashMap<>();

    // Parse the file
    // fixed location for log file
    String flowDataLogFilePath = "";
    program.parseFlowLogFile(flowDataLogFilePath, lookUpTable, tagCount, lookUpCount);

    // Output the result
    String outputFilePath = "";
    program.outputResultToFile(outputFilePath, tagCount, lookUpCount);
  }

  private void outputResultToFile(String outputFilePath,
                                  Map<String, Integer> tagCount,
                                  Map<PortProtocol, Integer> portProtocolCount) {
    try(PrintWriter printWriter = new PrintWriter(new FileOutputStream(outputFilePath))) {
      writeTagCount(printWriter, tagCount);
      writePortProtocolCombinationCount(printWriter, portProtocolCount);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void writePortProtocolCombinationCount(PrintWriter printWriter,
                                                 Map<PortProtocol, Integer> portProtocolCount) {
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

  private void writeTagCount(PrintWriter printWriter, Map<String, Integer> tagCount) {
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

  private void parseFlowLogFile(String logFilePath,
                                Map<PortProtocol, String> lookUpTable,
                                Map<String, Integer> tagCount,
                                Map<PortProtocol, Integer> lookUpCount) {
    File file = new File(logFilePath);
    if (!file.exists()) {
      // exception
    }
    if (!file.isFile()) {
      // exception
    }

    if (!file.canRead()) {
      // exception
    }

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
      String line;
      while((line = bufferedReader.readLine()) != null) {
        String[] splitData = line.split(" ");
        // Assume file has no dirty data, and all formatted correctly
        // Sample: 2 123456789010 eni-1235b8ca123456789 172.31.16.139 172.31.16.21 20641 22 6 20 4249 1418530010 1418530070 ACCEPT OK
        String dstPortString = splitData[6];
        int dstPort = Integer.valueOf(dstPortString);
        // Need to map from  Assigned Internet Protocol Numbers https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml
        String rawProtocol = splitData[7];
        String protocol = convertToProtocolString(rawProtocol);
        PortProtocol portProtocol = new PortProtocol(dstPort, protocol);
        String tag = lookUpTable.get(portProtocol);
        if (tag == null || tag.isBlank()) {
          // warning
        } else {
          tagCount.compute(tag, (k,v) -> (v == null) ? 1 : v + 1);
        }

        lookUpCount.compute(portProtocol, (k, v) -> (v == null) ? 1 : v + 1);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String convertToProtocolString(String protocolNumberString) {
    int protocol = Integer.valueOf(protocolNumberString);
    // TODO: Stub
    return "TCP";
  }

  private Map<PortProtocol, String> parseLookUpFile(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      // exception
    }
    if (!file.isFile()) {
      // exception
    }

    if (!file.canRead()) {
      // exception
    }

    Map<PortProtocol, String> lookUpTable = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
      String line;
      // Per line parsing
      while((line = bufferedReader.readLine()) != null) {
        String[] splitData = line.split(",");
        // Assume file is always correct
        // Assume no duplicate entry, if not only take latest
        if (splitData.length != 3) {
          // exception
        }
        String portString = splitData[0];
        int port = Integer.valueOf(portString);
        String protocol = splitData[1];
        String tag = splitData[2];
        PortProtocol portProtocol = new PortProtocol(port, protocol);
        lookUpTable.put(portProtocol, tag);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return lookUpTable;
  }



}
