package org.sciborgs1155.robot.subsystems.tunnel;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import monologue.Annotations.Log;
import org.sciborgs1155.robot.Robot;

// pretend that these are three
public class Tunnel extends SubsystemBase {
  public enum CubeStored {
    ZERO,
    ONE,
    TWO;
  }

  private final TunnelIO hardware;

  public static Tunnel create() {
    return Robot.isReal() ? new Tunnel(new RealTunnel()) : new Tunnel(new NoTunnel());
  }

  public Tunnel(TunnelIO hardware) {
    this.hardware = hardware;
  }

  public Command runConveyor(double percent) {
    return run(() -> hardware.setConveyorSpeed(percent))
        .finallyDo(() -> hardware.setConveyorSpeed(0))
        .asProxy();
  }

  public Command runExit(double percent) {
    return run(() -> hardware.setExitSpeed(percent))
        .finallyDo(() -> hardware.setExitSpeed(0))
        .asProxy();
  }

  public Command runEntrance(double percent) {
    return run(() -> hardware.setEntranceSpeed(percent))
        .finallyDo(() -> hardware.setEntranceSpeed(0))
        .asProxy();
  }

  // stupid jank workaround that sucks but Oh well!
  public Command feed(double speed) {
    return runEntrance(speed)
        .until(() -> !atEntrance())
        .alongWith(runConveyor(speed).onlyIf(this::atEntrance));
  }

  @Log.NT
  public boolean atEntrance() {
    return hardware.atFront();
  }

  @Log.NT
  public boolean atExit() {
    return hardware.atExit();
  }
}
