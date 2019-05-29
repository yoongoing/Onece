package com.example.capstone;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
  static final String HEXES = "0123456789ABCDEF";

  public static String scrape(String resp, String start, String stop) {
    int offset, len;
    if ((offset = resp.indexOf(start)) < 0)
      return "";
    if ((len = resp.indexOf(stop, offset + start.length())) < 0)
      return "";
    return resp.substring(offset + start.length(), len);
  }

  public static String md5(String data) {
    try {
      MessageDigest digester = MessageDigest.getInstance("MD5");
      digester.update(data.getBytes());
      byte[] messageDigest = digester.digest();
      return Utils.byteArrayToHexString(messageDigest);
    } catch (NoSuchAlgorithmException e) {
    }
    return null;
  }

  public static String byteArrayToHexString(byte[] array) {
    StringBuffer hexString = new StringBuffer();
    for (byte b : array) {
      int intVal = b & 0xff;
      if (intVal < 0x10)
        hexString.append("0");
      hexString.append(Integer.toHexString(intVal));
    }
    return hexString.toString();
  }


  public static String getHex(byte[] raw) {
    if (raw == null) {
      return null;
    }
    final StringBuilder hex = new StringBuilder(2 * raw.length);
    for (final byte b : raw) {
      hex.append(HEXES.charAt((b & 0xF0) >> 4))
              .append(HEXES.charAt((b & 0x0F)));
    }
    return hex.toString();
  }
}