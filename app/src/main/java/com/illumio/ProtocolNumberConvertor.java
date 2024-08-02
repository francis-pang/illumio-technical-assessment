package com.illumio;

import com.illumio.model.InternetProtocolNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtocolNumberConvertor {
  private static final Logger logger = LogManager.getLogger(ProtocolNumberConvertor.class);
  public static String convertToProtocolString(String protocolNumberString) {
    int protocol = Integer.valueOf(protocolNumberString);
    InternetProtocolNumber internetProtocolNumber = InternetProtocolNumber.searchByNumber(protocol);
    if (internetProtocolNumber == null) {
      logger.warn("Unable to find a Internet Protocol for the number {}", protocol);
      return "";
    }
    return internetProtocolNumber.getKeyword();
  }
}
