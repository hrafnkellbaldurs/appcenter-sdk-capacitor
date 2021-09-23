package com.getcapacitor.plugin.appcenter.crashes;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.microsoft.appcenter.reactnative.shared.AppCenterReactNativeShared;

import java.security.InvalidParameterException;

@CapacitorPlugin(name = "Crashes")
public class CrashesPlugin extends Plugin {

    private final CrashesBase implementation = new CrashesBase();

    @Override
    public void load() {
        AppCenterReactNativeShared.configureAppCenter(this.getActivity().getApplication());
        implementation.start();
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void setEnable(PluginCall call) {
        Boolean enable = call.getBoolean("enable", false);
        if (enable == null) {
            enable = false;
        }
        implementation.enable(enable);
        call.resolve();
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", implementation.isEnabled());
        call.resolve(ret);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void generateTestCrash(PluginCall call) {
        implementation.generateTestCrash();
        call.resolve();
    }

    @PluginMethod
    public void hasReceivedMemoryWarningInLastSession(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", implementation.hasReceivedMemoryWarningInLastSession());
        call.resolve(ret);
    }

    @PluginMethod
    public void hasCrashedInLastSession(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", implementation.hasCrashedInLastSession());
        call.resolve(ret);
    }

    @PluginMethod
    public void lastSessionCrashReport(PluginCall call) {
        JSObject lastSessionCrashReport = implementation.lastSessionCrashReport();
        if (lastSessionCrashReport == null) {
            call.reject("No crash report available");
            return;
        }
        JSObject ret = new JSObject();
        ret.put("value", lastSessionCrashReport);
        call.resolve(ret);
    }

    @PluginMethod
    public void notifyUserConfirmation(PluginCall call) {
        Integer userConfirmation = call.getInt("userConfirmation");
        if (userConfirmation == null) {
            call.reject("userConfirmation should be an integer, not null");
            return;
        }
        try {
            implementation.notifyUserConfirmation(userConfirmation);
        } catch (InvalidParameterException e) {
            call.reject(e.getMessage());
            return;
        } catch (Exception e) {
            call.reject("Unexpected error: " + e.getMessage());
            return;
        }
        call.resolve();
    }
}
