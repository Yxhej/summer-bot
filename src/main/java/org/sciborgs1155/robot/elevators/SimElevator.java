package org.sciborgs1155.robot.elevators;

import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;
import static org.sciborgs1155.robot.elevators.ElevatorConstants.START_POSITION;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Horizontal;
import org.sciborgs1155.robot.elevators.ElevatorConstants.Vertical;

public class SimElevator implements ElevatorIO {

  private final ElevatorSim sim =
      new ElevatorSim(
          LinearSystemId.createElevatorSystem(
              DCMotor.getKrakenX60(1),
              Vertical.WEIGHT.in(Kilograms),
              Vertical.MAX_HEIGHT.in(Meters),
              Vertical.GEARING),
          DCMotor.getKrakenX60(1),
          Vertical.MIN_HEIGHT.in(Meters),
          Vertical.MAX_HEIGHT.in(Meters),
          true,
          START_POSITION.in(Meters));

  private final ElevatorSim horizontalSim =
      new ElevatorSim(
          LinearSystemId.createElevatorSystem(
              DCMotor.getKrakenX60(1),
              Horizontal.WEIGHT.in(Kilograms),
              Horizontal.MAX_HEIGHT.in(Meters),
              Horizontal.GEARING),
          DCMotor.getKrakenX60(1),
          Horizontal.MIN_HEIGHT.in(Meters),
          Horizontal.MAX_HEIGHT.in(Meters),
          false,
          START_POSITION.in(Meters));

  // Could make switch statement with vert/horizontal enum, but that kind of goes against the entire
  // point of (create)
  @Override
  public void setVoltage(double voltage) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setVoltage'");
  }

  @Override
  public void updateSetpoint(double setpoint) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateSetpoint'");
  }

  @Override
  public double position() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'position'");
  }

  @Override
  public double velocity() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'velocity'");
  }

  @Override
  public boolean atSetpoint() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'atSetpoint'");
  }
}
