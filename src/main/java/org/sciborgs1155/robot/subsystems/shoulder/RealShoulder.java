package org.sciborgs1155.robot.subsystems.shoulder;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Seconds;
import static org.sciborgs1155.robot.Constants.PERIOD;
import static org.sciborgs1155.robot.Ports.Shoulder.*;
import static org.sciborgs1155.robot.subsystems.shoulder.ShoulderConstants.*;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;

public class RealShoulder implements ShoulderIO {
  private final TalonFX motor = new TalonFX(MOTOR);
  private final CANcoder encoder = new CANcoder(CANCODER);

  private final PositionVoltage positionRequest;
  private final StatusSignal<Double> position;
  private final StatusSignal<Double> velocity;

  private double goal;

  public RealShoulder() {
    TalonFXConfiguration motorCfg = new TalonFXConfiguration();
    motor.getConfigurator().apply(motorCfg);

    motorCfg.Slot0 =
        new Slot0Configs()
            .withGravityType(GravityTypeValue.Arm_Cosine)
            .withKP(kP)
            .withKI(kI)
            .withKD(kD)
            .withKS(kS)
            .withKG(kG)
            .withKV(kV)
            .withKA(kA);
    motorCfg.CurrentLimits.SupplyCurrentLimitEnable = true;
    motorCfg.CurrentLimits.SupplyCurrentLimit = SUPPLY_CURRENT_LIMIT;
    motorCfg.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
    motorCfg.Feedback.FeedbackRemoteSensorID = CANCODER;
    motorCfg.Feedback.SensorToMechanismRatio = CANCODER_POSITION_FACTOR.in(Radians);

    CANcoderConfiguration encoderCfg = new CANcoderConfiguration();
    encoder.getConfigurator().apply(encoderCfg);

    encoderCfg.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
    encoderCfg.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;

    encoder.getConfigurator().apply(encoderCfg);

    positionRequest = new PositionVoltage(0).withSlot(0);
    position = motor.getPosition();
    velocity = motor.getVelocity();
  }

  @Override
  public void setVoltage(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void updateSetpoint(double goal) {
    this.goal = goal;
    motor.setControl(positionRequest.withPosition(goal).withUpdateFreqHz(1 / PERIOD.in(Seconds)));
  }

  @Override
  public Rotation2d position() {
    return Rotation2d.fromRadians(position.getValueAsDouble());
  }

  @Override
  public boolean atGoal() {
    return Math.abs(position().getRadians() - goal) < TOLERANCE.in(Radians);
  }

  @Override
  public State setpoint() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setpoint'");
  }
}
