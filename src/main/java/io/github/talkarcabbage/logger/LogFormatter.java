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

package io.github.talkarcabbage.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Formatter for use with the preconstructed Loggers. Contains the configuration for how the log should be formatted.
 * @author Talkarcabbage
 *
 */
public class LogFormatter extends Formatter {
	
	/*
	 * Log levels for various log message headers. This defines the minimum level the logger must be set to for the
	 * information to be included.
	 * Lower values are finer statements, severe is the highest non-"off" value and "off" is the highest value.
	 */
	Level dateTimeLevel = Level.INFO; //By default, log the date if the level is at or below INFO. 
	Level levelLevel = Level.OFF; //By default, always log the log level.
	Level loggerNameLevel = Level.CONFIG; //By default, log the logger name if the level is anything below info.
	Level classNameLevel = Level.FINER; //By default, log the calling class name at FINER or below.
	Level methodNameLevel = Level.FINER; //By default, log the source's method name at FINER and up.
	Level threadIDLevel = Level.FINEST; //By default, log the thread ID at FINER and up.
	
	boolean useClassSimpleName = true; //By default, use this, but allow the verbose full name if disabled.
	
	String preDelim = "[";
	String postDelim = "] ";
	String lineDelim = "\n";
	String headerDelimiter = ""; //The separator between the header (information) and log message.
	boolean padLogLevel = false; //Determine whether or not to equalize the size of the Level entry with extra spacing
	/**
	 * If true, detailed information will be printed on warnings and errors.
	 */
	boolean verboseErr = false;

	DateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
	static LogFormatter defaultInstance = new LogFormatter();
	
	public LogFormatter() {
		super();
	}
	
	/**
	 * Returns the default instance of the LogFormatter. This is primarily for use in the LoggerManager.
	 * To get the active LogFormatter (which is usually but not necessarily this one) use {@link LoggerManager#getFormatter()}
	 * @return the default instance
	 */
	public static LogFormatter getDefaultInstance() {
		return defaultInstance;
	}
	
	/* 
	 * ---------------------------------------
	 * Begin header format properties
	 * ---------------------------------------
	 */

	/**
	 * Returns the current minimum Level the date and time will be printed at.
	 * @return the current minimum level the date and time will be printed at.
	 * @see LogFormatter#setDateTimeLevel
	 */
	public Level getDateTimeLevel() {
		return dateTimeLevel;
	}

	/**
	 * Set the minimum Level required to include the date and time in the logger entries.
	 * At or below this Level (ie more verbose; FINE is less than INFO, INFO is less than WARNING, etc)
	 * the information will be printed, and above this level it will be excluded.
	 * <p>As an example, setting the level to FINE will cause it to only be printed
	 * when the Global Level is FINE, FINER, FINEST, or ALL.</p>
	 * The default value is INFO
	 * @param dateTimeLogLevel The new minimum Level
	 * @see java.util.logging.Level
	 */
	public void setDateTimeLevel(Level dateTimeLogLevel) {
		this.dateTimeLevel = dateTimeLogLevel;
	}

	/**
	 * Returns the current minimum Level required to print the Logger Level (e.g. INFO) in the records the formatter formats.
	 * @return the minimum Level required to include this in the log entries.
	 * @see LogFormatter#setLevelLevel
	 */
	public Level getLevelLevel() {
		return levelLevel;
	}
	
	/**
	 * Set the minimum Level required to include the Logger Level (e.g. INFO) in the logger entries.
	 * At or below this Level (ie more verbose; FINE is less than INFO, INFO is less than WARNING, etc)
	 * the information will be printed, and above this level it will be excluded.
	 * <p>As an example, setting the level to FINE will cause it to only be printed
	 * when the Global Level is FINE, FINER, FINEST, or ALL.</p>
	 * The default value is OFF (always print)
	 * @param levelLevel The new minimum Level
	 * @see java.util.logging.Level
	 */
	public void setLevelLevel(Level levelLevel) {
		this.levelLevel = levelLevel;
	}

