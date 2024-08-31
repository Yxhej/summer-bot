package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Meters;
import static org.sciborgs1155.robot.Ports.VerticalElevator.*;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.START_POSITION;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical.*;

import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Optional;
import monologue.Annotations.Log;
import monologue.Logged;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical;
import org.sciborgs1155.robot.elevators.SimElevator.ElevatorType;

public class VerticalElevator extends SubsystemBase implements Logged {
  public enum State {
    DEFAULT(START_POSITION),
    CONE_INTAKE(CONE_INTAKE_EXTENSION),
    CUBE_INTAKE(CUBE_INTAKE_EXTENSION),
    LOW(LOW_EXTENSION),
    CUBE_MID(CUBE_MID_EXTENSION),
    CUBE_HIGH(CUBE_HIGH_EXTENSION),
    CONE_MID(CONE_MID_EXTENSION),
    CONE_HIGH(CONE_HIGH_EXTENSION),
    STOW(STOW_EXTENSION),
    CONE_FLIP(CONE_FLIP_EXTENSION);

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
    setDefaultCommand(updateSetpoint(State.STOW.extension.in(Meters)));
  }

  public State state() {
    return state;
  }

  @Log.NT
  public double height() {
    return hardware.position();
  }

  public Command moveTo(State state) {
    return moveTo(state.extension.in(Meters)).finallyDo(() -> this.state = state);
  }

  public Command moveTo(double position) {
    return updateSetpoint(position).asProxy();
  }

  private Command updateSetpoint(double position) {
    return run(() -> hardware.updateSetpoint(position))
        .until(hardware::atSetpoint)
        .finallyDo(() -> hardware.setVoltage(0));
  }

  @Log.NT
  public boolean atSetpoint() {
    return hardware.atSetpoint();
  }

  @Override
  public void periodic() {
    log("command", Optional.ofNullable(getCurrentCommand()).map(Command::getName).orElse("none"));
  }
}
