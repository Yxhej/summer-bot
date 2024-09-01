package org.sciborgs1155.robot.subsystems.claws.claw;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;

// https://github.com/MrThru/2023ChargedUp/blob/main/src/main/java/com/team1323/frc2023/Constants.java#L535
public class ClawConstants {

  public static final Measure<Mass> MASS = Kilograms.of(1);
  public static final Measure<Distance> LENGTH = Inches.of(6.5);

  public static final Measure<Angle> STARTING_ANGLE = Degrees.of(0);

  public static class Scoring {
    public static final double CUBE_SPEED = 0.25;
    public static final double CONE_SPEED = 0.8;
    public static final double STOW_SPEED = 0.05;

    public static final Measure<Angle> CONE_INTAKE_ANGLE = Degrees.of(85.26);
    public static final Measure<Angle> CUBE_INTAKE_ANGLE = Degrees.of(117.0);
    public static final Measure<Angle> CONE_FLIP_ANGLE = Degrees.of(133.5);

    public static final Measure<Angle> CUBE_NODE_ANGLE = Degrees.of(117.0);

    public static final Measure<Angle> CONE_MID_NODE = Degrees.of(-14.4);
    public static final Measure<Angle> CONE_HIGH_NODE = Degrees.of(-1.0);

    public static final Measure<Angle> CUBE_STOW_ANGLE = Degrees.of(98.0);
    public static final Measure<Angle> CONE_STOW_ANGLE = Degrees.of(95);
  }

  public static final Measure<Angle> MIN_ANGLE = Degrees.of(-144.0);
  public static final Measure<Angle> MAX_ANGLE = Degrees.of(134.6);
  public static final Measure<Angle> TOLERANCE = Radians.of(0.005);

  // Ratio of motor rotations per wrist rotation
  public static final double GEARING = 40.333333;

  public static final double kP = 3;
  public static final double kI = 0;
  public static final double kD = 0;

  public static final double kS = 0.0;
  public static final double kG = 0.04121425;
  public static final double kV = 0.0;
  public static final double kA = 0.0;

  public static final double kIntakeConeStatorCurrentLimit = 180.0; // 200.0
  public static final double kIntakeConeStatorHoldCurrent = 10.0; // 30.0
  public static final double kIntakeConeVelocityThreshold = 200.0;

  public static final double kIntakeCubeStatorCurrentLimit = 15.0;
  public static final double kIntakeCubeWeakStatorCurrentLimit = 6.0; // 10.0
  public static final double kIntakeCubeVelocityThreshold = 2100.0; // 1500
}
