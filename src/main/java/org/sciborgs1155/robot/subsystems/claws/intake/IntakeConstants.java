package org.sciborgs1155.robot.subsystems.claws.intake;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Kilograms;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;

// https://github.com/MrThru/2023ChargedUp/blob/main/src/main/java/com/team1323/frc2023/Constants.java#L169
public class IntakeConstants {
  public static final double SPIT_SPEED = -0.5;
  public static final double INTAKE_SPEED = 0.94;

  public static final Measure<Angle> TOLERANCE = Degrees.of(2);

  // negligible mass
  public static final Measure<Mass> MASS = Kilograms.of(1.5);
  public static final Measure<Distance> LENGTH = Inches.of(12);

  public static final Measure<Angle> POSITION_FACTOR = Degrees.of(41.66666667 * 2048.0 / 360.0);

  public static final Measure<Angle> UP_ANGLE = Degrees.of(0);

  public static final Measure<Angle> INTAKE_ANGLE = Degrees.of(-3.75);
  public static final Measure<Angle> FLOOR_ANGLE = Degrees.of(-38.75);

  public static final Measure<Angle> MIN_ANGLE = Degrees.of(-40);
  public static final Measure<Angle> MAX_ANGLE = Degrees.of(110);

  // Motor rotations : shoulder rotation
  public static final double GEARING = 41.66666667;

  public static final double kP = 3;
  public static final double kI = 0;
  public static final double kD = 0;

  public static final double kS = 0;
  public static final double kG = 0.11047957;
  public static final double kV = 0; // 1
  public static final double kA = 0; // 4? tf?

  public static final double SUPPLY_CURRENT_LIMIT = 25.0;
}
