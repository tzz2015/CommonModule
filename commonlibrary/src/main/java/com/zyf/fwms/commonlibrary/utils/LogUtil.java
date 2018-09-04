package com.zyf.fwms.commonlibrary.utils;








import android.util.Log;

import com.orhanobut.logger.Logger;

public class LogUtil {

	private final static String TAG = "TCircle";

	//public static int logLevel = Log.ASSERT;

	public static int logLevel = Logger.ERROR;
//	 private static boolean logFlag = true;
	private static LogUtil logger = new LogUtil();;

	public static LogUtil getInstance() {
		return logger;
	}

	private LogUtil() {

	}

	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			String className = st.getClassName();
			int indexOf = className.lastIndexOf(".");
			return "[类名:" + st.getClassName().substring(indexOf+1,className.length()) + "--方法名:" + st.getMethodName()
					+ "第" + st.getLineNumber() + "行 ]";
		}
		return null;
	}

	public void i(Object str) {
		// if (!AppConf.DEBUG)
		// return;
		if (logLevel <= Logger.INFO) {
			String name = getFunctionName();
			if (name != null) {
				Logger.i(name + " - " + str);
			} else {
				Logger.i(str.toString());
			}
		}
	}

	public void v(Object str) {
		// if (!AppConfig.DEBUG)
		// return;
		if (logLevel <= Logger.VERBOSE) {
			String name = getFunctionName();
			if (name != null) {
				Logger.v(name + " - " + str);
			} else {
				Logger.v(str.toString());
			}
		}
	}

	public void w(Object str) {
		// if (!AppConfig.DEBUG)
		// return;
		if (logLevel <= Logger.WARN) {
			String name = getFunctionName();
			if (name != null) {
				Logger.w(name + " - " + str);
			} else {
				Logger.w( str.toString());
			}
		}
	}

	public void e(Object str) {
		// if (!AppConfig.DEBUG)
		// return;
		if (logLevel <= Logger.ERROR) {
			String name = getFunctionName();
			if (name != null) {
				Logger.e(name + " - " + str);
			} else {
				Logger.e( str.toString());
			}
		}
	}

	public void e(Exception ex) {
		// if (!AppConfig.DEBUG)
		// return;
		if (logLevel <= Logger.ERROR) {
			Logger.e( "error", ex);
		}
	}

	public void d(Object str) {
		// if (!AppConfig.DEBUG)
		// return;
		if (logLevel <= Logger.DEBUG) {
			String name = getFunctionName();
			if (name != null) {
				Logger.d( name + " - " + str);
			} else {
				Logger.d( str.toString());
			}
		}
	}

}
