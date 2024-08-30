package org.sciborgs1155.robot.commands;

import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.Scoring.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import org.sciborgs1155.robot.Positions.MechanismStates;
import org.sciborgs1155.robot.Positions.Nodes;
import org.sciborgs1155.robot.Positions.Pickup;
import org.sciborgs1155.robot.Positions.Stow;
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
    TUNNEL,
    SUBSTATION;
  }

  private final VerticalElevator vertical;
  private final HorizontalElevator horizontal;
  private final Shoulder shoulder;
  private final ClawWrist wrist;
  private final ClawRollers rollers;
  private Position currentPosition = Position.GROUND;

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

  private Command intake(MechanismStates goalStates) {
    return shoulder
        .moveTo(goalStates.shoulderAngle())
        .alongWith(wrist.moveTo(goalStates.wristAngle()))
        .alongWith(rollers.runRollers(CONE_SPEED));
  }

  // oh god what have i created TODO actually check if the below makes sense
  private Command lift(MechanismStates goalStates) {
    return switch (currentPosition) {
      case TOP, MIDDLE, SUBSTATION ->
          vertical
              .moveTo(goalStates.vertical())
              .unless(goalStates::canWristCollideWithRotation)
              .alongWith(shoulder.moveTo(goalStates.shoulderAngle()))
              .unless(goalStates::canWristCollideWithShoulderRotation)
              .alongWith(
                  horizontal
                      .moveTo(goalStates.horizontal())
                      .unless(goalStates::canShoulderCollideWithElevator))
              .alongWith(wrist.moveTo(goalStates.wristAngle()))
              .unless(goalStates::canWristCollideWithRotation);
      case TUNNEL, GROUND ->
          wrist
              .moveTo(goalStates.wristAngle())
              .alongWith(
                  shoulder
                      .moveTo(goalStates.shoulderAngle())
                      .unless(goalStates::canShoulderCollideWithElevator))
              .alongWith(
                  horizontal
                      .moveTo(goalStates.horizontal())
                      .unless(goalStates::canShoulderCollideWithElevator))
              .alongWith(vertical.moveTo(goalStates.vertical()));
    };
  }

  private Command score(MechanismStates goalStates) {
    return lift(goalStates).andThen(rollers.eject());
  }

  private Command stow(MechanismStates goalStates) {
    return lift(goalStates).andThen(rollers.stow());
  }

  // code duplication my beloved
  public Command setIntakeState(Position goalPosition, Gamepiece gamepiece) {
    if (gamepiece == Gamepiece.CONE) {
      return switch (goalPosition) {
        case GROUND -> intake(Pickup.GROUND_CONE).finallyDo(() -> currentPosition = goalPosition);
        case SUBSTATION ->
            intake(Pickup.GROUND_CONE).finallyDo(() -> currentPosition = goalPosition);
        default -> warn("Can only intake from ground & substation!");
      };
    } else {
      return switch (goalPosition) {
        case GROUND -> intake(Pickup.TUNNEL_CUBE).finallyDo(() -> currentPosition = goalPosition);
        case TUNNEL -> intake(Pickup.TUNNEL_CUBE).finallyDo(() -> currentPosition = goalPosition);
        case SUBSTATION ->
            intake(Pickup.TUNNEL_CUBE).finallyDo(() -> currentPosition = goalPosition);
        default -> warn("Cannot intake from nodes!");
      };
    }
  }

  public Command setScoringState(Position goalPosition, Gamepiece gamepiece) {
    currentPosition = goalPosition;
    if (gamepiece == Gamepiece.CONE) {
      return switch (goalPosition) {
        case GROUND -> score(Nodes.GROUND_CONE).finallyDo(() -> currentPosition = goalPosition);
        case MIDDLE -> score(Nodes.MID_CONE).finallyDo(() -> currentPosition = goalPosition);
        case TOP -> score(Nodes.HIGH_CONE).finallyDo(() -> currentPosition = goalPosition);
        default -> warn("Can only score on nodes!");
      };
    } else {
      return switch (goalPosition) {
        case GROUND -> score(Nodes.GROUND_CUBE).finallyDo(() -> currentPosition = goalPosition);
        case MIDDLE -> score(Nodes.MID_CUBE).finallyDo(() -> currentPosition = goalPosition);
        case TOP -> score(Nodes.HIGH_CUBE).finallyDo(() -> currentPosition = goalPosition);
        default -> warn("Can only score on nodes!");
      };
    }
  }

  /**
   * Return a command that moves the superstructure in a safe way dependent on the position it is
   * exiting.
   *
   * <p>This also acts as the superstructure's default state.
   *
   * @param previousPosition The position being exited.
   * @param gamepiece The gamepiece being stowed.
   * @return The stowing command.
   */
  // TODO fix: inherently flawed but middle is the safest option
  public Command setStowState(Position previousPosition, Gamepiece gamepiece) {
    if (gamepiece == Gamepiece.CONE) {
      return switch (previousPosition) {
        case TOP, MIDDLE, GROUND, TUNNEL ->
            stow(Stow.CONE_STOW).finallyDo(() -> currentPosition = Position.MIDDLE);
        case SUBSTATION ->
            stow(Stow.CONE_COMMUNITY_STOW).finallyDo(() -> currentPosition = Position.MIDDLE);
      };
    }
    return stow(Stow.CUBE_STOW).finallyDo(() -> currentPosition = Position.MIDDLE);
  }

  private Command warn(String message) {
    return Commands.parallel(
        Commands.print("Operation not supported!"),
        Commands.runOnce(() -> DriverStation.reportWarning(message, false)));
  }
}
