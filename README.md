# Charged Up 1323 Robot Code Recreation

A codebase written for practice (and fun) that simulates 1323's 2023 Charged-Up game robot. Includes its own unique (and not that great) scoring system.

Missing related vision components of the robot. Has a simulation that works, mostly. GIFs below.

![](https://i.gyazo.com/742a4903b876334bbc87372aa85e4129.gif)

*Superstructure scoring on a high-cone node, then back to default stow*

![](https://i.gyazo.com/2baf2ac8f5d0047e7f8591598960dcc3.gif)

*Superstructure moving from stow, to mid-cone, to high-cone*

## Total subsystems

- [Vertical elevator](/src/main/java/org/sciborgs1155/robot/subsystems/elevators/VerticalElevator.java)
- [Horizontal elevator](/src/main/java/org/sciborgs1155/robot/subsystems/elevators/HorizontalElevator.java)
- [Shoulder](/src/main/java/org/sciborgs1155/robot/subsystems/shoulder/Shoulder.java)
- Claw (scoring): [wrist](/src/main/java/org/sciborgs1155/robot/subsystems/claws/ClawWrist.java) and [rollers](/src/main/java/org/sciborgs1155/robot/subsystems/claws/claw/ClawRollers.java)
- Cube Intake: [wrist](/src/main/java/org/sciborgs1155/robot/subsystems/claws/intake/IntakeWrist.java) and [rollers](/src/main/java/org/sciborgs1155/robot/subsystems/claws/intake/IntakeRollers.java)
- [Tunnel](/src/main/java/org/sciborgs1155/robot/subsystems/tunnel/Tunnel.java)
- [Vision (undeveloped)](/src/main/java/org/sciborgs1155/robot/subsystems/vision/Vision.java)
- Winch (not real)
