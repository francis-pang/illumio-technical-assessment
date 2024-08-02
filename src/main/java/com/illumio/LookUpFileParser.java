package com.illumio;

import com.illumio.model.LookUpTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LookUpFileParser {
  private String filePath;

  public LookUpFileParser(String filePath) {
    this.filePath = filePath;
  }

  public LookUpTable parseLookUpFile() {
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

    LookUpTable lookUpTable = new LookUpTable();

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
        lookUpTable.add(port, protocol, tag);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return lookUpTable;
  }
}
