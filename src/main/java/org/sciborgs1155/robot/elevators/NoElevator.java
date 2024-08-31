package org.sciborgs1155.robot.elevators;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class NoElevator implements ElevatorIO {

  @Override
  public void setVoltage(double voltage) {}

  @Override
  public void updateSetpoint(double goal) {}

  @Override
  public double position() {
    return 0;
  }

  @Override
  public double velocity() {
    return 0;
  }

  @Override
  public boolean atGoal() {
    return true;
  }

  @Override
  public TrapezoidProfile.State setpoint() {
    return new TrapezoidProfile.State();
  }
}
