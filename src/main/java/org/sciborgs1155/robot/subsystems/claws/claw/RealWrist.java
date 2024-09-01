package org.sciborgs1155.robot.subsystems.claws.claw;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.Ports.Scorer.*;
import static org.sciborgs1155.robot.subsystems.claws.claw.ClawConstants.*;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.subsystems.claws.WristIO;

public class RealWrist implements WristIO {
  private final TalonFX motor;

  private final PositionVoltage positionRequest;
  private final StatusSignal<Double> position;
  private final StatusSignal<Double> velocity;

  @Log.NT private double goal = STARTING_ANGLE.in(Radians);

  public RealWrist() {
    motor = new TalonFX(CLAW_ROTATOR);
    position = motor.getPosition();
    velocity = motor.getVelocity();

    position.setUpdateFrequency(1000);
    velocity.setUpdateFrequency(1000);

    TalonFXConfiguration cfg = new TalonFXConfiguration();
    motor.getConfigurator().apply(cfg);

    positionRequest = new PositionVoltage(0).withSlot(0);
  }

  @Override
  public void setVoltage(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void updateSetpoint(double goal) {
    motor.setControl(positionRequest.withPosition(goal).withUpdateFreqHz(1 / PERIOD.in(Seconds)));
    this.goal = goal;
  }

  @Override
  public double position() {
    return motor.getPosition().getValueAsDouble();
  }

  public boolean atSetpoint() {
    return Math.abs(position() - goal) < TOLERANCE.in(Radians);
  }
}
