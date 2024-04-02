package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
    private NetworkTable limelightTable;
    private final DriveSubsystem driveSubsystem;
    private final PivotSubsystem pivotSubsystem; // Use PivotSubsystem for the arm

    public LimelightSubsystem(DriveSubsystem driveSubsystem, PivotSubsystem pivotSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.pivotSubsystem = pivotSubsystem;
        this.limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        setPipeline(4); // Assuming pipeline 4 is set up for AprilTag detection
    }

    public void setPipeline(int pipeline) {
        limelightTable.getEntry("pipeline").setNumber(pipeline);
    }

    public boolean isTargetVisible() {
        return limelightTable.getEntry("tv").getDouble(0) == 1;
    }

    public double getVerticalOffset() {
        return limelightTable.getEntry("ty").getDouble(0);
    }

    public double getDistanceFromAprilTag() {
        double angleToTarget = getVerticalOffset();
        // Calibration required for these constants
        final double TARGET_HEIGHT = 2.0; 
        final double LIMELIGHT_HEIGHT = 1.0;
        final double LIMELIGHT_ANGLE = 30.0;

        double angle = Math.toRadians(LIMELIGHT_ANGLE + angleToTarget);
        return (TARGET_HEIGHT - LIMELIGHT_HEIGHT) / Math.tan(angle);
    }

    public void autoAlignAndAdjustPivot() {
        if (isTargetVisible()) {
            double distance = getDistanceFromAprilTag();
            double pivotAngle = distanceToPivotAngle(distance);

            pivotSubsystem.setPivotAngle(pivotAngle);

            double xError = getHorizontalOffset();
            double turnAdjust = 0.1 * xError; // Simple proportional control for alignment
            driveSubsystem.turn(turnAdjust);
        } else {
            driveSubsystem.stop();
            // Optionally handle pivot positioning when no target is visible
        }
    }

    private double distanceToPivotAngle(double distance) {
        // Convert distance to pivot angle; placeholder function
        return distance * 10; // Replace with actual logic based on your robot's configuration
    }

    // Additional methods...
}
