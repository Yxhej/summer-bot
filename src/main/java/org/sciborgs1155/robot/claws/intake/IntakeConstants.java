package org.sciborgs1155.robot.claws.intake;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Mass;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;

// https://github.com/MrThru/2023ChargedUp/blob/main/src/main/java/com/team1323/frc2023/Constants.java#L169
public class IntakeConstants {
  public static final double SPIT_SPEED = -0.5;
  public static final double INTAKE_SPEED = 0.94;

  public static final Measure<Angle> TOLERANCE = Degrees.of(2);

  public static final Measure<Mass> WEIGHT = Kilograms.of(0);
  public static final Measure<Distance> LENGTH = Meters.of(2);

  public static final Measure<Angle> POSITION_FACTOR = Degrees.of(41.66666667 * 2048.0 / 360.0);

  public static final Measure<Velocity<Angle>> MAX_VELOCITY = RadiansPerSecond.of(0);
  public static final Measure<Velocity<Velocity<Angle>>> MAX_ACCELERATION =
      RadiansPerSecond.per(Seconds).of(0);

  public static final Measure<Angle> STARTING_ANGLE = Degrees.of(0);

  public static final Measure<Angle> MIN_ANGLE = Degrees.of(-40);
  public static final Measure<Angle> MAX_ANGLE = Degrees.of(110);

  public static final double GEARING = 10;
  public static final double MOI = 1;

  public static final double kP = 1;
  public static final double kI = 0;
  public static final double kD = 0.05;

  public static final double kS = 0.0823;
  public static final double kG = 1.23;
  public static final double kV = 1.0; // 1
  public static final double kA = 4.0; // 4? tf?

  public static final Measure<Angle> INTAKE_ANGLE = Degrees.of(-3.75);
  public static final Measure<Angle> FLOOR_ANGLE = Degrees.of(-38.75);

  public static final double SUPPLY_CURRENT_LIMIT = 25.0;
}
