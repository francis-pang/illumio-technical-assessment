package com.illumio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {
  Program program = new Program();

  @Test
  void main_fullTest_success() {
    String lookUpPath = "src/test/resources/lookup-full.csv";
    String flowLogPath = "src/test/resources/flow-log-full.txt";
    String outputPath = "output.txt";

    Program.main(new String[]{lookUpPath, flowLogPath, outputPath});
  }
}