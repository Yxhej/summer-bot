package org.sciborgs1155.robot.subsystems.shoulder;

import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.subsystems.shoulder.ShoulderConstants.*;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import monologue.Annotations.Log;

public class SimShoulder implements ShoulderIO {
  private final SingleJointedArmSim sim =
      new SingleJointedArmSim(
          LinearSystemId.createSingleJointedArmSystem(
              DCMotor.getFalcon500Foc(1),
              SingleJointedArmSim.estimateMOI(LENGTH.in(Meters), MASS.in(Kilograms)),
              GEARING),
          DCMotor.getFalcon500Foc(1),
          GEARING,
          LENGTH.in(Meters),
          MIN_ANGLE.in(Radians),
          MAX_ANGLE.in(Radians),
          true,
          STARTING_ANGLE.in(Radians));

  @Log.NT
  private final ProfiledPIDController pid =
      new ProfiledPIDController(
          kP, kI, kD, new TrapezoidProfile.Constraints(MAX_VELOCITY, MAX_ACCEL));

  @Log.NT private final ArmFeedforward ff = new ArmFeedforward(kS, kG, kV, kA);

  public SimShoulder() {
    pid.setTolerance(TOLERANCE.in(Radians));
  }

  @Override
  public void setVoltage(double voltage) {
    log("voltage in", voltage);
    sim.setInputVoltage(voltage);
    sim.update(PERIOD.in(Seconds));
  }

  @Override
  public void updateSetpoint(double goal) {
    pid.setGoal(goal);
    setVoltage(
        pid.calculate(position().getRadians(), goal)
            + ff.calculate(setpoint().position, setpoint().velocity));
  }

  @Override
  public Rotation2d position() {
    return Rotation2d.fromRadians(sim.getAngleRads());
  }

  @Override
  public boolean atGoal() {
    return pid.atGoal();
  }

  @Override
  public State setpoint() {
    return pid.getSetpoint();
  }

  @Log.NT
  public double voltage() {
    return sim.getCurrentDrawAmps();
  }
}
