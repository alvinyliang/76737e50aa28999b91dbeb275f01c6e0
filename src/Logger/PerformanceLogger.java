package Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

public class PerformanceLogger {
//	static private FileHandler fileHandler;
//    static private SimpleFormatter formatter;
	static private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	static public void log(String pattern, long time, int numQ, String type, String config, String auto_cmp, String title){

//		writeLog(pattern, 
//				setMessage(time, numQ, type, config, auto_cmp, title));
		
		
//	    try {
//	    	BufferedWriter writer = new BufferedWriter(new FileWriter(pattern, true));
//			writer.append("The first line");
//			writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
	}
	
    static synchronized private void writeLog(String pattern, String[] msgs){
    	try{
	                // suppress the logging output to the console
//	        Logger rootLogger = Logger.getLogger("");
//	        Handler[] handlers = rootLogger.getHandlers();
//            if (handlers[0] instanceof ConsoleHandler) {
//            	rootLogger.removeHandler(handlers[0]);
//            }
            
//    		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            
//            
//            Handler consoleHandler = new ConsoleHandler();
//            logger.addHandler(consoleHandler);
    		SimpleFormatter formatter = new SimpleFormatter();
    		
        	logger.setLevel(Level.INFO);
//        	System.out.println("emmmm filehandler");
        	
        	
        	FileHandler fileHandler = new FileHandler(pattern, true);
        	fileHandler.setFormatter(formatter);
//        	logger.addHandler(fileHandler);
//            
	        
	        
	        
	        
	        String msg = "";
	        for (String m: msgs){
	        	msg += m + "\t";
	        	
	        }
	        
	        logger.info(msg);
	        fileHandler.close();
	        
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
