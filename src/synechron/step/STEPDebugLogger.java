package synechron.step;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class STEPDebugLogger {

	public static File frameworkLogFile;
	public static Logger logger;
	public static FileHandler fileHandler;
	public STEPDebugLogger (String frameworkLocation) {
		try {
			File logFile = new File(frameworkLocation + "\\output\\Logs\\debugLog.xml");
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fileHandler = new FileHandler(frameworkLocation + "\\output\\Logs\\debugLog.xml");
			Logger.getLogger("").setLevel(Level.ALL);
			Logger.getLogger("").addHandler(fileHandler);
			logger = Logger.getLogger("STEP Logger");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Logger getLogger() {
		return logger;
	}
}
