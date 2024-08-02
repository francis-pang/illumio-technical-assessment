package com.illumio;

import com.illumio.model.FlowLog;
import com.illumio.model.LookUpTable;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

public class Program {
  public static void main(String[] args) {
    Program program = new Program();
    // fix location for lookup table
    String lookUpCsvFilePath = "";

    // Parse the lookup table
    // Store it inside a data structure for look up
    LookUpFileParser lookUpFileParser = new LookUpFileParser(lookUpCsvFilePath);
    LookUpTable lookUpTable = null;
    try {
      lookUpTable = lookUpFileParser.parseLookUpFile();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NoSuchFileException e) {
      throw new RuntimeException(e);
    } catch (AccessDeniedException e) {
      throw new RuntimeException(e);
    }

    // Parse the file
    // fixed location for log file
    String flowDataLogFilePath = "";
    FlowLogParser flowLogParser = new FlowLogParser(flowDataLogFilePath, lookUpTable);
    FlowLog flowLog = flowLogParser.parseFlowLogFile();

    // Output the result
    String outputFilePath = "";
    OutputWriter outputWriter = new OutputWriter(outputFilePath);
    outputWriter.outputResultToFile(flowLog);
  }
}
