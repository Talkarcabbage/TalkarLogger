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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;


/**
 * 
 * @author Talkarcabbage
 *
 */
public class LogFormatter extends Formatter {

	protected final DateFormat format = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
	public static final LogFormatter instance;
	static {
		instance = new LogFormatter();
	}
	
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb
			.append("[")
			.append(format.format(record.getMillis()))
			.append("] [")
			.append(record.getLevel())
			.append("] ");
		if (LoggerManager.getGlobalLoggerLevel().intValue() < Level.INFO.intValue()) { //Medium info if logging more than INFO
			sb.append("[")
			.append(record.getLoggerName())
			.append("] ");
		}
		if (LoggerManager.getGlobalLoggerLevel().intValue() == Level.ALL.intValue()) { //Maximum information
			sb.append("[");
			try {
				sb.append(Class.forName(record.getSourceClassName()).getSimpleName());
			} 
			catch (ClassNotFoundException e) {} //NOSONAR We don't need to log this, and we know it may occur normally.
			sb.append(".")
			.append(record.getSourceMethodName())
			.append(" thread ")
			.append(record.getThreadID())
			.append("] ");
		}
		sb.append(record.getMessage() == null ? " " : record.getMessage());
		if (record.getThrown() != null) {
			StringWriter stringWriter = new StringWriter();
			record.getThrown().printStackTrace(new PrintWriter(stringWriter)); //Based on Guava and openjdk
			sb.append(stringWriter.toString()); //Closing stream should be unnecessary as it has no effect
		}
		
		sb.append("\n");
		return sb.toString();
	}
	
	public static LogFormatter getInstance() {
		return instance;
	}
}