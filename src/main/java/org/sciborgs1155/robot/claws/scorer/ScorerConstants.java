package org.sciborgs1155.robot.claws.scorer;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;

// https://github.com/MrThru/2023ChargedUp/blob/main/src/main/java/com/team1323/frc2023/Constants.java#L535
public class ScorerConstants {
  public static final double CUBE_SPEED = 0.25;
  public static final double CONE_SPEED = 0.8;

  public static final Measure<Mass> WEIGHT = Kilograms.of(0);
  public static final Measure<Distance> LENGTH = Inches.of(6.5);

  public static final Measure<Velocity<Angle>> MAX_VELOCITY = RadiansPerSecond.of(0);
  public static final Measure<Velocity<Velocity<Angle>>> MAX_ACCELERATION =
      RadiansPerSecond.per(Seconds).of(0);

  public static final Measure<Angle> STARTING_ANGLE = Degrees.of(0);

  public static final Measure<Angle> CONE_INTAKE_ANGLE = Degrees.of(85.26);
  public static final Measure<Angle> CUBE_INTAKE_ANGLE = Degrees.of(117.0);

  public static class Scoring {
    public static final Measure<Angle> CUBE_NODE_ANGLE = Degrees.of(117.0);

    public static final Measure<Angle> CONE_MID_NODE = Degrees.of(-14.4);
    public static final Measure<Angle> CONE_HIGH_NODE = Degrees.of(-1.0);
  }

  public static final Measure<Angle> MIN_ANGLE = Degrees.of(-144.0);
  public static final Measure<Angle> MAX_ANGLE = Degrees.of(134.6);
  public static final Measure<Angle> TOLERANCE = Degrees.of(2);

  public static final double GEARING = 10;
  public static final double MOI = 1;

  public static final double kP = 1;
  public static final double kI = 0;
  public static final double kD = 0.05;

  public static final double kS = 0.0823;
  public static final double kG = 1.23;
  public static final double kV = 0;
  public static final double kA = 0.1;

  public static final double kIntakeConeStatorCurrentLimit = 180.0; // 200.0
  public static final double kIntakeConeStatorHoldCurrent = 10.0; // 30.0
  public static final double kIntakeConeVelocityThreshold = 200.0;

  public static final double kIntakeCubeStatorCurrentLimit = 15.0;
  public static final double kIntakeCubeWeakStatorCurrentLimit = 6.0; // 10.0
  public static final double kIntakeCubeVelocityThreshold = 2100.0; // 1500
}
