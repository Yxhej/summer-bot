package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.*;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import monologue.Annotations.Log;

public class RealElevator implements ElevatorIO {
  private final TalonFX motor;

  private final PositionVoltage positionRequest;
  private final StatusSignal<Double> position;
  private final StatusSignal<Double> velocity;

  @Log.NT private double goal = START_POSITION.in(Meters);

  public RealElevator(int motorPort, TalonFXConfiguration config) {
    motor = new TalonFX(motorPort);
    position = motor.getPosition();
    velocity = motor.getVelocity();

    position.setUpdateFrequency(1000);
    velocity.setUpdateFrequency(1000);

    TalonFXConfiguration defaults = new TalonFXConfiguration();
    motor.getConfigurator().apply(defaults);
    motor.getConfigurator().apply(config);

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

  @Override
  public double velocity() {
    return motor.getVelocity().getValueAsDouble();
  }

  @Override
  public boolean atGoal() {
    return Math.abs(position() - goal) < TOLERANCE.in(Meters);
  }

  @Override
  public TrapezoidProfile.State setpoint() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setpoint'");
  }
}
