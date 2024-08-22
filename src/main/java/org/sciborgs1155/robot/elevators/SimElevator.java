package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.START_POSITION;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.TOLERANCE;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Horizontal;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical;

public class SimElevator implements ElevatorIO {
  public enum ElevatorType {
    VERTICAL,
    HORIZONTAL;
  }

  private final ElevatorSim sim;
  private final PIDController pid;
  private final ElevatorFeedforward ff;

  public SimElevator(ElevatorType type) {
    sim =
        switch (type) {
          case VERTICAL ->
              new ElevatorSim(
                  LinearSystemId.createElevatorSystem(
                      DCMotor.getFalcon500Foc(1),
                      Vertical.WEIGHT.in(Kilograms),
                      Vertical.MAX_HEIGHT.in(Meters),
                      Vertical.GEARING),
                  DCMotor.getFalcon500Foc(1),
                  Vertical.MIN_HEIGHT.in(Meters),
                  Vertical.MAX_HEIGHT.in(Meters),
                  true,
                  START_POSITION.in(Meters));
          case HORIZONTAL ->
              new ElevatorSim(
                  LinearSystemId.createElevatorSystem(
                      DCMotor.getFalcon500Foc(1),
                      Horizontal.WEIGHT.in(Kilograms),
                      Horizontal.MAX_HEIGHT.in(Meters),
                      Horizontal.GEARING),
                  DCMotor.getFalcon500Foc(1),
                  Horizontal.MIN_HEIGHT.in(Meters),
                  Horizontal.MAX_HEIGHT.in(Meters),
                  false,
                  START_POSITION.in(Meters));
        };

    pid =
        switch (type) {
          case VERTICAL -> new PIDController(Vertical.kP, Vertical.kI, Vertical.kD);
          case HORIZONTAL -> new PIDController(Horizontal.kP, Horizontal.kI, Horizontal.kD);
        };

    ff =
        switch (type) {
          case VERTICAL ->
              new ElevatorFeedforward(Vertical.kS, Vertical.kG, Vertical.kV, Vertical.kA);
          case HORIZONTAL ->
              new ElevatorFeedforward(Horizontal.kS, Horizontal.kG, Horizontal.kV, Horizontal.kA);
        };

    pid.setTolerance(TOLERANCE.in(Meters));
  }

  @Override
  public void setVoltage(double voltage) {
    sim.setInputVoltage(voltage);
    sim.update(PERIOD.in(Seconds));
  }

  @Override
  public void updateSetpoint(double setpoint) {
    pid.setSetpoint(setpoint);
    setVoltage(pid.calculate(setpoint) + ff.calculate(0));
  }

  @Override
  public double position() {
    return sim.getPositionMeters();
  }

  @Override
  public double velocity() {
    return sim.getVelocityMetersPerSecond();
  }

  @Override
  public boolean atSetpoint() {
    return pid.atSetpoint();
  }
}
