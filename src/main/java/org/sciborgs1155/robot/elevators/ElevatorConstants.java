package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;

public class ElevatorConstants {
  static MotorOutputConfigs output =
      new MotorOutputConfigs()
          .withNeutralMode(NeutralModeValue.Brake)
          .withInverted(InvertedValue.Clockwise_Positive);

  static CurrentLimitsConfigs limits =
      new CurrentLimitsConfigs().withStatorCurrentLimit(15).withSupplyCurrentLimit(20);

  public static final Measure<Distance> TOLERANCE = Inches.of(2);
  public static final Measure<Distance> START_POSITION = Meters.of(0.7);

  public static class Vertical {
    public static final Measure<Mass> WEIGHT = Kilograms.of(0);

    public static final Measure<Distance> MIN_HEIGHT = Inches.of(0);
    public static final Measure<Distance> MAX_HEIGHT = Inches.of(20);
    public static final double GEARING = 10;

    public static final double kP = 1;
    public static final double kI = 0;
    public static final double kD = 0.05;

    public static final double kS = 0.0823;
    public static final double kG = 1.23;
    public static final double kV = 0; // 1
    public static final double kA = 0; // 4? tf?

    static Slot0Configs controlGains =
        new Slot0Configs()
            .withKP(kP)
            .withKI(kI)
            .withKD(kD)
            .withKS(kS)
            .withKG(kG)
            .withKV(kV)
            .withKA(kA)
            .withGravityType(GravityTypeValue.Elevator_Static);

    static FeedbackConfigs sensors = new FeedbackConfigs().withSensorToMechanismRatio(GEARING);

    public static TalonFXConfiguration CONFIG =
        new TalonFXConfiguration()
            .withMotorOutput(output)
            .withFeedback(sensors)
            .withSlot0(controlGains)
            .withCurrentLimits(limits);

    public static final double kMinControlHeight = 0.0;
    public static final double kMaxControlHeight = 20.0;
  }

  public static class Horizontal {
    public static final Measure<Mass> WEIGHT = Kilograms.of(0);

    public static final Measure<Distance> MIN_HEIGHT = Inches.of(0);
    public static final Measure<Distance> MAX_HEIGHT = Inches.of(30);
    public static final double GEARING = 5;

    public static final double kP = 1;
    public static final double kI = 0;
    public static final double kD = 0.05;

    public static final double kS = 0.0823;
    public static final double kG = 1.23;
    public static final double kV = 0;
    public static final double kA = 0;

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
