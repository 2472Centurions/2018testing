/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2472.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	Double x,xoffset;
	AnalogInput centerDist=new AnalogInput(3);
	TalonSRX left=new TalonSRX(14);
	TalonSRX right=new TalonSRX(2);
	NetworkTableEntry entry,entry2; 
	NetworkTableInstance instance=NetworkTableInstance.getDefault();
	NetworkTable table;
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	right.setInverted(true);
		table=instance.getTable("testTable");
		entry=table.getEntry("shootCx");
		entry2=table.getEntry("off_switch");
				
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
				
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}
	public void teleopInit() {
		
	}
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		x=entry.getDouble(0.0);
		if(x != -1.0) {
			System.out.println(x);
			double p = 600.0;
			double speed = 0.5;
			double slowVal = (2000-centerDist.getValue())/2000;
			if(centerDist.getValue()<1200) {
			if(x!=0.0&&x>320.0) {
				double xoffset = Math.abs(x-320)/p;
				System.out.print(" xoffset: "+xoffset+" "+"slowval: "+slowVal+" ");

				right.set(ControlMode.PercentOutput, (1-xoffset)*speed);
				
				left.set(ControlMode.PercentOutput, speed);
				
			}else if(x<320.0&&x!=0.0){
				double xoffset = Math.abs(x-320)/p;
				System.out.print(" xoffset: "+xoffset+" "+"slowval: "+slowVal+" ");
				left.set(ControlMode.PercentOutput, (1-xoffset)*speed);
				
				//Proportional 
				right.set(ControlMode.PercentOutput, speed);
				
			}
			}else if(centerDist.getValue()>1200) {
				
			left.set(ControlMode.PercentOutput, 0);
			right.set(ControlMode.PercentOutput, 0);
			}
		}else {
			left.set(ControlMode.PercentOutput, 0.0);
			right.set(ControlMode.PercentOutput, 0.0);
		}
		
		
		}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testInit() {
		left.set(ControlMode.PercentOutput, 0);
		right.set(ControlMode.PercentOutput, 0);
	}
	public void testPeriodic() {
		System.out.println(entry2.setNumber(0));
		
	}
}