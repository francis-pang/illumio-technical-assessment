package com.illumio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolNumberConvertorTest {
  @Test
  void convertToProtocolString_lessThanZero_invalid() {
    assertEquals("", ProtocolNumberConvertor.convertToProtocolString("-1"));
  }

  void convertToProtocolString_notInsideEnum_invalid() {
    assertEquals("", ProtocolNumberConvertor.convertToProtocolString("253"));
  }

  void convertToProtocolString_tcp_valid() {
    assertEquals("TCP", ProtocolNumberConvertor.convertToProtocolString("6"));
  }
}