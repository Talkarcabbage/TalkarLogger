# Talkar's Logger Library
This is a simple Java logging wrapper I created while working on another project and exported it into an independent library. Its purpose is very simplified and almost-configuration-free logging aimed at smaller projects. It is designed as a "drop and go" solution for logging, requiring very little to no configuration to use, but offering moderate customizability for those that desire it. This library works via a Logger manager that creates and manages Logger instances for the application. 

A more detailed usage guide, as well as additional features, may be added eventually.

# Usage
* Include it in your build path. 

* To get a logger, call `LoggerManager.getInstance().getLogger("name")` where "name" can be anything but is encouraged to be unique per class. 

* The logger level can be configured via `LoggerManager.getInstance().setGlobalLoggingLevel(level)` where level refers to a Java logger Level enum entry.

* The logger's formatting is moderately customizable but preconfigured with a general purpose format. Formatting customization can be done by changing the included formatter, which is shared between loggers. More information is available in the javadocs.

# Formatting
The header information included at and below (e.g. below FINE would be FINER, FINEST, and ALL) can be configured via the formatter returned by `LoggerManager.getInstance().getFormatter()`
The pre and post delimiters can be changed (`"[" and "] "` respectively)
The date/time format can be changed.
The inclusion of the date/time, record (eg `logger.info("Something")`) Level, logger name, class name (or simple class name), method name, and thread ID can be enabled or disabled at and below any level individually. Their inclusion is then based on the global logging level.
If the global logging level is set to ALL, all information will be printed.

It is highly recommended not to use the global logger/empty string logger as it may cause duplicate, inconsistently-formatted logging. This may change with later versions.

My personal recommendation for smaller projects would be to use the class's simple name or a manually chosen string, though larger projects may prefer to use `className()` or an alternative identifier instead. This method returns a java.util.logging.Logger preconfigured to use System.out and System.err depending on the log level and with the specified log level from the LoggerManager's global logging level. This method will return the same logger instance if the same string is used to request a logger.
