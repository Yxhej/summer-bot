package org.sciborgs1155.robot.claws.scorer;

import static edu.wpi.first.units.Units.Radians;
import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.CONE_INTAKE_ANGLE;
import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.CUBE_INTAKE_ANGLE;
import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.SCORING_ANGLE;
import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.STARTING_ANGLE;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.claws.SimWrist;
import org.sciborgs1155.robot.claws.WristIO;
import org.sciborgs1155.robot.claws.WristIO.WristType;

// Wrist in code
public class ClawWrist extends SubsystemBase {
  public enum Position {
    DEFAULT(STARTING_ANGLE),
    CONE_INTAKE(CONE_INTAKE_ANGLE),
    CUBE_INTAKE(CUBE_INTAKE_ANGLE),
    NODE(SCORING_ANGLE);

    public Measure<Angle> angle;

    private Position(Measure<Angle> angle) {
      this.angle = angle;
    }
  }

  private final WristIO hardware;
  private Position state = Position.DEFAULT;

  public static ClawWrist create() {
    return Robot.isReal()
        ? new ClawWrist(new RealWrist())
        : new ClawWrist(new SimWrist(WristType.SCORER));
  }

  public ClawWrist(WristIO wrist) {
    this.hardware = wrist;
  }

  public Position state() {
    return state;
  }

  public Command moveTo(Position position) {
    state = position;
    return moveTo(position.angle.in(Radians));
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
