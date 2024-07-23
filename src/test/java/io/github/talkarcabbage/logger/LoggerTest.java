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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the logger manager and returned logger
 * @author Talkarcabbage
 *
 */
public class LoggerTest {

	Logger logger;
	
	/*
	 * This could be more robust but for now it's as it is.
	 */
	@Test
	public void test() {
		logger = LoggerManager.getInstance().getLogger(LoggerTest.class.getName());
		LoggerManager.getInstance().getFormatter().verboseErr = false;
		logger.info("This is a sysout test of INFO at global level INFO");
		logger.fine("This is a sysout test of the fine level at global level INFO");
		logger.warning("This is a syserr test of the warning level at global level INFO");
		logger.severe("This is a syserr test of the severe level at global level INFO");
		assertEquals(logger, LoggerManager.getInstance().getLogger(LoggerTest.class.getName()));
		
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.ALL);
		assertEquals(LoggerManager.getInstance().getGlobalLoggerLevel(), Level.ALL);
		assertEquals(logger.getLevel(), Level.ALL);
		logger.info("This is a sysout test of INFO at global level ALL");
		logger.fine("This is a sysout test of the fine level at global level ALL");
		logger.warning("This is a syserr test of the warning level at global level ALL");
		logger.severe("This is a syserr test of the severe level at global level ALL");
		assertTrue(logger.isLoggable(Level.FINEST));
		assertTrue(logger.isLoggable(Level.WARNING));
		assertTrue(logger.isLoggable(Level.SEVERE));
		
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.INFO);
		logger.warning("This is a syserr test of the warning level at INFO");
		logger.info("This is a test of the info level at global level INFO");
		logger.fine("This should not print, a test of the fine level at INFO");
		assertFalse(logger.isLoggable(Level.FINE));
		assertTrue(logger.isLoggable(Level.WARNING));
		assertTrue(logger.isLoggable(Level.SEVERE));
		
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.SEVERE);
		logger.info("This should not print, a test of the info level at global level SEVERE");
		logger.severe("This is a syserr test of the severe level at global level SEVERE");
		assertFalse(logger.isLoggable(Level.FINE));
		assertFalse(logger.isLoggable(Level.WARNING));
		assertTrue(logger.isLoggable(Level.SEVERE));

	}
	
	@Test
	public void testVerboseErr() {
		logger = LoggerManager.getInstance().getLogger(LoggerTest.class.getName());
		LoggerManager.getInstance().getFormatter().verboseErr = true;
		logger.info("This is a sysout test of INFO at global level INFO");
		logger.fine("This is a sysout test of the fine level at global level INFO");
		logger.warning("This is a syserr test of the warning level at global level INFO");
		logger.severe("This is a syserr test of the severe level at global level INFO");
		
		assertEquals(logger, LoggerManager.getInstance().getLogger(LoggerTest.class.getName()));
		
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.ALL);
		assertEquals(LoggerManager.getInstance().getGlobalLoggerLevel(), Level.ALL);
		assertEquals(logger.getLevel(), Level.ALL);
		logger.info("This is a sysout test of INFO at global level ALL");
		logger.fine("This is a sysout test of the fine level at global level ALL");
		logger.warning("This is a syserr test of the warning level at global level ALL");
		logger.severe("This is a syserr test of the severe level at global level ALL");
		assertTrue(logger.isLoggable(Level.FINEST));
		assertTrue(logger.isLoggable(Level.WARNING));
		assertTrue(logger.isLoggable(Level.SEVERE));
		
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.INFO);
		logger.warning("This is a syserr test of the warning level at INFO");
		logger.info("This is a test of the info level at global level INFO");
		logger.fine("This should not print, a test of the fine level at INFO");
		assertFalse(logger.isLoggable(Level.FINE));
		assertTrue(logger.isLoggable(Level.WARNING));
		assertTrue(logger.isLoggable(Level.SEVERE));
		
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.SEVERE);
		logger.info("This should not print, a test of the info level at global level SEVERE");
		logger.severe("This is a syserr test of the severe level at global level SEVERE");
		assertFalse(logger.isLoggable(Level.FINE));
		assertFalse(logger.isLoggable(Level.WARNING));
		assertTrue(logger.isLoggable(Level.SEVERE));
	}
	
	@Test
	public void testShouldBeInLog() {
		
		//Default settings
		LoggerManager.getInstance().getFormatter().verboseErr = false;
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.INFO);
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.INFO, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.WARNING, Level.INFO));
		assertFalse(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINE, Level.INFO));
		assertFalse(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINE, Level.WARNING));

		//Global level of FINE
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.FINE);
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.INFO, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.WARNING, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINE, Level.INFO));
		assertFalse(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINEST, Level.WARNING));
		
		//Global level of ALL
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.ALL);
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.INFO, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.WARNING, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINE, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINEST, Level.WARNING));
		
		//With verboseErr
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.INFO);
		LoggerManager.getInstance().getFormatter().verboseErr = true;
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.INFO, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.WARNING, Level.INFO));
		assertFalse(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINE, Level.INFO));
		assertTrue(LoggerManager.getInstance().getFormatter().shouldBeInLog(Level.FINE, Level.WARNING));
	}
	
	@Test
	public void testExceptions() {
		logger = LoggerManager.getInstance().getLogger(LoggerTest.class.getName());
		try {
			createStack();
		} catch (UnsupportedOperationException e) {
			logger.log(Level.SEVERE, "Exception caught: ", e);
		}
	}
	
	public void createStack() {
		throw new UnsupportedOperationException("Test");
	}
	
	public void resetLoggerFinest() {
		LoggerManager.getInstance().getFormatter().verboseErr = false;
		logger = LoggerManager.getInstance().getLogger(LoggerTest.class.getName());
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.FINEST);

	}
	
	@Test
	public void testRemovableDate() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setDateTimeLevel(Level.ALL);
		logger.info("This should not have a date stamp");
		
		LoggerManager.getInstance().getFormatter().setDateTimeLevel(Level.INFO);
		logger.info("This should have a date stamp");
				
	}
	
	@Test
	public void testRemovableLevel() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setLevelLevel(Level.ALL);
		logger.info("This should not have a level stamp");
		
		LoggerManager.getInstance().getFormatter().setLevelLevel(Level.OFF);
		logger.info("This should have a level stamp");
				
	}
	
	@Test
	public void testRemovableLoggerName() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setLoggerNameLevel(Level.ALL);
		logger.info("This should not have a name stamp");
		
		LoggerManager.getInstance().getFormatter().setLoggerNameLevel(Level.CONFIG);
		logger.info("This should have a name stamp");
		
	}
	
	@Test
	public void testRemovableSimpleClassName() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setClassNameLevel(Level.ALL);
		logger.info("This should not have simple class stamp");
		
		LoggerManager.getInstance().getFormatter().setClassNameLevel(Level.FINER);
		logger.info("This should have a simple class stamp");
				
	}
	
	@Test
	public void testRemovableVerboseClassName() {
		resetLoggerFinest();
		System.out.println("");
		LoggerManager.getInstance().getFormatter().setUseClassSimpleName(false);
		
		LoggerManager.getInstance().getFormatter().setClassNameLevel(Level.ALL);
		logger.info("This should not have a verbose class stamp");
		
		LoggerManager.getInstance().getFormatter().setClassNameLevel(Level.FINER);
		logger.info("This should have a verbose class stamp");
				
		LoggerManager.getInstance().getFormatter().setUseClassSimpleName(false);
	}
	
	@Test
	public void testRemovableMethodName() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setMethodNameLevel(Level.ALL);
		logger.info("This should not have a method stamp");
		
		LoggerManager.getInstance().getFormatter().setMethodNameLevel(Level.FINER);
		logger.info("This should have a method stamp");
				
	}
	
	@Test
	public void testRemovableThreadID() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setThreadIDLevel(Level.ALL);
		logger.info("This should not have a date stamp");
		
		LoggerManager.getInstance().getFormatter().setThreadIDLevel(Level.FINEST);
		logger.info("This should have a date stamp");
				
	}
	
	@Test
	public void testRemovableDelimiters() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setPreDelim("");
		LoggerManager.getInstance().getFormatter().setPostDelim("");
		
		logger.info("This should not have delimiters");
		
		LoggerManager.getInstance().getFormatter().setPreDelim("[");
		LoggerManager.getInstance().getFormatter().setPostDelim("] ");
		
		logger.info("This should have delimiters");
				
	}
	
	@Test
	public void testRemovableThread() {
		resetLoggerFinest();
		System.out.println("");
		
		LoggerManager.getInstance().getFormatter().setThreadIDLevel(Level.ALL);
		logger.info("This should not have a thread stamp");
		
		LoggerManager.getInstance().getFormatter().setThreadIDLevel(Level.FINER);
		logger.info("This should have a thread stamp");
				
	}
	
	@Test
	public void testConsistentLengthNoDate() {
		resetLoggerFinest();
		LoggerManager.getInstance().getFormatter().setDateTimeLevel(Level.ALL);
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.INFO);
		LoggerManager.getInstance().getFormatter().setPadLogLevel(true);
		System.out.println("");
		logger.info("Testing info for size");
		logger.warning("Testing warning for size");
		logger.severe("Testing severe for size");
		LoggerManager.getInstance().getFormatter().setPadLogLevel(false);
		LoggerManager.getInstance().getFormatter().setDateTimeLevel(Level.INFO);

	}
	
	@Test
	public void testConsistentLengthDate() {
		resetLoggerFinest();
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.INFO);
		LoggerManager.getInstance().getFormatter().setPadLogLevel(true);
		System.out.println("");
		logger.info("Testing info for size");
		logger.warning("Testing warning for size");
		logger.severe("Testing severe for size");
		LoggerManager.getInstance().getFormatter().setPadLogLevel(false);
	}
	
	@Test
	public void testDefaults() {
		logger = LoggerManager.getInstance().getLogger(LoggerTest.class.getName());
		logger.info("INFO Test");
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.WARNING);
		logger.warning("WARNING test");
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.FINE);
		logger.fine("FINE test");
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.FINEST);
		logger.finest("FINEST test");
		LoggerManager.getInstance().setGlobalLoggingLevel(Level.ALL);
		logger.info("ALL test");
	}

}
