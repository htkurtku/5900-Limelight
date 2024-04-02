package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PivotSubsystem extends SubsystemBase {
  private CANSparkMax pivotMotor;

  public PivotSubsystem() {
    pivotMotor = new CANSparkMax(0, MotorType.kBrushless); // Update with actual motor ID
    // Initialize motor, PID controller, etc. as needed
  }

  public void setPivotAngle(double angle) {
    // Convert angle to motor output or position
    // implement PID control
    double motorOutput = angleToMotorOutput(angle);
    pivotMotor.set(motorOutput);
  }

  private double angleToMotorOutput(double angle) {
    // Placeholder 
    return angle / 360.0; // Assuming a full rotation is 360 degrees
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
