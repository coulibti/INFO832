package discretebehaviorsimulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Contains methods that transform a value in an other type
 * @author Flavien Vernier
 */
public class LogFormatter  extends Formatter {
	/**
	 * Transform a LogRecord to String
	 * @param rec LogRecord The log record to transform
	 * @return String The log record in String format
	 */
	public String format(LogRecord rec) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(calcDate(rec.getMillis()));
		buf.append(": ");
		buf.append(rec.getLevel());
		buf.append(System.getProperty("line.separator"));
		buf.append(formatMessage(rec));
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}
	/**
	 * Transform a number of milliseconds to date
	 * @param millisecs long Number of milliseconds
	 * @return String The input in date
	 */
	private String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
	  }

	/**
	 * Getter of the head
	 * (This method is called just after the handler using this formatter is created)
	 * @param h Handler The handle
	 * @return String Empty string
	 */
	 public String getHead(Handler h) {
		 @Override
		return "";
	 }
	  
	/**
	 * Getter of the tail
	 * (This method is called just after the handler using this formatter is created)
	 * @param h Handler The handle
	 * @return String Empty string
	 */
	 public String getTail(Handler h) {
	 	return "";
	 }

}
