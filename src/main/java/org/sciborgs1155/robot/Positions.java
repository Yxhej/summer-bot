package org.sciborgs1155.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import org.sciborgs1155.robot.claws.scorer.ClawWrist;
import org.sciborgs1155.robot.claws.scorer.ScorerConstants;
import org.sciborgs1155.robot.elevators.HorizontalElevator;
import org.sciborgs1155.robot.elevators.VerticalElevator;
import org.sciborgs1155.robot.shoulder.Shoulder;
import org.sciborgs1155.robot.shoulder.ShoulderConstants;

/**
 * @see
 *     https://github.com/MrThru/2023ChargedUp/blob/main/src/main/java/com/team1323/frc2023/subsystems/superstructure/MechanismStates.java#L3
 */
public class Positions {
  // The shoulder joint's position in space when both elevators are fully retracted
  private static final Translation2d DEFAULT_SHOULDER_JOINT = new Translation2d(12.75, 25.126);

  // The length from the shoulder joint to the wrist joint
  private static final Measure<Distance> SHOULDER_TO_WRIST = Inches.of(17.5);

  // The position in space of the center of the bottom of the elevator's top bar (the bar is a 2x1)
  private static final Translation2d ELEVATOR_MAX_VERTICAL = new Translation2d(6.25, 46.876);

  private static final Translation2d BUMPER_CORNERS =
      new Translation2d(Constants.ROBOT_LENGTH.divide(2).in(Meters), 6.75);

