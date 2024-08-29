package org.sciborgs1155.robot.commands;

import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.Scoring.*;

import org.sciborgs1155.robot.Positions;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import org.sciborgs1155.robot.claws.scorer.ClawRollers;
import org.sciborgs1155.robot.claws.scorer.ClawWrist;
import org.sciborgs1155.robot.elevators.HorizontalElevator;
import org.sciborgs1155.robot.elevators.VerticalElevator;
import org.sciborgs1155.robot.shoulder.Shoulder;

public class Scoring {
  public enum Gamepiece {
    CUBE,
    CONE;
  }

  public enum Position {
    TOP,
    MIDDLE,
    GROUND,
    SUBSTATION;
  }

  private static final double kVerticalHeightForTopBarClearance = 0.5;
  private static final double kVerticalHeightForBumperClearance = 16.0;
  private static final double kVerticalHeightForStow = 4.25;
  private static final double kShoulderAngleForHorizontalRetraction = -60.0;
  private static final double kShoulderAngleForHorizontalExtension = 20.0;
  private static final double kShoulderAngleForEscapingElevator = 135.0;
  private static final double kHorizontalExtensionForMinShoulderReach = 0.25;
  private static final double kHorizontalExtensionForUprightShoulder = 6.0;
  private static final double kHorizontalExtensionForGridClearance = 15.0;

  private final VerticalElevator vertical;
  private final HorizontalElevator horizontal;
  private final Shoulder shoulder;
  private final ClawWrist wrist;
  private final ClawRollers rollers;

  public Scoring(
      VerticalElevator vertical,
      HorizontalElevator horizontal,
      Shoulder shoulder,
      ClawWrist wrist,
      ClawRollers rollers) {
    this.vertical = vertical;
    this.horizontal = horizontal;
    this.shoulder = shoulder;
    this.wrist = wrist;
    this.rollers = rollers;
  }

  public Command intakeCone() {
    return shoulder
        .moveTo(Shoulder.State.CONE_INTAKE)
        .alongWith(wrist.moveTo(ClawWrist.State.CONE_INTAKE))
        .alongWith(rollers.runRollers(CONE_SPEED));
  }

  public Command intakeCube() {
    return shoulder
        .moveTo(Shoulder.State.CUBE_INTAKE)
        .alongWith(wrist.moveTo(ClawWrist.State.CUBE_INTAKE))
        .alongWith(rollers.runRollers(CUBE_SPEED));
  }

  public Command setIntakeState(Position position, Gamepiece gamepiece) {
    if (gamepiece == Gamepiece.CONE) {
      return switch (position) {
          case GROUND -> 
          case SUBSTATION -> 
        default -> Commands.none();
      };
    } else {
      return switch (position) {
          case GROUND ->
          case SUBSTATION ->
        default -> Commands.none();
      };
    }
  }

  public Command setScoringState(Position position, Gamepiece gamepiece) {
    if (gamepiece == Gamepiece.CONE) {
      return switch (position) {
          case GROUND ->
          case MIDDLE ->
          case TOP ->
        default -> Commands.none();
      };
    } else {
      return switch (position) {
          case GROUND ->
          case MIDDLE ->
          case TOP ->
        default -> Commands.none();
      };
    }
  }
}
