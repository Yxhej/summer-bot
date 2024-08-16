package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;

public interface ElevatorIO {
    public enum Elevator {
        VERTICAL,
        HORIZONTAL;
    }

    void setVoltage(double voltage);

    default void updateSetpoint(Measure<Distance> position, double ff) {
        updateSetpoint(position.in(Meters), ff);
    }

    void updateSetpoint(double setpoint, double ff);

    double position();

    double velocity();

    boolean atGoal();

    boolean atSetpoint();
}
