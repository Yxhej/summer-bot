package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;

public interface ElevatorIO {
  void setVoltage(double voltage);

  default void updateSetpoint(Measure<Distance> position) {
    updateSetpoint(position.in(Meters));
  }

  void updateSetpoint(double setpoint);

  double position();

  double velocity();

  boolean atSetpoint();
}
