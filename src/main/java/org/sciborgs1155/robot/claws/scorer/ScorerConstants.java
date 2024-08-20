package org.sciborgs1155.robot.claws.scorer;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;

// https://github.com/MrThru/2023ChargedUp/blob/main/src/main/java/com/team1323/frc2023/Constants.java#L535
public class ScorerConstants {
  public static final double CUBE_SPEED = 0.25;
  public static final double CONE_SPEED = 0.8;

  public static final Measure<Angle> STARTING_ANGLE = Degrees.of(0);
  public static final Measure<Angle> TOLERANCE = Degrees.of(2);

  public static final Measure<Mass> WEIGHT = Kilograms.of(0);

  public static final Measure<Angle> MIN_ANGLE = Radians.of(0);
  public static final Measure<Angle> MAX_ANGLE = Radians.of(20);
  public static final double GEARING = 10;

  public static final double kP = 0;
  public static final double kI = 0;
  public static final double kD = 0;

  public static final double kS = 0.0823;
  public static final double kG = 1.23;
  public static final double kV = 0;
  public static final double kA = 0;

  public static final double kIntakeConeStatorCurrentLimit = 180.0; // 200.0
  public static final double kIntakeConeStatorHoldCurrent = 10.0; // 30.0
  public static final double kIntakeConeVelocityThreshold = 200.0;

  public static final double kIntakeCubeStatorCurrentLimit = 15.0;
  public static final double kIntakeCubeWeakStatorCurrentLimit = 6.0; // 10.0
  public static final double kIntakeCubeVelocityThreshold = 2100.0; // 1500
}
