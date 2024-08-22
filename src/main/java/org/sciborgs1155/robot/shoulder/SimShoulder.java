package org.sciborgs1155.robot.shoulder;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.*;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class SimShoulder implements ShoulderIO {
  private final SingleJointedArmSim sim =
      new SingleJointedArmSim(
          DCMotor.getFalcon500Foc(1),
          GEARING,
          MOI,
          LENGTH.in(Meters),
          MIN_ANGLE.in(Radians),
          MAX_ANGLE.in(Radians),
          true,
          STARTING_ANGLE.in(Radians));
  private final ProfiledPIDController pid =
      new ProfiledPIDController(
          kP, kI, kD, new TrapezoidProfile.Constraints(MAX_VELOCITY, MAX_ACCEL));
  private final ArmFeedforward ff = new ArmFeedforward(kS, kG, kV, kA);

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
