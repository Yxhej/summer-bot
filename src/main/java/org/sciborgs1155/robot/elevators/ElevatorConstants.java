package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Centimeters;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;

public class ElevatorConstants {
  static MotorOutputConfigs output =
      new MotorOutputConfigs()
          .withNeutralMode(NeutralModeValue.Brake)
          .withInverted(InvertedValue.Clockwise_Positive);

  static CurrentLimitsConfigs limits =
      new CurrentLimitsConfigs().withStatorCurrentLimit(15).withSupplyCurrentLimit(20);

  public static final Measure<Distance> TOLERANCE = Centimeters.of(3);

  public static class Vertical {
    public static final double GEARING = 10;

    public static final double kP = 1;
    public static final double kI = 0;
    public static final double kD = 0.05;

    public static final double kS = 0.0823;
    public static final double kG = 1.23;
    public static final double kV = 3.424;
    public static final double kA = 1.47;

    static Slot0Configs controlGains =
        new Slot0Configs()
            .withKP(kP)
            .withKI(kI)
            .withKD(kD)
            .withKS(kS)
            .withKG(kG)
            .withKV(kV)
            .withKA(kA);

    static FeedbackConfigs sensors = new FeedbackConfigs().withSensorToMechanismRatio(GEARING);

    public static TalonFXConfiguration CONFIG =
        new TalonFXConfiguration()
            .withMotorOutput(output)
            .withFeedback(sensors)
            .withSlot0(controlGains)
            .withCurrentLimits(limits);
  }

  public static class Horizontal {
    public static final double GEARING = 5;

    public static final double kP = 1;
    public static final double kI = 0;
    public static final double kD = 0.05;

    public static final double kS = 0.0823;
    public static final double kG = 1.23;
    public static final double kV = 3.424;
    public static final double kA = 1.47;

    static Slot0Configs controlGains =
        new Slot0Configs().withKP(kP).withKI(kI).withKD(kD).withKS(kS).withKV(kV).withKA(kA);

    static FeedbackConfigs sensors = new FeedbackConfigs().withSensorToMechanismRatio(GEARING);

    public static TalonFXConfiguration CONFIG =
        new TalonFXConfiguration()
            .withMotorOutput(output)
            .withFeedback(sensors)
            .withSlot0(controlGains)
            .withCurrentLimits(limits);
  }
}
