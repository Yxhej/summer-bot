package org.sciborgs1155.robot.subsystems.shoulder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;

public class NoShoulder implements ShoulderIO {

  @Override
  public void setVoltage(double voltage) {}

  @Override
  public void updateSetpoint(double goal) {}

  @Override
  public Rotation2d position() {
    return new Rotation2d();
  }

  @Override
  public boolean atGoal() {
    return false;
  }

  @Override
  public State setpoint() {
    return new TrapezoidProfile.State();
  }
}
