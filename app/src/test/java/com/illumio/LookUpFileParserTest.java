package com.illumio;

import com.illumio.model.LookUpTable;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LookUpFileParserTest {

  /**
   * Instead of using Mockito to mock out the method, I decide to test the method with actual file. This is faster and
   * this validates the file I/O as well
   */

  private LookUpFileParser lookUpFileParser;

  @Test
  void parseLookUpFile_noSuchFile_error() {
    lookUpFileParser = new LookUpFileParser("./nosuchfile.txt");
    assertThrows(FileNotFoundException.class, () -> lookUpFileParser.parseLookUpFile());
  }

  @Test
  void parseLookUpFile_fileIsDirectory_error() {
    lookUpFileParser = new LookUpFileParser("./src");
    assertThrows(NoSuchFileException.class, () -> lookUpFileParser.parseLookUpFile());
  }

  @Test
  void parseLookUpFile_singleEntryFile_pass() throws AccessDeniedException, FileNotFoundException, NoSuchFileException {
    lookUpFileParser = new LookUpFileParser("src/test/resources/lookup-1line.csv");

    LookUpTable lookupTable = lookUpFileParser.parseLookUpFile();
    assertEquals("SV_P1",lookupTable.getTag(25, "TCP"));
  }

  @Test
  void parseLookUpFile_multipleEntry_pass() throws AccessDeniedException, FileNotFoundException, NoSuchFileException {
    lookUpFileParser = new LookUpFileParser("src/test/resources/lookup-2line.csv");

    LookUpTable lookupTable = lookUpFileParser.parseLookUpFile();
    assertEquals("SV_P1",lookupTable.getTag(25, "TCP"));
    assertEquals("SV_P2",lookupTable.getTag(68, "UDP"));
  }

  @Test
  void parseLookUpFile_multipleEntrySameTag_pass() throws AccessDeniedException, FileNotFoundException, NoSuchFileException {
    lookUpFileParser = new LookUpFileParser("src/test/resources/lookup-2lines-same-tag.csv");

    LookUpTable lookupTable = lookUpFileParser.parseLookUpFile();
    assertEquals("SV_P1",lookupTable.getTag(25, "TCP"));
    assertEquals("SV_P1",lookupTable.getTag(68, "UDP"));
  }

}