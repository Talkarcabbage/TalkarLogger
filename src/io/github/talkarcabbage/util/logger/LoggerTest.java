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

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

public class LoggerTest {

	Logger logger;
	
	@Before
	public void setUp() throws Exception {
		
	}

	/*
	 * This could be more robust but for now it's as it is.
	 */
	@Test
	public void test() {
		logger = LoggerManager.getLogger(LoggerTest.class.getName());
		logger.info("This is a sysout test of INFO at INFO, the default settings.");
		assertEquals(logger, LoggerManager.getLogger(LoggerTest.class.getName()));
		
		LoggerManager.setGlobalLoggingLevel(Level.ALL);
		assertEquals(LoggerManager.getGlobalLoggerLevel(), Level.ALL);
		assertEquals(logger.getLevel(), Level.ALL);
		logger.info("This is a sysout test of INFO at ALL");
		logger.fine("This is a sysout test of the fine level at INFO");
		logger.warning("This is a syserr test of the warning level at ALL");
		logger.severe("This is a syserr test of the severe level at SEVERE");
		
		LoggerManager.setGlobalLoggingLevel(Level.INFO);
		logger.warning("This is a syserr test of the warning level at INFO");
		logger.fine("This should not print, a test of the fine level at INFO");
		
		LoggerManager.setGlobalLoggingLevel(Level.SEVERE);
		logger.info("This should not print, a test of the info level at SEVERE");
		logger.severe("This is a syserr test of the severe level at SEVERE");
		
		
	}

}
