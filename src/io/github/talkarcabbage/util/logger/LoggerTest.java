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

	@Test
	public void test() {
		logger = LoggerManager.getLogger(LoggerTest.class.getName());
		logger.info("This is a test of INFO at INFO");
		assertEquals(logger, LoggerManager.getLogger(LoggerTest.class.getName()));
		LoggerManager.setGlobalLoggingLevel(Level.ALL);
		assertEquals(LoggerManager.getGlobalLoggerLevel(), Level.ALL);
		assertEquals(logger.getLevel(), Level.ALL);
		logger.info("This is a test of INFO at ALL");
		LoggerManager.setGlobalLoggingLevel(Level.INFO);
	}

}
