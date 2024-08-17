package org.sciborgs1155.robot.elevators;

import static org.sciborgs1155.robot.Ports.VerticalElevator.*;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical;

public class VerticalElevator extends SubsystemBase {
  private final ElevatorIO hardware;

  public static HorizontalElevator create() {
    return Robot.isReal()
        ? new HorizontalElevator(new RealElevator(MOTOR, Vertical.CONFIG))
        : new HorizontalElevator(new SimElevator());
  }

  public VerticalElevator(ElevatorIO hardware) {
    this.hardware = hardware;
  }

  public Command moveTo(double position) {
    return run(() -> hardware.updateSetpoint(position))
        .until(hardware::atSetpoint)
        .finallyDo(() -> hardware.setVoltage(0));
  }

  @Log.NT
  public boolean atSetpoint() {
    return hardware.atSetpoint();
  }
}
