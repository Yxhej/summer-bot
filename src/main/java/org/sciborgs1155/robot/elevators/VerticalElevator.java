package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Meters;
import static org.sciborgs1155.robot.Ports.VerticalElevator.*;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.START_POSITION;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical.*;

import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical;
import org.sciborgs1155.robot.elevators.SimElevator.ElevatorType;

public class VerticalElevator extends SubsystemBase {
  public enum State {
    DEFAULT(START_POSITION),
    LOW(LOW_EXTENSION),
    MID(MID_EXTENSION),
    HIGH(HIGH_EXTENSION);

    public Measure<Distance> extension;

    private State(Measure<Distance> extension) {
      this.extension = extension;
    }
  }

  private final ElevatorIO hardware;
  private State state = State.DEFAULT;

  public static VerticalElevator create() {
    return Robot.isReal()
        ? new VerticalElevator(new RealElevator(MOTOR, Vertical.CONFIG))
        : new VerticalElevator(new SimElevator(ElevatorType.VERTICAL));
  }

  public VerticalElevator(ElevatorIO hardware) {
    this.hardware = hardware;
  }

  public State state() {
    return state;
  }

  public Command moveTo(State state) {
    return moveTo(state.extension.in(Meters));
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