	/**
	 * Returns the current minimum Level required to print the Logger Name as specified by getLogger(name) in the records the formatter formats.
	 * @return the minimum Level required to include this in the log entries.
	 * @see LogFormatter#setLoggerNameLevel
	 */
	public Level getLoggerNameLevel() {
		return loggerNameLevel;
	}

	/**
	 * Set the minimum Level required to include the Logger Name as specified by getLogger(name) in the logger entries.
	 * At or below this Level (ie more verbose; FINE is less than INFO, INFO is less than WARNING, etc)
	 * the information will be printed, and above this level it will be excluded.
	 * <p>As an example, setting the level to FINE will cause it to only be printed
	 * when the Global Level is FINE, FINER, FINEST, or ALL.</p>
	 * The default value is CONFIG
	 * @param loggerNameLevel The new minimum Level
	 * @see java.util.logging.Level
	 */
	public void setLoggerNameLevel(Level loggerNameLevel) {
		this.loggerNameLevel = loggerNameLevel;
	}

	/**
	 * Returns the current minimum Level required to print the detected class name in the records the formatter formats.
	 * @return the minimum Level required to include this in the log entries.
	 * @see LogFormatter#setClassNameLevel
	 */
	public Level getClassNameLevel() {
		return classNameLevel;
	}

	/**
	 * Set the minimum Level required to include the detected class name in the logger entries.
	 * At or below this Level (ie more verbose; FINE is less than INFO, INFO is less than WARNING, etc)
	 * the information will be printed, and above this level it will be excluded.
	 * <p>As an example, setting the level to FINE will cause it to only be printed
	 * when the Global Level is FINE, FINER, FINEST, or ALL.</p>
	 * The default value is FINER
	 * The reliability of obtaining the class name is not guaranteed in all situations.
	 * @param classNameLevel The new minimum Level
	 * @see java.util.logging.Level
	 */
	public void setClassNameLevel(Level classNameLevel) {
		this.classNameLevel = classNameLevel;
	}

	/**
	 * Returns the current minimum Level required to print name of the method that called the logger in the logger entries. (accuracy probable but not guaranteed)
	 * @return the minimum Level required to include this in the log entries.
	 * @see LogFormatter#setMethodNameLevel
	 */
	public Level getMethodNameLevel() {
		return methodNameLevel;
	}

	/**
	 * Set the minimum Level required to include the name of the method that called the logger in the logger entries. (accuracy probable but not guaranteed)
	 * At or below this Level (ie more verbose; FINE is less than INFO, INFO is less than WARNING, etc)
	 * the information will be printed, and above this level it will be excluded.
	 * <p>As an example, setting the level to FINE will cause it to only be printed
	 * when the Global Level is FINE, FINER, FINEST, or ALL.</p>
	 * The default value is FINER
	 * @param methodNameLevel The new minimum Level
	 * @see java.util.logging.Level
	 */
	public void setMethodNameLevel(Level methodNameLevel) {
		this.methodNameLevel = methodNameLevel;
	}

	/**
	 * Returns the current minimum Level required to print the ID of the thread that called the logger in the records the formatter formats.
	 * @return the minimum Level required to include this in the log entries.
	 * @see LogFormatter#setThreadIDLevel
	 */
	public Level getThreadIDLevel() {
		return threadIDLevel;
	}

	/**
	 * Set the minimum Level required to include the thread ID in the logger entries.
	 * At or below this Level (ie more verbose; FINE is less than INFO, INFO is less than WARNING, etc)
	 * the information will be printed, and above this level it will be excluded.
	 * <p>As an example, setting the level to FINE will cause it to only be printed
	 * when the Global Level is FINE, FINER, FINEST, or ALL.</p>
	 * The default value is FINEST
	 * @param threadIDLevel The new minimum Level
	 * @see java.util.logging.Level
	 */
	public void setThreadIDLevel(Level threadIDLevel) {
		this.threadIDLevel = threadIDLevel;
	}

