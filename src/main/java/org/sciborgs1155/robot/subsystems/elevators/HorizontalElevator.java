package org.sciborgs1155.robot.subsystems.elevators;

import static edu.wpi.first.units.Units.Meters;
import static org.sciborgs1155.robot.Ports.HorizontalElevator.*;
import static org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.Horizontal.*;
import static org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.START_POSITION;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Optional;
import monologue.Annotations.Log;
import monologue.Logged;
import org.sciborgs1155.lib.Utils;
import org.sciborgs1155.robot.Robot;
import org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.Horizontal;
import org.sciborgs1155.robot.subsystems.elevators.SimElevator.ElevatorType;

public class HorizontalElevator extends SubsystemBase implements Logged {
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
  private State state;

  public static HorizontalElevator create() {
    return Robot.isReal()
        ? new HorizontalElevator(new RealElevator(MOTOR, Horizontal.CONFIG))
        : new HorizontalElevator(new SimElevator(ElevatorType.HORIZONTAL));
  }

  public HorizontalElevator(ElevatorIO hardware) {
    this.hardware = hardware;
  }

  public State state() {
    return state;
  }

  @Log.NT
  public double height() {
    return hardware.position();
  }

  public Command moveTo(State state) {
    return moveTo(state.extension.in(Meters));
  }

  public Command moveTo(double position) {
    return run(() -> hardware.updateGoal(position))
        .until(() -> Utils.inRange(height(), position, ElevatorConstants.TOLERANCE.in(Meters)))
        .finallyDo(() -> hardware.setVoltage(0));
  }

  public TrapezoidProfile.State setpoint() {
    return hardware.setpoint();
  }

  @Log.NT
  public boolean atGoal() {
    return hardware.atGoal();
  }

  @Override
  public void periodic() {
    log("command", Optional.ofNullable(getCurrentCommand()).map(Command::getName).orElse("none"));
    log("velocity setpoint", setpoint().velocity);
    log("position setpoint", setpoint().position);
  }
}
