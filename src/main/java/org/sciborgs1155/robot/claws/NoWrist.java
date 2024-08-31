package org.sciborgs1155.robot.claws;

public class NoWrist implements WristIO {

  @Override
  public void setVoltage(double voltage) {}

  @Override
  public void updateSetpoint(double goal) {}

  @Override
  public double position() {
    return 0;
  }

  @Override
  public boolean atSetpoint() {
    return true;
  }
}
