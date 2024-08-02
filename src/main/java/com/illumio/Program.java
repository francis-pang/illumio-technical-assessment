package com.illumio;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Program {
  public static void main(String[] args) {
    Program program = new Program();
    // fix location for lookup table
    String lookUpCsvFilePath = "";

    // Parse the lookup table
    // Store it inside a data structure for look up
    Map<LookUp, String> lookUpTable = program.parseLookUpFile(lookUpCsvFilePath);

    // Set up the output data structure


    // Parse the file

    // Output the result

  }

  private Map<LookUp, String> parseLookUpFile(String filePath) {
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

    Map<LookUp, String> lookUpTable = new HashMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
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
        LookUp lookUp = new LookUp(port, protocol);
        lookUpTable.put(lookUp, tag);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return lookUpTable;
  }


  private record LookUp(int port, String protocol) {}
}
