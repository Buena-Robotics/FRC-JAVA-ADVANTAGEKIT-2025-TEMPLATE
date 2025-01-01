package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Config {
    public static final Mode SIM_MODE = Mode.SIM;
    public static final Mode ROBOT_MODE = RobotBase.isReal() ? Mode.REAL : SIM_MODE;
    public static enum Mode {
        REAL, /** Running on a real robot. */
        SIM, /** Running a physics simulator. */
        REPLAY_SIM /** Replaying from a log file. */
    }
}