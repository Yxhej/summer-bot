package org.sciborgs1155.robot.claws;

import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.claws.claw.ClawConstants.TOLERANCE;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.claws.claw.ClawConstants;
import org.sciborgs1155.robot.claws.intake.IntakeConstants;

public class SimWrist implements WristIO {
  private final SingleJointedArmSim sim;
  @Log.NT private final PIDController pid;
  @Log.NT private final ArmFeedforward ff;

  public SimWrist(WristType type) {
    sim =
        switch (type) {
          case INTAKE ->
              new SingleJointedArmSim(
                  DCMotor.getFalcon500Foc(1),
                  IntakeConstants.GEARING,
                  SingleJointedArmSim.estimateMOI(
                      IntakeConstants.LENGTH.in(Meters), IntakeConstants.MASS.in(Kilograms)),
                  IntakeConstants.LENGTH.in(Meters),
                  IntakeConstants.MIN_ANGLE.in(Radians),
                  IntakeConstants.MAX_ANGLE.in(Radians),
                  true,
                  IntakeConstants.UP_ANGLE.in(Radians));
          case SCORER ->
              new SingleJointedArmSim(
                  DCMotor.getFalcon500Foc(1),
                  ClawConstants.GEARING,
                  SingleJointedArmSim.estimateMOI(
                      ClawConstants.LENGTH.in(Meters), ClawConstants.MASS.in(Kilograms)),
                  ClawConstants.LENGTH.in(Meters),
                  ClawConstants.MIN_ANGLE.in(Radians),
                  ClawConstants.MAX_ANGLE.in(Radians),
                  true,
                  ClawConstants.STARTING_ANGLE.in(Radians));
        };
    pid =
        switch (type) {
          case INTAKE ->
              new PIDController(IntakeConstants.kP, IntakeConstants.kI, IntakeConstants.kD);
          case SCORER -> new PIDController(ClawConstants.kP, ClawConstants.kI, ClawConstants.kD);
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

    pid.setTolerance(TOLERANCE.in(Radians));
  }

  @Override
  public void setVoltage(double voltage) {
    sim.setInputVoltage(voltage);
    sim.update(PERIOD.in(Seconds));
  }

  @Override
  public void updateSetpoint(double setpoint) {
    pid.setSetpoint(setpoint);
    setVoltage(pid.calculate(position(), setpoint) + ff.calculate(setpoint, 0));
  }

  @Override
  public double position() {
    return sim.getAngleRads();
  }

  @Override
  public boolean atSetpoint() {
    return pid.atSetpoint();
  }
}
