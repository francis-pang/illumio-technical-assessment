package com.illumio;

import com.illumio.model.FlowLog;
import com.illumio.model.LookUpTable;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

public class Program {
  public static void main(String[] args) {
    InputParameters inputParameters = parseMainArguments(args);

    LookUpFileParser lookUpFileParser = new LookUpFileParser(inputParameters.lookUpCsvPath);
    LookUpTable lookUpTable;
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
    FlowLogParser flowLogParser = new FlowLogParser(inputParameters.flowLogPath, lookUpTable);
    FlowLog flowLog = flowLogParser.parseFlowLogFile();

    // Output the result
    OutputWriter outputWriter = new OutputWriter(inputParameters.outputPath);
    outputWriter.outputResultToFile(flowLog);
  }

  private static InputParameters parseMainArguments(String[] arguments) {
    String lookUpCsvPath = arguments[0];
    validateInputString(lookUpCsvPath);
    String flowLogPath = arguments[1];
    validateInputString(flowLogPath);
    String outputPath = arguments[2];
    return new InputParameters(lookUpCsvPath, flowLogPath, outputPath);
  }

  private static void validateInputString(String input) {
    if (input == null || input.isBlank()) {
      String errorMessage = String.format("Look Up Table file is empty. path:%s",
          (input == null) ? "NULL" : input);
      throw new IllegalArgumentException(errorMessage);
    }
  }

  private record InputParameters(String lookUpCsvPath,
                                 String flowLogPath,
                                 String outputPath) {}
}
