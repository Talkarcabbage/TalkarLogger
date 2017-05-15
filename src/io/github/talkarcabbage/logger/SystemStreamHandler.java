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

import java.io.PrintStream;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * Behaves similarly to a ConsoleHandler, but allows the use of other streams; intended streams are System.out and System.in.
 * Takes two Levels, and will only log between those levels. Primarily used to separate System.err from System.out logging.
 * @author Talkarcabbage
 *
 */
public class SystemStreamHandler extends StreamHandler {

	Level minLevel;
	Level maxLevel;
	
	/**
	 * Creates a Console Handler with the specified minimum and maximum levels. The lowest normal level is finest, and the highest is severe. Alternatively use OFF and NONE
	 * @param stream The PrintStream to print to
	 * @param minLevel The least severe Level
	 * @param maxLevel The most severe level.
	 * @param formatter The formatter to use; this is typically specified via the LoggerManager instance.
	 */
	public SystemStreamHandler(PrintStream stream, final Level minLevel, final Level maxLevel, Formatter formatter) {
		super(stream, formatter);
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		setLevel(Level.ALL);
		setFilter(new Filter() {
			@Override
			public boolean isLoggable(LogRecord record) {
				return record.getLevel().intValue() >= minLevel.intValue() && record.getLevel().intValue() <= maxLevel.intValue();
			}
		});
	}
	
	@Override
	public void publish(LogRecord record) {
		super.publish(record);
		flush();
	}
	
	@Override
	public void close() {
		flush();
	}
}