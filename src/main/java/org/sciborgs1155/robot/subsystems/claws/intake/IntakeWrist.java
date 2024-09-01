package org.sciborgs1155.robot.subsystems.claws.intake;

import static edu.wpi.first.units.Units.Radians;
import static org.sciborgs1155.robot.subsystems.claws.intake.IntakeConstants.FLOOR_ANGLE;
import static org.sciborgs1155.robot.subsystems.claws.intake.IntakeConstants.UP_ANGLE;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import monologue.Logged;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.subsystems.claws.SimWrist;
import org.sciborgs1155.robot.subsystems.claws.WristIO;
import org.sciborgs1155.robot.subsystems.claws.WristIO.WristType;

public class IntakeWrist extends SubsystemBase implements Logged {
  public enum State {
    UP(UP_ANGLE),
    INTAKE(FLOOR_ANGLE);

    public Measure<Angle> angle;

    private State(Measure<Angle> angle) {
      this.angle = angle;
    }
  }

  private final WristIO hardware;
  private State state = State.UP;

  public static IntakeWrist create() {
    return Robot.isReal()
        ? new IntakeWrist(new RealWrist())
        : new IntakeWrist(new SimWrist(WristType.INTAKE));
  }

  public IntakeWrist(WristIO wrist) {
    this.hardware = wrist;
  }

  public State state() {
    return state;
  }

  @Log.NT
  public double angle() {
    return hardware.position();
  }

  public Command moveTo(State position) {
    state = position;
    return moveTo(position.angle.in(Radians));
  }

  private Command moveTo(double position) {
    return run(() -> hardware.updateSetpoint(position));
  }

  @Log.NT
  public boolean atSetpoint() {
    return hardware.atSetpoint();
  }
}
