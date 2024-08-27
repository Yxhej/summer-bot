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
    public static final MechanismStates GROUND_CUBE = new MechanismStates(null, null, null, null);
    public static final MechanismStates GROUND_CONE = new MechanismStates(null, null, null, null);

    public static final MechanismStates MID_CUBE =
        new MechanismStates(
            VerticalElevator.State.CUBE_MID,
            HorizontalElevator.State.CUBE_MID,
            Shoulder.State.CUBE_NODE,
            ClawWrist.State.CUBE_NODE);
    public static final MechanismStates MID_CONE = new MechanismStates(null, null, null, null);

    public static final MechanismStates HIGH_CUBE = new MechanismStates(null, null, null, null);
    public static final MechanismStates HIGH_CONE = new MechanismStates(null, null, null, null);
  }

  public static class Pickup {
    public static final MechanismStates GROUND_CUBE = new MechanismStates(null, null, null, null);
    public static final MechanismStates GROUND_CONE = new MechanismStates(null, null, null, null);

    public static final MechanismStates TUNNEL_CUBE = new MechanismStates(null, null, null, null);

    public static final MechanismStates HIGH_CUBE = new MechanismStates(null, null, null, null);
    public static final MechanismStates HIGH_CONE = new MechanismStates(null, null, null, null);
  }
}
