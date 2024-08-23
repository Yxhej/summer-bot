package org.sciborgs1155.robot.claws;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class RealRoller implements RollerIO {
  private final TalonFX rollers;

  public RealRoller(RollerType type) {
    rollers = new TalonFX(type.port);

    TalonFXConfiguration cfg = new TalonFXConfiguration();
    rollers.getConfigurator().apply(cfg);

    cfg.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    cfg.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    cfg.CurrentLimits.StatorCurrentLimit = 15;
    cfg.CurrentLimits.SupplyCurrentLimit = 20;

    rollers.getConfigurator().apply(cfg);
  }

  public void setSpeed(double percent) {
    rollers.set(percent);
  }
}