	/**
	 * Returns whether the LogFormatter should use the class's getSimpleName() rather than getName().
	 * @return whether to use the simple name instead of the full name.
	 * @see LogFormatter#setUseClassSimpleName
	 */
	public boolean isUseClassSimpleName() {
		return useClassSimpleName;
	}

	/**
	 * Sets whether the LogFormatter should use the class's getSimpleName() rather than getName().
	 * The default value is true.
	 * @param useClassSimpleName whether to use the simple name instead of the full name.
	 */
	public void setUseClassSimpleName(boolean useClassSimpleName) {
		this.useClassSimpleName = useClassSimpleName;
	}
	
	 /**
	  * Get the starting delimiter used by the formatter. This is added in front of each header (ex. date time).
	  * @return the starting delimiter
	  */
	public String getPreDelim() {
		return preDelim;
	}

	 /**
	  * Set the starting delimiter used by the formatter. This is added in front of each header (ex. date time).
	  * The default is "["
	  * @param preDelim the new starting delimiter
	  */
	public void setPreDelim(String preDelim) {
		this.preDelim = preDelim;
	}

	 /**
	  * Get the ending delimiter used by the formatter. This is added after each header (ex. date time).
	  * @return the starting delimiter
	  */
	public String getPostDelim() {
		return postDelim;
	}

	 /**
	  * Set the ending delimiter used by the formatter. This is added after each header (ex. date time).
	  * The default is "] "
	  * @param postDelim the new ending delimiter
	  */
	public void setPostDelim(String postDelim) {
		this.postDelim = postDelim;
	}

	/**
	 * Returns the current line ending delimiter.
	 * The default is "\n"
	 * @return the current line delimiter
	 */
	public String getLineDelim() {
		return lineDelim;
	}

	/**
	 * Returns the current line ending delimiter.
	 * The default is "\n"
	 * @param lineDelim the new line delimiter, such as "\r\n"
	 */
	public void setLineDelim(String lineDelim) {
		this.lineDelim = lineDelim;
	}

	/**
	 * Returns the delimiter separating the log's information from its message.
	 * @return the header delimiter
	 */
	public String getHeaderDelimiter() {
		return headerDelimiter;
	}

	/**
	 * Sets the delimiter separating the log's information from its message.
	 * The default value is "" (an empty string)
	 * @param headerDelimiter the new header delimiter.
	 */
	public void setHeaderDelimiter(String headerDelimiter) {
		this.headerDelimiter = headerDelimiter;
	}

	/**
	 * Returns whether or not warning and error messages display verbose header information
	 * regardless of the individual Level settings of said header information entries (e.g. date time).
	 * @return Whether warning and error message headers are currently verbose
	 */
	public boolean isVerboseErr() {
		return verboseErr;
	}

	/**
	 * Sets whether or not warning and error messages display verbose header information.
	 * If this is set to true full header information will be printed regardless of the individual Level settings of said header information entries (e.g. date time).
	 * The default is false
	 * @param verboseErr Whether warning and error message headers should be verbose
	 */
	public void setVerboseErr(boolean verboseErr) {
		this.verboseErr = verboseErr;
	}

	/**
	 * Returns the DateFormat used by this logger to format the date time. 
	 * The default is a SimpleDateFormat("MMM dd yyyy HH:mm:ss");
	 * @return The DateFormat
	 */
	public DateFormat getDateFormat() {
		return dateFormat;
	}
	
	/**
	 * Sets the DateFormat used by this logger to format the date time. 
	 * The default is a SimpleDateFormat("MMM dd yyyy HH:mm:ss");
	 * @param dateFormat the new DateFormat for the formatter to use
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	

	/**
	 * Returns whether or not the Level will be padded if printed, to make the length of all Level entries equal in the log.
	 * @return whether Level entries are padded
	 */
	public boolean isPadLogLevel() {
		return padLogLevel;
	}

	/**
	 * Sets whether or not the Level will be padded if printed, to make the length of all Level entries equal in the log.
	 * For example, [INFO] will have three extra spaces added to reach the length of [WARNING]
	 * @param padLogLevel whether to pad the Level in log entries
	 */
	public void setPadLogLevel(boolean padLogLevel) {
		this.padLogLevel = padLogLevel;
	}
	
