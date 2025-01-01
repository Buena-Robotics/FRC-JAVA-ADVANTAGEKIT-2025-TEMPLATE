// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;
import org.littletonrobotics.urcl.URCL;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends LoggedRobot {
	private Command m_autonomousCommand;

	private final RobotContainer robot_container;

	public Robot() {
		// Set up data receivers & replay source
		switch (Config.ROBOT_MODE) {
		case REAL:
			// Running on a real robot, log to a USB stick ("/U/logs")
			Logger.addDataReceiver(new WPILOGWriter());
			Logger.addDataReceiver(new NT4Publisher());
			break;

		case SIM:
			// Running a physics simulator, log to NT
			Logger.addDataReceiver(new NT4Publisher());
			break;

		case REPLAY_SIM:
			// Replaying a log, set up replay source
			setUseTiming(false); // Run as fast as possible
			String logPath = LogFileUtil.findReplayLog();
			Logger.setReplaySource(new WPILOGReader(logPath));
			Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
			break;
		}

		// Initialize URCL
		Logger.registerURCL(URCL.startExternal());

		// Start AdvantageKit logger
		Logger.start();
		robot_container = new RobotContainer();
	}

	@Override public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override public void disabledInit() {

	}

	@Override public void disabledPeriodic() {
	}

	@Override public void disabledExit() {
	}

	@Override public void autonomousInit() {
		m_autonomousCommand = robot_container.getAutonomousCommand();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.schedule();
		}
	}

	@Override public void autonomousPeriodic() {
	}

	@Override public void autonomousExit() {
	}

	@Override public void teleopInit() {
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override public void teleopPeriodic() {
	}

	@Override public void teleopExit() {
	}

	@Override public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override public void testPeriodic() {
	}

	@Override public void testExit() {
	}
}
