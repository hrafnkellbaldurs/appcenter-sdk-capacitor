import Foundation
import Capacitor
import AppCenter
import AppCenterCrashes

enum CrashesError: Error {
    case unknownUserConfirmationError(userConfirmation: Int?)
}

@objc public class AppCenterCrashesBase: NSObject {
    // Constant for DO NOT SEND crash report.
    static let DONT_SEND: Int = 0
    // Constant for SEND crash report.
    static let SEND: Int = 1
    // Constant for ALWAS SEND crash reports.
    static let ALWAYS_SEND: Int = 2
    
   
    public func enable(_ flag: Bool) {
        Crashes.enabled = flag
    }
   
    public func isEnabled() -> Bool {
        return Crashes.enabled
    }
    
    public func start() {
        AppCenter.startService(Crashes.self)
    }
    
    public func generateTestCrash() {
        Crashes.generateTestCrash()
    }
    
    public func hasReceivedMemoryWarningInLastSession() -> Bool {
        return Crashes.hasReceivedMemoryWarningInLastSession
    }
    
    public func hasCrashedInLastSession() -> Bool {
        return Crashes.hasCrashedInLastSession
    }
    
    public func lastSessionCrashReport() -> Dictionary<String, Any>? {
        return CrashesUtil.convertReportToJs(report: Crashes.lastSessionCrashReport)
    }
    
    public func notifyUserConfirmation(_ userConfirmation: Int?) throws {
        let value: UserConfirmation
        
        switch(userConfirmation) {
        case AppCenterCrashesBase.DONT_SEND:
            value = UserConfirmation.dontSend
            break
            
        case AppCenterCrashesBase.SEND:
            value = UserConfirmation.send
            break
        
        case AppCenterCrashesBase.ALWAYS_SEND:
            value = UserConfirmation.always
            break
        
        default:
            throw CrashesError.unknownUserConfirmationError(userConfirmation: userConfirmation)
        }
        
        Crashes.notify(with: value)
    }
}
