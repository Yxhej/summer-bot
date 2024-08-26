package org.sciborgs1155.robot.shoulder;

import static edu.wpi.first.units.Units.Radians;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.CONE_INTAKE_ANGLE;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.CUBE_INTAKE_ANGLE;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.SCORING_ANGLE;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.STARTING_ANGLE;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.Robot;

public class Shoulder extends SubsystemBase {
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

  private final ShoulderIO hardware;

  public static Shoulder create() {
    return Robot.isReal() ? new Shoulder(new RealShoulder()) : new Shoulder(new SimShoulder());
  }

  public static Shoulder none() {
    return new Shoulder(new NoShoulder());
  }

  public Shoulder(ShoulderIO hardware) {
    this.hardware = hardware;
  }

  public Command moveTo(Position position) {
    return moveTo(position.angle.in(Radians));
  }

  private Command moveTo(double angle) {
    return run(() -> hardware.updateSetpoint(angle)).finallyDo(() -> hardware.setVoltage(0));
  }

  @Log.NT
  public boolean atGoal() {
    return hardware.atGoal();
  }
}
