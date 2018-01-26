/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2472.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;







//import usfirst.frc.team2168.robot.FalconPathPlanner;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	int index=0;
	long time;
	double[][] waypointPath = new double[][]{
		{0,0},
		{5,0}
};

	FalconPathPlanner path=new FalconPathPlanner(waypointPath); 
	
	double Targetspeed;
	Joystick xbox=new Joystick(0);
	TalonSRX leftTalon,rightTalon;
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
		time =System.currentTimeMillis();
		
		rightTalon=new TalonSRX(2);
		rightTalon.setInverted(true);
		leftTalon=new TalonSRX(14);
		leftTalon.setSensorPhase(true);
		rightTalon.setSensorPhase(true);
		rightTalon.config_kF(0,1.9175, 0);
		rightTalon.config_kP(0, 3.5, 0);
		rightTalon.config_kI(0, .03, 0);
		rightTalon.config_kD(0, 175, 0);
		
		leftTalon.config_kF(0, 1.363, 0);
		leftTalon.config_kP(0,3, 0);
		leftTalon.config_kI(0, .02, 0);
		leftTalon.config_kD(0, 300, 0);
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
		path.calculate(15, .02, 23.5);
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
//1309 
		System.out.println(leftinchesToNative(path.smoothLeftVelocity[index][1])+"  "+RightinchesToNative(path.smoothRightVelocity[index][1]));
		
		 leftTalon.set(ControlMode.Velocity,leftinchesToNative(path.smoothLeftVelocity[index][1]));
		 rightTalon.set(ControlMode.Velocity,RightinchesToNative(path.smoothRightVelocity[index][1]));
		
		 index++;
		
	}

	/**
	 * This function is called periodically during operator control.
	 *///1436 1424 1455
	//946 960 977
	@Override
	public void teleopPeriodic() {
		Targetspeed=(350)*xbox.getRawAxis(0)/600;
		leftTalon.set(ControlMode.Velocity,Targetspeed*1420);
		rightTalon.set(ControlMode.Velocity,Targetspeed*1320);
		System.out.println(leftTalon.getSelectedSensorPosition(0));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testInit() {
		
		
		
	}
	public double leftinchesToNative(double speed) {
		//speed*10=counts per second
		//53.0616576 counts per inch
		return (speed*10)*1500/(53.0616576);
		
		
	}
	public double RightinchesToNative(double speed) {
		//speed*10=counts per second
		//53.0616576 counts per inch
		return (speed*10)*1310/(53.0616576);
		
		
	}
	public void testPeriodic() {
		
		
		 
	}
}
