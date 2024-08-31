package org.sciborgs1155.robot.claws.intake;

import static edu.wpi.first.units.Units.Radians;
import static org.sciborgs1155.robot.claws.intake.IntakeConstants.INTAKE_ANGLE;
import static org.sciborgs1155.robot.claws.intake.IntakeConstants.STARTING_ANGLE;

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

public class IntakeWrist extends SubsystemBase implements Logged {
  public enum Position {
    UP(STARTING_ANGLE),
    INTAKE(INTAKE_ANGLE);

    public Measure<Angle> angle;

    private Position(Measure<Angle> angle) {
      this.angle = angle;
    }
  }

  private final WristIO hardware;
  private Position state = Position.UP;

  public static IntakeWrist create() {
    return Robot.isReal()
        ? new IntakeWrist(new RealWrist())
        : new IntakeWrist(new SimWrist(WristType.INTAKE));
  }

  public IntakeWrist(WristIO wrist) {
    this.hardware = wrist;
  }

  public Position state() {
    return state;
  }

  @Log.NT
  public double angle() {
    return hardware.position();
  }

  public Command moveTo(Position position) {
    state = position;
    return moveTo(position.angle.in(Radians));
  }

  private Command moveTo(double position) {
    return run(() -> hardware.updateSetpoint(position))
        .until(hardware::atSetpoint)
        .finallyDo(() -> hardware.setVoltage(0));
  }

  @Log.NT
  public boolean atGoal() {
    return hardware.atSetpoint();
  }
}
