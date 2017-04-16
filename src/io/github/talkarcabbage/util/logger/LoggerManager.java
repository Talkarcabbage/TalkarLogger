package io.github.talkarcabbage.util.logger;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author Talkarcabbage
 *
 */
public class LoggerManager {

	static final HashMap<String, Logger> hm = new HashMap<>();
	static Level logLevel;

	LoggerManager() {}
	
	public static Logger getLogger(String name) {
		if (hm.get(name) == null) {
			Logger logger = Logger.getLogger(name);
			logger.setUseParentHandlers(false);
			logger.setLevel(logLevel);
			SysoutHandler errPrint = new SysoutHandler(System.err, Level.WARNING, Level.OFF); //NOSONAR Flags Syserr/Sysout as "use a logger"
			SysoutHandler outPrint = new SysoutHandler(System.out, Level.ALL, Level.INFO); //NOSONAR Flags Syserr/Sysout as "use a logger"
			logger.addHandler(errPrint);
			logger.addHandler(outPrint);
			hm.put(name, logger);
			return logger;
		} else {
			return hm.get(name);
		}
	}
	
	public static void setGlobalLoggingLevel(Level level) {
		for (Logger value : hm.values()) {
			value.setLevel(level);
		}
	}
	
	public static Level getGlobalLoggerLevel() {
		return logLevel;
	}
	
}