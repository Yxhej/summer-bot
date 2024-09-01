package org.sciborgs1155.robot.commands;

import static org.sciborgs1155.robot.subsystems.claws.intake.IntakeWrist.State;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import org.sciborgs1155.robot.subsystems.claws.intake.IntakeRollers;
import org.sciborgs1155.robot.subsystems.claws.intake.IntakeWrist;
import org.sciborgs1155.robot.subsystems.tunnel.Tunnel;

public class CubeTunnel {
  private final IntakeWrist wrist;
  private final IntakeRollers rollers;
  private final Tunnel tunnel;

  public CubeTunnel(IntakeWrist wrist, IntakeRollers rollers, Tunnel tunnel) {
    this.wrist = wrist;
    this.rollers = rollers;
    this.tunnel = tunnel;
  }

  public Command intake() {
    return wrist.moveTo(State.INTAKE).alongWith(rollers.intake()).until(tunnel::atEntrance);
  }

  public Command feed() {
    return tunnel.feed(0.75);
  }

  public Command reverse() {
    return Commands.either(
        tunnel.feed(-0.75),
        tunnel.feed(-0.75).alongWith(rollers.runRollers(-0.75)),
        () -> wrist.state() == State.UP);
  }

  public Command instantPass() {
    return intake().alongWith(feed());
  }

  public Command passToClaw() {
    return tunnel.runExit(0.75);
  }
}
