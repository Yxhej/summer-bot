package org.sciborgs1155.lib;

public class Utils {
  public static boolean inRange(double position, double setpoint, double tolerance) {
    double error = Math.abs(setpoint - position);
    return error <= tolerance;
  }
}
