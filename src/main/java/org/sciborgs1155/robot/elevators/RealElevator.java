package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Meters;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.*;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

public class RealElevator implements ElevatorIO {
  private final TalonFX motor;
  private final PositionVoltage positionRequest;
  private double setpoint;

  public RealElevator(int motorPort, TalonFXConfiguration config) {
    motor = new TalonFX(motorPort);
    positionRequest = new PositionVoltage(0);

    TalonFXConfiguration factory = new TalonFXConfiguration();
    motor.getConfigurator().apply(factory);

    motor.getConfigurator().apply(config);
  }

  @Override
  public void setVoltage(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void updateSetpoint(double setpoint, double ff) {
    motor.setControl(positionRequest.withPosition(setpoint).withFeedForward(ff).withSlot(0));
    this.setpoint = setpoint;
  }

  @Override
  public double position() {
    return motor.getPosition().getValueAsDouble();
  }

  @Override
  public double velocity() {
    return motor.getVelocity().getValueAsDouble();
  }

  @Override
  public boolean atSetpoint() {
    return position() - setpoint < TOLERANCE.in(Meters);
  }
}
