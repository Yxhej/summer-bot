package org.sciborgs1155.robot;

import static edu.wpi.first.units.Units.Meters;
import static org.sciborgs1155.robot.Constants.ROBOT_LENGTH;
import static org.sciborgs1155.robot.Constants.ROBOT_WIDTH;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import org.sciborgs1155.robot.claws.intake.IntakeWrist;
import org.sciborgs1155.robot.claws.scorer.ScorerWrist;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical;
import org.sciborgs1155.robot.elevators.HorizontalElevator;
import org.sciborgs1155.robot.elevators.VerticalElevator;

public class SuperstructureVisualizer {
  private final Mechanism2d structure;
  private final MechanismRoot2d base;

  private final MechanismLigament2d intakeClaw;
  private final MechanismLigament2d vertical;
  private final MechanismLigament2d horizontal;
  private final MechanismLigament2d scorer;

  private final VerticalElevator verticalElevator;
  private final HorizontalElevator horizontalElevator;
  private final IntakeWrist intake;
  private final ScorerWrist claw;

  public SuperstructureVisualizer(
      VerticalElevator verticalElevator,
      HorizontalElevator horizontalElevator,
      IntakeWrist cubeIntake,
      ScorerWrist scoringClaw) {
    this.verticalElevator = verticalElevator;
    this.horizontalElevator = horizontalElevator;
    this.intake = cubeIntake;
    this.claw = scoringClaw;

    structure = new Mechanism2d(2, 2);
    final MechanismRoot2d base =
        structure.getRoot(
            "Base",
            ROBOT_LENGTH.divide(10.0 * 2.0).in(Meters),
            ROBOT_WIDTH.divide(10.0 * 2.0).in(Meters));
    vertical =
        base.append(
            new MechanismLigament2d(
                "Vertical Elevator",
                Vertical.MAX_HEIGHT.in(Meters),
                90,
                4,
                new Color8Bit(Color.kYellow)));
  }
}
