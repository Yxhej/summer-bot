package org.sciborgs1155.robot.claws.intake;

import static org.sciborgs1155.robot.Ports.CubeIntake.*;
import static org.sciborgs1155.robot.claws.intake.IntakeConstants.*;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeRollers extends SubsystemBase {
  private final TalonFX rollers = new TalonFX(INTAKE_ROLLER);

  public IntakeRollers() {
    TalonFXConfiguration cfg = new TalonFXConfiguration();
    rollers.getConfigurator().apply(cfg);

    cfg.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    cfg.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    cfg.CurrentLimits.StatorCurrentLimit = 15;
    cfg.CurrentLimits.SupplyCurrentLimit = 20;

    rollers.getConfigurator().apply(cfg);
  }

  public Command runRollers(double speed) {
    return run(() -> rollers.set(speed)).finallyDo(() -> rollers.set(0));
  }

  public Command spit() {
    return runRollers(SPIT_SPEED);
  }

  public Command intake() {
    return runRollers(INTAKE_SPEED);
  }
}
