package com.getcapacitor.plugin.appcenter.crashes;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.microsoft.appcenter.reactnative.shared.AppCenterReactNativeShared;

@CapacitorPlugin(name = "Crashes")
public class CrashesPlugin extends Plugin {

    private final CrashesBase implementation = new CrashesBase();

    @Override
    public void load() {
        AppCenterReactNativeShared.configureAppCenter(this.getActivity().getApplication());
        implementation.start();
    }

    @PluginMethod
    public void trackError(PluginCall call) {
        JSObject error = call.getObject("error");
        JSObject properties = call.getObject("properties");
        JSArray attachments = call.getArray("attachments");

        String errorReportId;
        try {
            errorReportId = implementation.trackError(error, properties, attachments);
        } catch (java.lang.Exception e) {
            call.reject(e.getMessage());
            return;
        }

        JSObject ret = new JSObject();
        ret.put("errorReportId", errorReportId);
        call.resolve(ret);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void setEnable(PluginCall call) {
        implementation.enable(call.getBoolean("enable", false));
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
        call.unimplemented("Not yet implemented on Android.");
    }
    
}
