package org.sciborgs1155.robot;

import static edu.wpi.first.units.Units.Inches;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import org.sciborgs1155.robot.claws.scorer.ClawWrist;
import org.sciborgs1155.robot.elevators.HorizontalElevator;
import org.sciborgs1155.robot.elevators.VerticalElevator;
import org.sciborgs1155.robot.shoulder.Shoulder;

/**
 * This class serves to coordinate movements between the various subsystems (vertical elevator,
 * horizontal elevator, shoulder, and wrist).
 *
 * <p>Each method in this class corresponds to one final position that the superstructure can be
 * asked to reach. Each method analyzes the superstructure's current position and choreographs a
 * series of movements (expressed as a Request) that will get the Superstructure to its desired
 * state in a safe manner. Any choreography should abide by these three main constraints:
 *
 * <p>1. The wrist should not collide with the ground, bumper, or top of the elevator at any point
 * throughout its travel.
 *
 * <p>2. Similarly, the shoulder should not collide with the ground, bumper, or top of the elevator
 * at any point throughout its travel.
 *
 * <p>3. Outward extension of the shoulder should be minimized when it is traveling in the range of
 * [-90deg, 90deg].
 *
 * @see
 *     https://github.com/MrThru/2023ChargedUp/blob/main/src/main/java/com/team1323/frc2023/subsystems/superstructure/SuperstructureCoordinator.java
 */
public class Positions {

  // The shoulder joint's position in space when both elevators are fully retracted
  private static final Translation2d kShoulderJointRetractedPosition =
      new Translation2d(12.75, 25.126);
  // The length from the shoulder joint to the wrist joint
  private static final Measure<Distance> kShoulderJointToWristJointLength = Inches.of(17.5);
  // The position in space of the center of the bottom of the elevator's top bar (the bar is a 2x1)
  private static final Translation2d kElevatorTopBarPosition = new Translation2d(6.25, 46.876);

  public record MechanismStates(
      VerticalElevator.State vertical,
      HorizontalElevator.State horizontal,
      Shoulder.State shoulderAngle,
      ClawWrist.State wristAngle) {}

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
}
