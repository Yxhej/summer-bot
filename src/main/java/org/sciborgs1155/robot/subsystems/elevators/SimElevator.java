package org.sciborgs1155.robot.subsystems.elevators;

import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.START_POSITION;
import static org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.TOLERANCE;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import monologue.Annotations.Log;
import org.sciborgs1155.lib.Utils;
import org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.Horizontal;
import org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.Vertical;

public class SimElevator implements ElevatorIO {
  public enum ElevatorType {
    VERTICAL,
    HORIZONTAL;
  }

  private final ElevatorSim sim;

  @Log.NT private final ProfiledPIDController pid;
  @Log.NT private final ElevatorFeedforward ff;
  private boolean atGoal;

  public SimElevator(ElevatorType type) {
    sim =
        switch (type) {
          case VERTICAL ->
              new ElevatorSim(
                  LinearSystemId.createElevatorSystem(
                      DCMotor.getFalcon500Foc(1),
                      Vertical.WEIGHT.in(Kilograms),
                      0.0181864,
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
                      0.0181864,
                      Horizontal.GEARING),
                  DCMotor.getFalcon500Foc(1),
                  Horizontal.MIN_HEIGHT.in(Meters),
                  Horizontal.MAX_HEIGHT.in(Meters),
                  false,
                  START_POSITION.in(Meters));
        };

    pid =
        switch (type) {
          case VERTICAL ->
              new ProfiledPIDController(
                  Vertical.kP,
                  Vertical.kI,
                  Vertical.kD,
                  new TrapezoidProfile.Constraints(Vertical.MAX_VELOCITY, Vertical.MAX_ACCEL));
          case HORIZONTAL ->
              new ProfiledPIDController(
                  Horizontal.kP,
                  Horizontal.kI,
                  Horizontal.kD,
                  new TrapezoidProfile.Constraints(Horizontal.MAX_VELOCITY, Horizontal.MAX_ACCEL));
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
  public void updateGoal(double goal) {
    atGoal = Utils.inRange(position(), goal, TOLERANCE.in(Meters));
    pid.setGoal(goal);
    setVoltage(pid.calculate(position()) + ff.calculate(setpoint().velocity));
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
  public TrapezoidProfile.State setpoint() {
    return pid.getSetpoint();
  }

  @Override
  public boolean atGoal() {
    return atGoal;
  }
}
