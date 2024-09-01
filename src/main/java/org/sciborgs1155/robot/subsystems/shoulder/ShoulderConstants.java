package org.sciborgs1155.robot.subsystems.shoulder;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Second;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;

// https://github.com/MrThru/2023ChargedUp/blob/2cf4747920ac1db82e7a7dd8507048b93d6382ca/src/main/java/com/team1323/frc2023/Constants.java#L400
public class ShoulderConstants {
  // Ratio of motor rotations per shoulder rotations
  public static final double GEARING = 69.444444;
  public static final Measure<Angle> CANCODER_POSITION_FACTOR = Degrees.of(4096.0 / 360.0);

  public static final Measure<Distance> LENGTH =
      Inches.of(18.625); // 5 inches to account for a cone
  public static final Measure<Mass> MASS = Kilograms.of(2);

  // TODO THESE ARE ABSOLUTELY INSANE PLEASE GUESS THEM
  public static final Measure<Velocity<Angle>> MAX_VELOCITY = DegreesPerSecond.of(200);
  public static final Measure<Velocity<Velocity<Angle>>> MAX_ACCEL =
      DegreesPerSecond.per(Second).of(100);
  public static final Measure<Angle> STARTING_ANGLE = Degrees.of(0);

  public static class Scoring {
    public static final Measure<Angle> CONE_INTAKE_ANGLE = Degrees.of(-53.76);
    public static final Measure<Angle> CUBE_INTAKE_ANGLE = Degrees.of(-85);
    public static final Measure<Angle> CONE_FLIP_ANGLE = Degrees.of(-95);

    public static final Measure<Angle> CUBE_NODE_ANGLE = Degrees.of(55.0);

    public static final Measure<Angle> CONE_MID_NODE = Degrees.of(49.5);
    public static final Measure<Angle> CONE_HIGH_NODE = Degrees.of(36.0);

    public static final Measure<Angle> STOW_ANGLE = Degrees.of(140.0);
    public static final Measure<Angle> CONE_COMMUNITY_STOW = Degrees.of(124.0);
    public static final Measure<Angle> CONE_PRESCORE_STOW = Degrees.of(104.0);
  }

  public static final Measure<Angle> MIN_ANGLE = Degrees.of(-180);
  public static final Measure<Angle> MAX_ANGLE = Degrees.of(180.0);

  public static final Measure<Angle> TOLERANCE = Degrees.of(2.0);

  public static final double kP = 3;
  public static final double kI = 0.0;
  public static final double kD = 0.15;

  public static final double kS = 0.0;
  public static final double kG = 0.13718;
  public static final double kV = 1.46;
  public static final double kA = 0.0;

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
