package org.sciborgs1155.robot.shoulder;

public class NoShoulder implements ShoulderIO {

  @Override
  public void setVoltage(double voltage) {}

  @Override
  public void updateSetpoint(double goal) {}

  @Override
  public double position() {
    return 0;
  }

  @Override
  public boolean atGoal() {
    return false;
  }
}
