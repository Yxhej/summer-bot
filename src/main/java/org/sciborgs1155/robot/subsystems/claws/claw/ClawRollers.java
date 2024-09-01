package org.sciborgs1155.robot.subsystems.claws.claw;

import static org.sciborgs1155.robot.subsystems.claws.claw.ClawConstants.Scoring.CONE_SPEED;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.subsystems.claws.NoRoller;
import org.sciborgs1155.robot.subsystems.claws.RealRoller;
import org.sciborgs1155.robot.subsystems.claws.RollerIO;
import org.sciborgs1155.robot.subsystems.claws.RollerIO.RollerType;

// Claw in code
public class ClawRollers extends SubsystemBase {
  private final RollerIO hardware;

  public static ClawRollers create() {
    return Robot.isReal()
        ? new ClawRollers(new RealRoller(RollerType.CLAW))
        : new ClawRollers(new NoRoller());
  }

  public ClawRollers(RollerIO hardware) {
    this.hardware = hardware;
  }

  public Command runRollers(double speed) {
    return run(() -> hardware.setSpeed(speed)).finallyDo(() -> hardware.setSpeed(0));
  }

  public Command eject() {
    return runRollers(-CONE_SPEED);
  }

  public Command stow() {
    return runRollers(-CONE_SPEED);
  }
}
