package org.sciborgs1155.robot.subsystems.tunnel;

public interface TunnelIO {
  void setEntranceSpeed(double speed);

  void setExitSpeed(double speed);

  void setConveyorSpeed(double speed);

  boolean atFront();

  boolean atExit();

  default void setAllSpeeds(double speed) {
    setEntranceSpeed(speed);
    setExitSpeed(speed);
    setConveyorSpeed(speed);
  }
}
