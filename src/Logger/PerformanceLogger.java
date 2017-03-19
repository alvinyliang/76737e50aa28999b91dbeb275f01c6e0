package Logger;

import java.io.IOException;
import java.util.logging.*;

public class PerformanceLogger {
//	static private FileHandler fileHandler;
//    static private SimpleFormatter formatter;
	
	static public void log(String pattern, long time, int numQ, String type, String config, String auto_cmp, String title){
		writeLog(pattern, 
				setMessage(time, numQ, type, config, auto_cmp, title));
	}
	
    static private void writeLog(String pattern, String[] msgs){
    	try{
	        // get the global logger to configure it
	        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	        // suppress the logging output to the console
//	        Logger rootLogger = Logger.getLogger("");
//	        Handler[] handlers = rootLogger.getHandlers();
//            if (handlers[0] instanceof ConsoleHandler) {
//            	rootLogger.removeHandler(handlers[0]);
//            }
            
            
            logger.setLevel(Level.INFO);
//            
//            Handler consoleHandler = new ConsoleHandler();
//            logger.addHandler(consoleHandler);
            FileHandler fileHandler = new FileHandler(pattern, true);
	        System.out.println();
            SimpleFormatter formatter = new SimpleFormatter();
	        fileHandler.setFormatter(formatter);
	        logger.addHandler(fileHandler);
	        
	        String msg = "";
	        for (String m: msgs){
	        	msg += m + "\t";
	        	
	        }
	        
	        logger.info(msg);
	       
    	} catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
    	}
    }
    
    static private String[] setMessage(long time, int numQ, String type, String config, String auto_cmp, String title){
		String[] result = new String[6];
		// has to follow the format <type>:<
		result[0] = "type:"+type;
		result[1] = "config:"+config;
		result[2] = "auto_cmp:"+ auto_cmp;
		result[3] = "title:"+title;
		result[4] = "numQ:" + Integer.toString(numQ);
		result[5] = "time:" + Double.toString((double) time/1000000);
		
		
		
		return result;
	}
    
    
    
    
//    public static void main(String[] args) {
//    	
//    	PerformanceLogger.log("../../WebContent/logs/TestLog.log", 0, "TEST", "null", 
//    			"", "");
//    }
    
}
