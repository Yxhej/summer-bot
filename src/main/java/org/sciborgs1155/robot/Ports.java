package org.sciborgs1155.robot;

public final class Ports {
  public static final class OI {
    public static final int OPERATOR = 0;
    public static final int DRIVER = 1;
  }

  public static final class Drive {
    public static final int FRONT_LEFT_DRIVE = 11;
    public static final int REAR_LEFT_DRIVE = 10;
    public static final int FRONT_RIGHT_DRIVE = 12;
    public static final int REAR_RIGHT_DRIVE = 13;

    public static final int FRONT_LEFT_TURNING = 15;
    public static final int REAR_LEFT_TURNING = 14;
    public static final int FRONT_RIGHT_TURNING = 16;
    public static final int REAR_RIGHT_TURNING = 17;
  }

  public static final class Shoulder {
    public static final int MOTOR = 25;
    public static final int CANCODER = 26;
  }

  public static final class CubeIntake {
    public static final int INTAKE_ROLLER = 20;
    public static final int INTAKE_ROTATION = 21;
    public static final int INTAKE_ENCODER = 22;
  }

  public static final class Scorer {
    public static final int CLAW_ROLLER = 18;
    public static final int CLAW_ROTATOR = 19;
  }

  public static final class VerticalElevator {
    public static final int MOTOR = 23;
  }

  public static final class HorizontalElevator {
    public static final int MOTOR = 24;
  }
}
