package org.sciborgs1155.robot.tunnel;

import static org.sciborgs1155.robot.Ports.Tunnel.*;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;

public class RealTunnel implements TunnelIO {
  private final TalonFX front;
  private final TalonFX conveyor;
  private final TalonFX roller;

  private final DigitalInput entrance;
  private final DigitalInput exit;

  public RealTunnel() {
    front = new TalonFX(TUNNEL_FRONT);
    conveyor = new TalonFX(TUNNEL_CONVEYOR);
    roller = new TalonFX(TUNNEL_ROLLER);

    entrance = new DigitalInput(ENTRANCE_BEAMBREAK);
    exit = new DigitalInput(EXIT_BEAMBREAK);
  }

  @Override
  public void setEntranceSpeed(double speed) {
    front.set(speed);
  }

  @Override
  public void setRollerSpeed(double speed) {
    roller.set(speed);
  }

  @Override
  public void setConveyorSpeed(double speed) {
    conveyor.set(speed);
  }

  @Override
  public boolean atFront() {
    return !entrance.get();
  }

  @Override
  public boolean atExit() {
    return !exit.get();
  }
}
