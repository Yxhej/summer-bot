package org.sciborgs1155.robot.subsystems.shoulder;

import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import monologue.Logged;

public interface ShoulderIO extends Logged {
  void setVoltage(double voltage);

  default void updateSetpoint(Measure<Angle> angle) {
    updateSetpoint(angle.in(Radians));
  }

  void updateSetpoint(double goal);

  Rotation2d position();

  TrapezoidProfile.State setpoint();

  boolean atGoal();
}
