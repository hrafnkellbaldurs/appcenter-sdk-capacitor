package com.getcapacitor.plugin.appcenter.crashes;

import com.getcapacitor.JSObject;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.crashes.Crashes;

import java.security.InvalidParameterException;

public class CrashesBase {
  /**
   * Constant for DO NOT SEND crash report.
   */
  private static final int DONT_SEND = 0;

  /**
   * Constant for SEND crash report.
   */
  private static final int SEND = 1;

  /**
   * Constant for ALWAYS SEND crash reports.
   */
  private static final int ALWAYS_SEND = 2;

  public void enable(boolean enabled) {
    Crashes.setEnabled(enabled).get();
  }

  public boolean isEnabled() {
    return Crashes.isEnabled().get();
  }

  public void start() {
    if (AppCenter.isConfigured()) {
      AppCenter.start(Crashes.class);
    }      
  }

  public void generateTestCrash() {
    Crashes.generateTestCrash();
  }

  public boolean hasReceivedMemoryWarningInLastSession() {
    return Crashes.hasReceivedMemoryWarningInLastSession().get();
  }

  public boolean hasCrashedInLastSession() {
    return Crashes.hasCrashedInLastSession().get();
  }

  public JSObject lastSessionCrashReport() {
    return CrashesUtil.convertReportToJs(Crashes.getLastSessionCrashReport().get());
  }

  public void notifyUserConfirmation(int userConfirmation) throws InvalidParameterException {
    switch (userConfirmation) {
      case DONT_SEND:
        userConfirmation = Crashes.DONT_SEND;
        break;

      case SEND:
        userConfirmation = Crashes.SEND;
        break;

      case ALWAYS_SEND:
        userConfirmation = Crashes.ALWAYS_SEND;
        break;

      default:
        throw new InvalidParameterException("User confirmation value of " + userConfirmation + " not recognized");
    }

    Crashes.notifyUserConfirmation(userConfirmation);
  }
}
