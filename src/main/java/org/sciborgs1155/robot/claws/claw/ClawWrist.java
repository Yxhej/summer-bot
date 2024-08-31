package org.sciborgs1155.robot.claws.claw;

import static edu.wpi.first.units.Units.Radians;
import static org.sciborgs1155.robot.claws.claw.ClawConstants.STARTING_ANGLE;
import static org.sciborgs1155.robot.claws.claw.ClawConstants.Scoring.*;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import monologue.Logged;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.claws.SimWrist;
import org.sciborgs1155.robot.claws.WristIO;
import org.sciborgs1155.robot.claws.WristIO.WristType;

// Wrist in code
public class ClawWrist extends SubsystemBase implements Logged {
  public enum State {
    DEFAULT(STARTING_ANGLE),
    CONE_INTAKE(CONE_INTAKE_ANGLE),
    CUBE_INTAKE(CUBE_INTAKE_ANGLE),
    CUBE_NODE(CUBE_NODE_ANGLE),
    CONE_MID(CONE_MID_NODE),
    CONE_HIGH(CONE_HIGH_NODE),
    CONE_STOW(CONE_STOW_ANGLE),
    CUBE_STOW(CUBE_STOW_ANGLE),
    CONE_FLIP(CONE_FLIP_ANGLE);

    public Measure<Angle> angle;

    private State(Measure<Angle> angle) {
      this.angle = angle;
    }
  }

  private final WristIO hardware;
  private State state = State.DEFAULT;

  public static ClawWrist create() {
    return Robot.isReal()
        ? new ClawWrist(new RealWrist())
        : new ClawWrist(new SimWrist(WristType.SCORER));
  }

  public ClawWrist(WristIO wrist) {
    this.hardware = wrist;
  }

  public State state() {
    return state;
  }

  @Log.NT
  public double angle() {
    return hardware.position();
  }

  public Command moveTo(State state) {
    this.state = state;
    return moveTo(state.angle.in(Radians));
  }

  private Command moveTo(double position) {
    return run(() -> hardware.updateSetpoint(position))
        .until(hardware::atGoal)
        .finallyDo(() -> hardware.setVoltage(0));
  }

  @Log.NT
  public boolean atGoal() {
    return hardware.atGoal();
  }
}
