package org.sciborgs1155.robot.commands;

import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.Scoring.*;

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

  // public Command score(MechanismStates state) {
  //   return vertical.moveTo(state.vertical())
  // }
}
