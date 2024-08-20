package org.sciborgs1155.robot.claws.intake;

import static edu.wpi.first.units.Units.Radians;
import static org.sciborgs1155.robot.Ports.CubeIntake.*;
import static org.sciborgs1155.robot.claws.intake.IntakeConstants.*;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import org.sciborgs1155.robot.claws.WristIO;

public class RealWrist implements WristIO {

  private final TalonFX motor;
  private final DutyCycleEncoder encoder;

  private final ProfiledPIDController pid =
      new ProfiledPIDController(
          kP, kI, kD, new TrapezoidProfile.Constraints(MAX_VELOCITY, MAX_ACCELERATION));
  private final ArmFeedforward ff = new ArmFeedforward(kS, kG, kV, kA);

  public RealWrist() {
    motor = new TalonFX(INTAKE_ROTATION);
    encoder = new DutyCycleEncoder(INTAKE_ENCODER);

    TalonFXConfiguration cfg = new TalonFXConfiguration();
    motor.getConfigurator().apply(cfg);

    encoder.setDistancePerRotation(POSITION_FACTOR.in(Radians));
  }

  @Override
  public void setVoltage(double voltage) {
    motor.setVoltage(voltage);
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
    return encoder.getDistance();
  }

  @Override
  public boolean atGoal() {
    return pid.atGoal();
  }
}