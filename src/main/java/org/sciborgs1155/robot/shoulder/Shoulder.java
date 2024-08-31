package org.sciborgs1155.robot.shoulder;

import static edu.wpi.first.units.Units.Radians;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.STARTING_ANGLE;
import static org.sciborgs1155.robot.shoulder.ShoulderConstants.Scoring.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Optional;
import monologue.Annotations.Log;
import monologue.Logged;
import org.sciborgs1155.robot.Robot;

public class Shoulder extends SubsystemBase implements Logged {
  public enum State {
    DEFAULT(STARTING_ANGLE),
    CONE_INTAKE(CONE_INTAKE_ANGLE),
    CUBE_INTAKE(CUBE_INTAKE_ANGLE),
    CUBE_NODE(CUBE_NODE_ANGLE),
    CONE_MID(CONE_MID_NODE),
    CONE_HIGH(CONE_HIGH_NODE),
    STOW(STOW_ANGLE),
    COMMUNITY_CONE_HOLD(CONE_COMMUNITY_STOW),
    CONE_PRESCORE(CONE_PRESCORE_STOW),
    CONE_FLIP(CONE_FLIP_ANGLE);

    public Measure<Angle> angle;

    private State(Measure<Angle> angle) {
      this.angle = angle;
    }
  }

  private final ShoulderIO hardware;
  private State state;

  public static Shoulder create() {
    return Robot.isReal() ? new Shoulder(new RealShoulder()) : new Shoulder(new SimShoulder());
  }

  public static Shoulder none() {
    return new Shoulder(new NoShoulder());
  }

  public Shoulder(ShoulderIO hardware) {
    this.hardware = hardware;
  }

  public State state() {
    return state;
  }

  @Log.NT
  public Rotation2d angle() {
    return hardware.position();
  }

  public Command moveTo(State state) {
    this.state = state;
    return moveTo(state.angle.in(Radians));
  }

  private Command moveTo(double angle) {
    return run(() -> hardware.updateSetpoint(angle)).asProxy();
  }

  @Log.NT
  public boolean atGoal() {
    return hardware.atGoal();
  }

  public TrapezoidProfile.State setpoint() {
    return hardware.setpoint();
  }

  @Override
  public void periodic() {
    log("command", Optional.ofNullable(getCurrentCommand()).map(Command::getName).orElse("none"));
    log("position setpoint", setpoint().position);
    log("velocity setpoint", setpoint().velocity);
    log("gravity output", ShoulderConstants.kG * Math.cos(setpoint().position));
    log("velocity output", ShoulderConstants.kV * setpoint().velocity);
  }
}
