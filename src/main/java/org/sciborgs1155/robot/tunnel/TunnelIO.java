package org.sciborgs1155.robot.tunnel;

public interface TunnelIO {
  void setEntranceSpeed(double speed);

  void setRollerSpeed(double speed);

  void setConveyorSpeed(double speed);

  boolean atFront();

  boolean atExit();

  default void setAllSpeeds(double speed) {
    setEntranceSpeed(speed);
    setRollerSpeed(speed);
    setConveyorSpeed(speed);
  }
}