	/* 
	 * ---------------------------------------
	 * End header format properties
	 * ---------------------------------------
	 */

	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder(120);
		
		formatHeader(sb, record);
		
		sb.append(record.getMessage() == null ? " " : record.getMessage());
		
		if (record.getThrown() != null) {
			StringWriter stringWriter = new StringWriter();
			record.getThrown().printStackTrace(new PrintWriter(stringWriter)); //Based on Guava and openjdk
			sb.append(stringWriter.toString()); //Closing stream should be unnecessary as it has no effect
		}
		
		sb.append(lineDelim);
		return sb.toString();
	}
	
	/**
	 * Formats the header portion (information before the message) of the record.
	 * @param sb StringBuilder to modify.
	 * @param record The log record
	 */
	public void formatHeader(StringBuilder sb, LogRecord record) {
		if (shouldBeInLog(dateTimeLevel, record.getLevel())) {
			sb
			.append(preDelim)
			.append(dateFormat.format(record.getMillis()))
			.append(postDelim);
		}
		if (shouldBeInLog(levelLevel, record.getLevel())) {
			sb.append(preDelim)
			.append(record.getLevel());
			sb.append(postDelim);

			if (padLogLevel) {
				addPaddingForLevel(sb, record.getLevel());
			}
		}
		
		if (shouldBeInLog(loggerNameLevel, record.getLevel())) { 
			sb.append(preDelim)
			.append(record.getLoggerName())
			.append(postDelim);
		}
		
		if (shouldBeInLog(classNameLevel, record.getLevel())) {
			
			sb.append(preDelim);
			try {
				if (useClassSimpleName){
					sb.append(Class.forName(record.getSourceClassName()).getSimpleName());
				} else {
					sb.append(Class.forName(record.getSourceClassName()).getName());
				}
			} 
			catch (ClassNotFoundException e) {} //NOSONAR We don't need to log this, and we know it may occur normally.
			
			if (shouldBeInLog(methodNameLevel, record.getLevel())) {
				sb.append("#")
				.append(record.getSourceMethodName());
			}
			
			if (shouldBeInLog(threadIDLevel, record.getLevel())) {
				sb.append(" thread ")
				.append(record.getThreadID());
			}
			sb.append(postDelim);
		}
		sb.append(headerDelimiter);
	}
	
	/**
	 * Returns whether a property should be included in the logger header based on its minimum level, the record level, and global level.
	 * Note that the minLevel and recordLevel are not necessarily correlated with each other in regards to the result of this method (e.g. minLevel can be less than, greater than, or equal to recordLevel with no effect)
	 * @param minLevel Minimum level to include this header in.
	 * @param recordLevel Logger level of the record being logged. Used to determine if verboseErr is applied if this value is WARNING or SEVERE
	 * @return If the property should be included.
	 */
	public boolean shouldBeInLog(Level minLevel, Level recordLevel) {
		return (LoggerManager.getInstance().getGlobalLoggerLevel().intValue() <= minLevel.intValue()) || (verboseErr && recordLevel.intValue() >= Level.WARNING.intValue());
	}
	
	/**
	 * Adds padding to the stringbuilder in the form of trailing spaces to equalize the length of Level headers.
	 * @param sb The stringbuilder
	 * @param level - the level
	 */
	void addPaddingForLevel(StringBuilder sb, Level level) {
		if (padLogLevel) {
			int paddingCount = getPaddingForLevel(level);
			for (int i = 0; i < paddingCount; i++) {
				sb.append(" ");
			}
		}
	}
	
	/**
	 * Determines the amount of trailing spaces required to equalize the length of all Level entries in the logs.
	 * @param level The level to calculate padding for
	 * @return The required amount of trailing spaces
	 */
	public int getPaddingForLevel(Level level) {
		return Level.WARNING.getName().length() - level.getName().length();
	}

}