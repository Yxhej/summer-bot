package org.sciborgs1155.robot.claws.intake;

import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
// TODO find way to abstract both claws into one
public interface WristIO {
    void setVoltage(double voltage);

    default void updateSetpoint(Measure<Angle> angle, double ff) {
        updateSetpoint(angle.in(Radians), ff);
    }

    void updateSetpoint(double setpoint, double ff);

    double position();

    double velocity();

    boolean atGoal();

    boolean atSetpoint();
}
