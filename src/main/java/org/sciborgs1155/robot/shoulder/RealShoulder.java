package org.sciborgs1155.robot.shoulder;

import static org.sciborgs1155.robot.Ports.Shoulder.*;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.*;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

public class RealShoulder implements ShoulderIO {
  private final TalonFX motor = new TalonFX(MOTOR);
  private final CANcoder encoder = new CANcoder(CANCODER);

  public RealShoulder() {
    TalonFXConfiguration motorCfg = new TalonFXConfiguration();
    motor.getConfigurator().apply(motorCfg);

    motorCfg.CurrentLimits.SupplyCurrentLimitEnable = true;
    motorCfg.CurrentLimits.SupplyCurrentLimit = SUPPLY_CURRENT_LIMIT;

    CANcoderConfiguration encoderCfg = new CANcoderConfiguration();
    encoder.getConfigurator().apply(encoderCfg);

    encoderCfg.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
    encoderCfg.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;

    encoder.getConfigurator().apply(encoderCfg);
  }

  @Override
  public void setVoltage(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void updateSetpoint(double goal) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSetpoint'");
  }

  @Override
  public double position() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'position'");
  }

  @Override
  public boolean atGoal() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'atGoal'");
  }
}
