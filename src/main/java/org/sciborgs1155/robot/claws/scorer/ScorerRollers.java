package org.sciborgs1155.robot.claws.scorer;

import static org.sciborgs1155.robot.Ports.Scorer.*;
import static org.sciborgs1155.robot.claws.scorer.ScorerConstants.*;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ScorerRollers extends SubsystemBase {
  private final TalonFX rollers = new TalonFX(CLAW_ROLLER);

  public ScorerRollers() {
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
}
