package org.sciborgs1155.robot.subsystems.claws;

import static org.sciborgs1155.robot.Ports.CubeIntake.INTAKE_ROLLER;
import static org.sciborgs1155.robot.Ports.Scorer.CLAW_ROLLER;

public interface RollerIO {
  public enum RollerType {
    INTAKE(INTAKE_ROLLER),
    CLAW(CLAW_ROLLER);

    public int port;

    private RollerType(int port) {
      this.port = port;
    }
  }

  public void setSpeed(double percent);
}
