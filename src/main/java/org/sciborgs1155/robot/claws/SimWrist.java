package org.sciborgs1155.robot.claws;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import org.sciborgs1155.robot.claws.claw.ClawConstants;
import org.sciborgs1155.robot.claws.intake.IntakeConstants;

public class SimWrist implements WristIO {
  private final SingleJointedArmSim sim;
  private final ProfiledPIDController pid;
  private final ArmFeedforward ff;

  public SimWrist(WristType type) {
    sim =
        switch (type) {
          case INTAKE ->
              new SingleJointedArmSim(
                  DCMotor.getFalcon500Foc(1),
                  IntakeConstants.GEARING,
                  IntakeConstants.MOI,
                  IntakeConstants.LENGTH.in(Meters),
                  IntakeConstants.MIN_ANGLE.in(Radians),
                  IntakeConstants.MAX_ANGLE.in(Radians),
                  true,
                  IntakeConstants.STARTING_ANGLE.in(Radians));
          case SCORER ->
              new SingleJointedArmSim(
                  DCMotor.getFalcon500Foc(1),
                  ClawConstants.GEARING,
                  ClawConstants.MOI,
                  ClawConstants.LENGTH.in(Meters),
                  ClawConstants.MIN_ANGLE.in(Radians),
                  ClawConstants.MAX_ANGLE.in(Radians),
                  true,
                  ClawConstants.STARTING_ANGLE.in(Radians));
        };
    pid =
        switch (type) {
          case INTAKE ->
              new ProfiledPIDController(
                  IntakeConstants.kP,
                  IntakeConstants.kI,
                  IntakeConstants.kD,
                  new TrapezoidProfile.Constraints(
                      IntakeConstants.MAX_VELOCITY, IntakeConstants.MAX_ACCELERATION));
          case SCORER ->
              new ProfiledPIDController(
                  ClawConstants.kP,
                  ClawConstants.kI,
                  ClawConstants.kD,
                  new TrapezoidProfile.Constraints(
                      ClawConstants.MAX_VELOCITY, ClawConstants.MAX_ACCELERATION));
        };

    ff =
        switch (type) {
          case INTAKE ->
              new ArmFeedforward(
                  IntakeConstants.kS, IntakeConstants.kG, IntakeConstants.kV, IntakeConstants.kA);
          case SCORER ->
              new ArmFeedforward(
                  ClawConstants.kS, ClawConstants.kG, ClawConstants.kV, ClawConstants.kA);
        };
  }

  @Override
  public void setVoltage(double voltage) {
    sim.setInputVoltage(voltage);
    sim.update(PERIOD.in(Seconds));
  }

  @Override
  public void updateSetpoint(double goal) {
    pid.setGoal(goal);
    setVoltage(
        pid.calculate(position(), goal)
            + ff.calculate(pid.getSetpoint().position, pid.getSetpoint().velocity));
  }

  @Override
  public double position() {
    return sim.getAngleRads();
  }

  @Override
  public boolean atGoal() {
    return pid.atGoal();
  }
}
