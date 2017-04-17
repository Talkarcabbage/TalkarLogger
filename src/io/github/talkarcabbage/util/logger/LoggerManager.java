/*    Copyright (C) 2017 Talkarcabbage
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package io.github.talkarcabbage.util.logger;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Primary class of the library. This class creates and manages loggers and can set logging levels for all loggers returned by it.<br>
 * Intended use of this library involves first setting the global logger level via setGlobalLoggingLevel, then using getLogger per class to obtain a logger for that class.<br>
 * This class's getLogger method returns a preconfigured logger which:<br>
 * - Uses {@link SystemStreamHandler} for its handler, a customized console writer, to write to System.out or System.err depending on level.<br>
 * - Uses the current Global Logging Level, which has getters and setters.<br>
 * - Uses a custom Formatter, specified in {@link LogFormatter} which adds timestamps and additional information by {@link Level}.
 * @author Talkarcabbage
 *
 */
public class LoggerManager {

	static final HashMap<String, Logger> hm = new HashMap<>(64); //Initial size based on small projects
	static Level globalLogLevel = Level.INFO; //Default value is INFO.

	LoggerManager() {} //Prevent unnecessary instantiation
	
	/**
	 * Obtains the Java Logger with the specified name, configures it to use System.out and System.err, and sets its logging level to the current global level
	 * @param name Name of the logger to get; typically the name of the class getting the logger, but can be any string.
	 * @return A preconfigured Logger for the specified name.
	 */
	public static Logger getLogger(String name) {
		if (hm.get(name) == null) {
			Logger logger = Logger.getLogger(name);
			logger.setUseParentHandlers(false);
			logger.setLevel(globalLogLevel);
			logger.addHandler(new SystemStreamHandler(System.err, Level.WARNING, Level.OFF)); //NOSONAR Flags Syserr/Sysout as "use a logger"
			logger.addHandler(new SystemStreamHandler(System.out, Level.ALL, Level.INFO)); //NOSONAR Flags Syserr/Sysout as "use a logger"
			hm.put(name, logger);
			return logger;
		} else {
			return hm.get(name);
		}
	}
	
	/**
	 * Sets the global logging level for all Loggers returned by this class. Use of this method overrides any changes made outside of this class to individual logger levels.
	 * @param level the new Level for all loggers to use. 
	 */
	public static void setGlobalLoggingLevel(Level level) {
		globalLogLevel = level;
		for (Logger value : hm.values()) {
			value.setLevel(level);
		}
	}
	
	/**
	 * Returns the current global logging level. New Loggers generated by this class use this level when they are created.
	 * @return
	 */
	public static Level getGlobalLoggerLevel() {
		return globalLogLevel;
	}
	
}