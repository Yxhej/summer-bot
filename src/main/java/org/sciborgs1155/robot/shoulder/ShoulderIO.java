package org.sciborgs1155.robot.shoulder;

import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;

public interface ShoulderIO {
  void setVoltage(double voltage);

  default void updateSetpoint(Measure<Angle> angle) {
    updateSetpoint(angle.in(Radians));
  }

  void updateSetpoint(double goal);

  double position();

  boolean atGoal();
}
