package org.sciborgs1155.robot.shoulder;

import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.Robot;

public class Shoulder extends SubsystemBase {
  private final ShoulderIO hardware;

  public static Shoulder create() {
    return Robot.isReal() ? new Shoulder(new RealShoulder()) : new Shoulder(new SimShoulder());
  }

  public static Shoulder none() {
    return new Shoulder(new NoShoulder());
  }

  public Shoulder(ShoulderIO hardware) {
    this.hardware = hardware;
  }

  public Command runShoulder(Measure<Angle> angle) {
    return runShoulder(angle.in(Radians));
  }

  public Command runShoulder(double angle) {
    return run(() -> hardware.updateSetpoint(angle)).finallyDo(() -> hardware.setVoltage(0));
  }

  @Log.NT
  public boolean atGoal() {
    return hardware.atGoal();
  }
}
