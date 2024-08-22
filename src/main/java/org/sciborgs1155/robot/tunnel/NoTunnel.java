package org.sciborgs1155.robot.tunnel;

public class NoTunnel implements TunnelIO {

  @Override
  public void setEntranceSpeed(double speed) {}

  @Override
  public void setRollerSpeed(double speed) {}

  @Override
  public void setConveyorSpeed(double speed) {}

  @Override
  public boolean atFront() {
    return true;
  }

  @Override
  public boolean atExit() {
    return true;
  }
}
