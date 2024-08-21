package org.sciborgs1155.robot.claws.intake;

import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.claws.SimWrist;
import org.sciborgs1155.robot.claws.WristIO;
import org.sciborgs1155.robot.claws.WristIO.WristType;

public class IntakeWrist extends SubsystemBase {
  private final WristIO hardware;

  public static IntakeWrist create() {
    return Robot.isReal()
        ? new IntakeWrist(new RealWrist())
        : new IntakeWrist(new SimWrist(WristType.INTAKE));
  }

  public IntakeWrist(WristIO wrist) {
    this.hardware = wrist;
  }

  public Command moveTo(Measure<Angle> position) {
    return moveTo(position.in(Radians));
  }

  public Command moveTo(double position) {
    return run(() -> hardware.updateSetpoint(position))
        .until(hardware::atGoal)
        .finallyDo(() -> hardware.setVoltage(0));
  }

  @Log.NT
  public boolean atGoal() {
    return hardware.atGoal();
  }
}