  public record MechanismStates(
      VerticalElevator.State vertical,
      HorizontalElevator.State horizontal,
      Shoulder.State shoulderAngle,
      ClawWrist.State wristAngle) {

    /**
     * Calculates the distance from the default position of the shoulder joint to its current
     * position based on elevator extension.
     *
     * @return The translation between the original and current positions of the shoulder's joint.
     */
    public Translation2d shoulderJointPosition() {
      return DEFAULT_SHOULDER_JOINT.plus(
          new Translation2d(horizontal.extension, vertical.extension));
    }

    /**
     * Calculates the distance from the default position of the wrist joint to its current position
     * based on shoulder angle and elevator extension.
     *
     * @return The translation between the original and current positions of the wrist's joint.
     */
    public Translation2d wristJointPosition() {
      Pose2d shoulderJointPose =
          new Pose2d(shoulderJointPosition(), new Rotation2d(shoulderAngle.angle));
      Pose2d wristJointPose =
          shoulderJointPose.plus(
              new Transform2d(
                  new Translation2d(SHOULDER_TO_WRIST, Meters.zero()), new Rotation2d()));

      return wristJointPose.getTranslation();
    }

    /**
     * Calculates the distance from the default position of the wrist's tip to its current position
     * based on wrist angle, shoulder angle, and elevator extension.
     *
     * @return The translation between the original and current positions of the wrist's ti[].
     */
    public Translation2d wristTipPosition() {
      Rotation2d wristDirection =
          new Rotation2d(wristAngle.angle).rotateBy(new Rotation2d(shoulderAngle.angle));
      Pose2d wristJointPose = new Pose2d(wristJointPosition(), wristDirection);
      Pose2d wristTipPose =
          wristJointPose.transformBy(
              new Transform2d(
                  new Translation2d(ScorerConstants.LENGTH, Meters.zero()), new Rotation2d()));
      return wristTipPose.getTranslation();
    }

    /**
     * @return Whether or not the shoulder would collide with the top of the elevator at any point
     *     throughout its travel, given the position of the shoulder joint.
     */
    public boolean canShoulderCollideWithElevator() {
      final double clearanceInches = 2.0;

      return shoulderJointPosition().getDistance(ELEVATOR_MAX_VERTICAL)
          < (ShoulderConstants.LENGTH.in(Meters) + clearanceInches);
    }

    /**
     * @return The angle at which the shoulder would be most likely to collide with the elevator, if
     *     the shoulder were to move from its current position.
     */
    public Rotation2d getElevatorCollisionAngle() {
      Transform2d shoulderJointToElevatorBar =
          new Transform2d(ELEVATOR_MAX_VERTICAL, new Rotation2d())
              .plus(new Transform2d(shoulderJointPosition(), new Rotation2d()).inverse());

      return shoulderJointToElevatorBar.getRotation();
    }

    public boolean isShoulderJointHigherThanElevatorBar() {
      return shoulderJointPosition().getY() >= ELEVATOR_MAX_VERTICAL.getY();
    }

    /**
     * @return Whether or not the shoulder or wrist intersect the bumper or ground in the current
     *     position.
     */
    public boolean collidesWithBumperOrGround() {
      Translation2d wristJointPosition = wristJointPosition();
      boolean shoulderCollides =
          intersectsBumper(wristJointPosition) || intersectsGround(wristJointPosition);
      Translation2d wristTipPosition = wristTipPosition();
      boolean wristCollides =
          intersectsBumper(wristTipPosition) || intersectsGround(wristTipPosition);

      return shoulderCollides || wristCollides;
    }

    private boolean intersectsBumper(Translation2d point) {
      return point.getX() <= BUMPER_CORNERS.getX() && point.getY() <= BUMPER_CORNERS.getY();
    }

    private boolean intersectsGround(Translation2d point) {
      return point.getY() <= 0.0;
    }

    /**
     * @return Whether or not the wrist can collide with the bumper at any point throughout the
     *     shoulder's travel.
     */
    public boolean canWristCollideWithShoulderRotation() {
      Translation2d shoulderJointPosition = shoulderJointPosition();
      double shoulderJointToWristTip = shoulderJointPosition.getDistance(wristTipPosition());
      double shoulderJointToBumper = shoulderJointPosition.getDistance(BUMPER_CORNERS);

      return shoulderJointToWristTip >= shoulderJointToBumper;
    }

    /**
     * @return The shoulder angle at which the wrist is most likely to collide with the bumper.
     */
    public Rotation2d getShoulderBumperCollisionAngle() {
      Translation2d shoulderJointPosition = shoulderJointPosition();
      double shoulderJointToWristTipLength = shoulderJointPosition.getDistance(wristTipPosition());
      Rotation2d shoulderJointToBumperDirection =
          direction(getTransform(shoulderJointPosition, BUMPER_CORNERS));

      Rotation2d shoulderAngleDelta =
          lawOfCosines(
              ScorerConstants.LENGTH.in(Meters),
              SHOULDER_TO_WRIST.in(Meters),
              shoulderJointToWristTipLength);
      if (wristAngle.angle.in(Degrees) > 0.0) {
        shoulderAngleDelta = shoulderAngleDelta.unaryMinus();
      }
      return shoulderJointToBumperDirection.rotateBy(shoulderAngleDelta);
    }

    /**
     * @return Whether or not the wrist will collide with the bumper or ground at any point
     *     throughout its own travel.
     */
    public boolean canWristCollideWithRotation() {
      return canWristCollideWithBumper() || canWristCollideWithGround();
    }

    private boolean canWristCollideWithBumper() {
      return shoulderJointPosition().getDistance(BUMPER_CORNERS)
          < ScorerConstants.LENGTH.in(Meters);
    }

    private boolean canWristCollideWithGround() {
      return shoulderJointPosition().getY() < ScorerConstants.LENGTH.in(Meters);
    }

    public Rotation2d getWristCollisionAngle() {
      if (canWristCollideWithGround()) {
        // Return the wrist angle that would result in the wrist pointing straight down
        return Rotation2d.fromDegrees(-90)
            .rotateBy(new Rotation2d(shoulderAngle.angle).unaryMinus());
      }

      return direction(getTransform(shoulderJointPosition(), BUMPER_CORNERS))
          .rotateBy(new Rotation2d(shoulderAngle.angle).unaryMinus());
    }

    private Translation2d getTransform(Translation2d start, Translation2d end) {
      return new Translation2d(end.getX() - start.getX(), end.getY() - start.getY());
    }

    private Rotation2d direction(Translation2d translation) {
      return Rotation2d.fromRadians(Math.atan2(translation.getY(), translation.getX()));
    }

    private Rotation2d lawOfCosines(
        double oppositeSideLength, double adjacentSideLength1, double adjacentSideLength2) {
      double numerator =
          adjacentSideLength1 * adjacentSideLength1
              + adjacentSideLength2 * adjacentSideLength2
              - oppositeSideLength * oppositeSideLength;
      double denominator = 2 * adjacentSideLength1 * adjacentSideLength2;
      double radians = Math.acos(numerator / denominator);

      return Rotation2d.fromRadians(radians);
    }

    private double shoulderDegrees() {
      return shoulderAngle.angle.in(Degrees);
    }

    private double wristDegrees() {
      return wristAngle.angle.in(Degrees);
    }

    private double horizontalExtensionMeters() {
      return horizontal.extension.in(Meters);
    }

    private double verticalExtensionMeters() {
      return vertical.extension.in(Meters);
    }
  }

  public static class Scoring {
    public static final MechanismStates GROUND_CUBE =
        new MechanismStates(
            VerticalElevator.State.LOW,
            HorizontalElevator.State.LOW,
            Shoulder.State.DEFAULT,
            ClawWrist.State.DEFAULT);
    public static final MechanismStates GROUND_CONE =
        new MechanismStates(
            VerticalElevator.State.LOW,
            HorizontalElevator.State.LOW,
            Shoulder.State.DEFAULT,
            ClawWrist.State.DEFAULT);

    public static final MechanismStates MID_CUBE =
        new MechanismStates(
            VerticalElevator.State.CUBE_MID,
            HorizontalElevator.State.CUBE_MID,
            Shoulder.State.CUBE_NODE,
            ClawWrist.State.CUBE_NODE);
    public static final MechanismStates MID_CONE =
        new MechanismStates(
            VerticalElevator.State.CONE_MID,
            HorizontalElevator.State.CONE_MID,
            Shoulder.State.CONE_MID,
            ClawWrist.State.CONE_MID);

