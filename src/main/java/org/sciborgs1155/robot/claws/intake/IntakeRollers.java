package org.sciborgs1155.robot.claws.intake;

import static org.sciborgs1155.robot.Ports.CubeIntake.*;
import static org.sciborgs1155.robot.claws.intake.IntakeConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.claws.NoRoller;
import org.sciborgs1155.robot.claws.RealRoller;
import org.sciborgs1155.robot.claws.RollerIO;
import org.sciborgs1155.robot.claws.RollerIO.RollerType;

public class IntakeRollers extends SubsystemBase {
  private final RollerIO hardware;

  public static IntakeRollers create() {
    return Robot.isReal()
        ? new IntakeRollers(new RealRoller(RollerType.INTAKE))
        : new IntakeRollers(new NoRoller());
  }

  public IntakeRollers(RollerIO hardware) {
    this.hardware = hardware;
  }

  public Command runRollers(double speed) {
    return run(() -> hardware.setSpeed(speed)).finallyDo(() -> hardware.setSpeed(0));
  }

  public Command spit() {
    return runRollers(SPIT_SPEED);
  }

  public Command intake() {
    return runRollers(INTAKE_SPEED);
  }
}
