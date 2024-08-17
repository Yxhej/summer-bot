package org.sciborgs1155.robot.claws;

import com.ctre.phoenix6.hardware.TalonFX;

public class RealWrist implements WristIO {

  private final TalonFX motor;

  public RealWrist(int motorPort, int encoderPort) {
    motor = new TalonFX(motorPort);
  }

  @Override
  public void setVoltage(double voltage) {}

  @Override
  public void updateSetpoint(double setpoint, double ff) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSetpoint'");
  }

  @Override
  public double position() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'position'");
  }

  @Override
  public double velocity() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'velocity'");
  }

  @Override
  public boolean atGoal() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'atGoal'");
  }

  @Override
  public boolean atSetpoint() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'atSetpoint'");
  }
}
