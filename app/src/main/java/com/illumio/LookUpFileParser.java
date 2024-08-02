package com.illumio;

import com.illumio.model.LookUpTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

public class LookUpFileParser {
  private static final Logger logger = LogManager.getLogger(LookUpFileParser.class);
  private File lookUpFile;

  public LookUpFileParser(String filePath) {
    this.lookUpFile = new File(filePath);
  }

  public LookUpTable parseLookUpFile() throws FileNotFoundException, NoSuchFileException, AccessDeniedException {
    if (!this.lookUpFile.exists()) {
      String errorMessage = String.format("File does not exist. path:%s", lookUpFile.getAbsolutePath());
      logger.warn(errorMessage);
      throw new FileNotFoundException(errorMessage);
    }
    if (!this.lookUpFile.isFile()) {
      String errorMessage = String.format("File is not of file type. path:%s", lookUpFile.getAbsolutePath());
      logger.warn(errorMessage);
      throw new NoSuchFileException(errorMessage);
    }

    if (!this.lookUpFile.canRead()) {
      String errorMessage = String.format("Unable to read file. path:%s", lookUpFile.getAbsolutePath());
      logger.warn(errorMessage);
      throw new AccessDeniedException(errorMessage);
    }

    LookUpTable lookUpTable = new LookUpTable();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(lookUpFile))) {
      String line;
      // Per line parsing
      while((line = bufferedReader.readLine()) != null) {
        if ("dstport,protocol,tag".equals(line)) {
          continue;
        }
        String[] splitData = line.split(",");
        // Assume file is always correct
        // Assume no duplicate entry, if not only take latest
        if (splitData.length != 3) {
          // exception
        }
        String portString = splitData[0];
        int port = Integer.valueOf(portString);
        String protocol = splitData[1];
        String upperProtocol = protocol.toUpperCase();
        String tag = splitData[2];
        lookUpTable.add(port, upperProtocol, tag);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return lookUpTable;
  }
}
