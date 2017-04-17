# Talkar's Logger Library
This is a simple Java logging library I created while working on another project and exported it into an independent library. Its purpose is very simplified and almost-configuration-free logging aimed at smaller projects. It is designed as a "drop and go" solution for logging, requiring very little to no configuration to use.

A more detailed usage guide, as well as additional features, will be added soon.

# Usage
* Include it in your build path. 

* To get a logger, call LoggerManager.getLogger("name") where "name" can be anything but is encouraged to be unique per class. 

* The logger level can be configured via LoggerManager.setGlobalLoggingLevel(level) where level refers to a Java logger Level enum entry.

It is highly recommended not to use the global logger/empty string logger as it may cause duplicate, inconsistently-formatted logging.

My personal recommendation for smaller projects would be to use the class's simple name or a manual string, though larger projects may prefer to use className() instead. This method returns a java.util.logging.Logger preconfigured to use System.out and System.err depending on the log level and with the specified log level from the LoggerManager's global logging level. This method will return the same logger instance if the same string is used to request a logger.
