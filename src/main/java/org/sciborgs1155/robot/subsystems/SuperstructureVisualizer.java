package org.sciborgs1155.robot.subsystems;

import static edu.wpi.first.units.Units.*;
import static org.sciborgs1155.robot.Constants.*;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import org.sciborgs1155.robot.subsystems.claws.claw.ClawConstants;
import org.sciborgs1155.robot.subsystems.claws.claw.ClawWrist;
import org.sciborgs1155.robot.subsystems.claws.intake.IntakeConstants;
import org.sciborgs1155.robot.subsystems.claws.intake.IntakeWrist;
import org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.Horizontal;
import org.sciborgs1155.robot.subsystems.elevators.ElevatorConstants.Vertical;
import org.sciborgs1155.robot.subsystems.elevators.HorizontalElevator;
import org.sciborgs1155.robot.subsystems.elevators.VerticalElevator;
import org.sciborgs1155.robot.subsystems.shoulder.Shoulder;
import org.sciborgs1155.robot.subsystems.shoulder.ShoulderConstants;

public class SuperstructureVisualizer implements Sendable, AutoCloseable {
  private final Mechanism2d mech;

  private final MechanismLigament2d intake;
  private final MechanismLigament2d vertical;
  private final MechanismLigament2d horizontal;
  private final MechanismLigament2d shoulder;
  private final MechanismLigament2d claw;

  private final VerticalElevator verticalElevator;
  private final HorizontalElevator horizontalElevator;
  private final IntakeWrist intakeWrist;
  private final Shoulder shoulderArm;
  private final ClawWrist clawWrist;

  public SuperstructureVisualizer(
      VerticalElevator verticalElevator,
      HorizontalElevator horizontalElevator,
      IntakeWrist cubeIntake,
      Shoulder shoulderArm,
      ClawWrist scoringClaw) {
    this.verticalElevator = verticalElevator;
    this.horizontalElevator = horizontalElevator;
    this.intakeWrist = cubeIntake;
    this.shoulderArm = shoulderArm;
    this.clawWrist = scoringClaw;

    mech = new Mechanism2d(3, 3);
    final MechanismRoot2d center = mech.getRoot("Base", 1, 1);
    final MechanismRoot2d bumper = mech.getRoot("Intake Joint", 0.5, 1.05);
    intake =
        bumper.append(
            new MechanismLigament2d(
                "Intake", IntakeConstants.LENGTH.in(Meters), 270, 4, new Color8Bit(Color.kAzure)));
    vertical =
        center.append(
            new MechanismLigament2d(
                "Vertical Elevator",
                Vertical.MAX_HEIGHT.in(Meters),
                90,
                4,
                new Color8Bit(Color.kYellow)));
    horizontal =
        vertical.append(
            new MechanismLigament2d(
                "Horizontal Elevator",
                Horizontal.MAX_HEIGHT.in(Meters),
                -90,
                4,
                new Color8Bit(Color.kBlue)));
    shoulder =
        horizontal.append(
            new MechanismLigament2d(
                "Shoulder",
                ShoulderConstants.LENGTH.in(Meters),
                0,
                4,
                new Color8Bit(Color.kAliceBlue)));
    claw =
        shoulder.append(
            new MechanismLigament2d(
                "Claw", ClawConstants.LENGTH.in(Meters), 0, 4, new Color8Bit(Color.kWhite)));
  }

  public void updatePositions() {
    intake.setAngle(Units.radiansToDegrees(intakeWrist.angle()) + 270.0);
    shoulder.setAngle(shoulderArm.angle().getDegrees());
    claw.setAngle(Units.radiansToDegrees(clawWrist.angle()));
    horizontal.setLength(horizontalElevator.height());
    vertical.setLength(verticalElevator.height());
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    mech.initSendable(builder);
  }

  @Override
  public void close() throws Exception {
    mech.close();
  }
}
