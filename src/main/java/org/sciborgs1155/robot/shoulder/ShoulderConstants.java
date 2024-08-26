package org.sciborgs1155.robot.shoulder;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Second;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;

// https://github.com/MrThru/2023ChargedUp/blob/2cf4747920ac1db82e7a7dd8507048b93d6382ca/src/main/java/com/team1323/frc2023/Constants.java#L400
public class ShoulderConstants {
  public static final double kMotorRotationsPerShoulderRotation =
      69.444444; // 61.728395;// 69.444444;
  public static final Measure<Angle> CANCODER_POSITION_FACTOR = Degrees.of(4096.0 / 360.0);

  public static final double GEARING = 0;
  public static final double MOI = 0;

  public static final Measure<Distance> LENGTH =
      Inches.of(18.625 + 5.0); // 5 inches to account for a cone
  public static final Measure<Mass> WEIGHT = Pounds.of(0);

  // TODO THESE ARE ABSOLUTELY INSANE PLEASE GUESS THEM
  public static final Measure<Velocity<Angle>> MAX_VELOCITY = RotationsPerSecond.of(106);
  public static final Measure<Velocity<Velocity<Angle>>> MAX_ACCEL =
      RotationsPerSecond.per(Second).of(20);

  public static final Measure<Angle> STARTING_ANGLE = Degrees.of(0);
  public static final Measure<Angle> SCORING_ANGLE = Degrees.of(0);

  public static final Measure<Angle> CONE_INTAKE_ANGLE = Degrees.of(0);
  public static final Measure<Angle> CUBE_INTAKE_ANGLE = Degrees.of(0);

  public static final Measure<Angle> MIN_ANGLE = Degrees.of(-97.5);
  public static final Measure<Angle> MAX_ANGLE = Degrees.of(180.0);

  public static final Measure<Angle> TOLERANCE = Degrees.of(6.0);

  public static final double kP = 1.0;
  public static final double kI = 0.0;
  public static final double kD = 0.0;

  public static final double kS = 0;
  public static final double kG = 0;
  public static final double kV = 1.0;
  public static final double kA = 5.0;

  public static final int SUPPLY_CURRENT_LIMIT = 150;

  // public static final AbsoluteEncoderInfo kAbsoluteEncoderInfo = new AbsoluteEncoderInfo(
  //     1.0,
  //     Settings.kIsUsingCompBot ? 28.916016 : 244.511719,
  //     Settings.kIsUsingCompBot ? 177.0 : 174.0,
  //     -95.0,
  //     185.0
  // );

  // public static final CurrentZeroingConfig kCurrentZeroingConfig = new CurrentZeroingConfig(
  //     0.1,
  //     100.0,
  //     176.0,
  //     175.0
  // );
}