    public static final MechanismStates HIGH_CUBE =
        new MechanismStates(
            VerticalElevator.State.CUBE_HIGH,
            HorizontalElevator.State.CUBE_HIGH,
            Shoulder.State.CUBE_NODE,
            ClawWrist.State.CUBE_NODE);
    public static final MechanismStates HIGH_CONE =
        new MechanismStates(
            VerticalElevator.State.CONE_HIGH,
            HorizontalElevator.State.CONE_HIGH,
            Shoulder.State.CONE_HIGH,
            ClawWrist.State.CONE_HIGH);
  }

  public static class Pickup {
    public static final MechanismStates GROUND_CONE =
        new MechanismStates(
            VerticalElevator.State.CONE_INTAKE,
            HorizontalElevator.State.CONE_INTAKE,
            Shoulder.State.CONE_INTAKE,
            ClawWrist.State.CONE_INTAKE);
    public static final MechanismStates TUNNEL_CUBE =
        new MechanismStates(
            VerticalElevator.State.CUBE_INTAKE,
            HorizontalElevator.State.CUBE_INTAKE,
            Shoulder.State.CUBE_INTAKE,
            ClawWrist.State.CUBE_INTAKE);
  }

  public static class Stow {
    public static final MechanismStates CONE_STOW =
        new MechanismStates(
            VerticalElevator.State.STOW,
            HorizontalElevator.State.STOW,
            Shoulder.State.STOW,
            ClawWrist.State.CONE_STOW);
    public static final MechanismStates CUBE_STOW =
        new MechanismStates(
            VerticalElevator.State.STOW,
            HorizontalElevator.State.STOW,
            Shoulder.State.STOW,
            ClawWrist.State.CUBE_STOW);

    public static final MechanismStates CONE_COMMUNITY_STOW =
        new MechanismStates(
            VerticalElevator.State.STOW,
            HorizontalElevator.State.STOW,
            Shoulder.State.COMMUNITY_CONE_HOLD,
            ClawWrist.State.CONE_STOW);

    public static final MechanismStates CONE_PRESCORE =
        new MechanismStates(
            VerticalElevator.State.STOW,
            HorizontalElevator.State.STOW,
            Shoulder.State.CONE_PRESCORE,
            ClawWrist.State.CONE_STOW);

    public static final MechanismStates CONE_FLIP =
        new MechanismStates(
            VerticalElevator.State.CONE_FLIP,
            HorizontalElevator.State.CONE_FLIP,
            Shoulder.State.CONE_FLIP,
            ClawWrist.State.CONE_FLIP);
  }

  // Nearly all actions of the robot should be done parallel UNLESS a safety is violated, from which
  // that specific dangerous command will defer itself until it is safe
  public static boolean elevatorCollision(MechanismStates start, MechanismStates end) {
    // determine if shoulder angle will cause collision when retracting horizontal elevator
    boolean collision = start.canShoulderCollideWithElevator();
    Rotation2d collisionAngle = start.getElevatorCollisionAngle();
    boolean willCollideWithElevator =
        collision
            && inRange(collisionAngle.getDegrees(), end.shoulderDegrees(), start.shoulderDegrees());
    willCollideWithElevator |=
        start.isShoulderJointHigherThanElevatorBar()
            && start.shoulderDegrees() >= ShoulderConstants.Scoring.STOW_ANGLE.in(Degrees);
    return willCollideWithElevator;
  }

  private static boolean rotatingShoulderCollidesWithBumper(
      MechanismStates currentPosition, MechanismStates finalPosition) {
    Rotation2d bumperCollisionAngle = currentPosition.getShoulderBumperCollisionAngle();
    boolean shoulderRotationCollidesWithBumper =
        currentPosition.canWristCollideWithShoulderRotation()
            && inRange(
                bumperCollisionAngle.getDegrees(),
                currentPosition.shoulderDegrees(),
                finalPosition.shoulderDegrees());

    return shoulderRotationCollidesWithBumper;
  }

  private static boolean rotatingWristCausesCollision(
      MechanismStates currentPosition, MechanismStates finalPosition) {
    Rotation2d wristCollisionAngle = currentPosition.getWristCollisionAngle();
    boolean wristRotationCausesCollision =
        currentPosition.canWristCollideWithRotation()
            && inRange(
                wristCollisionAngle.getDegrees(),
                currentPosition.wristDegrees(),
                finalPosition.wristDegrees());

    return wristRotationCausesCollision;
  }

  private static boolean inRange(double value, double rangeLimit1, double rangeLimit2) {
    double min, max;
    if (rangeLimit1 < rangeLimit2) {
      min = rangeLimit1;
      max = rangeLimit2;
    } else {
      min = rangeLimit2;
      max = rangeLimit1;
    }
    return min <= value && value <= max;
  }
}
