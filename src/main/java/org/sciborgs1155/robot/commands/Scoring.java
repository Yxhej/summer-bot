package org.sciborgs1155.robot.commands;

import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.CONE_SPEED;
import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.CUBE_SPEED;

import edu.wpi.first.wpilibj2.command.Command;
import org.sciborgs1155.robot.claws.scorer.ClawRollers;
import org.sciborgs1155.robot.claws.scorer.ClawWrist;
import org.sciborgs1155.robot.elevators.HorizontalElevator;
import org.sciborgs1155.robot.elevators.VerticalElevator;
import org.sciborgs1155.robot.shoulder.Shoulder;

public class Scoring {
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
        .moveTo(Shoulder.Position.CONE_INTAKE)
        .alongWith(wrist.moveTo(ClawWrist.Position.CONE_INTAKE))
        .alongWith(rollers.runRollers(CONE_SPEED));
  }

  public Command intakeCube() {
    return shoulder
        .moveTo(Shoulder.Position.CUBE_INTAKE)
        .alongWith(wrist.moveTo(ClawWrist.Position.CUBE_INTAKE))
        .alongWith(rollers.runRollers(CUBE_SPEED));
  }

  // public Command score(MechanismStates state) {
  //   return vertical.moveTo(state.vertical())
  // }
}
