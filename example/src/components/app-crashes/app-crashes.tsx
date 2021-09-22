import { Component, State, h } from '@stencil/core';
import { modalController, ToggleChangeEventDetail, SelectChangeEventDetail } from '@ionic/core';
import Crashes, { ErrorReport, UserConfirmation } from '@capacitor-community/appcenter-crashes';

import { ErrorReportItem } from './error-report-items-modal';

@Component({
  tag: 'app-crashes',
  styleUrl: 'app-crashes.css',
})
export class AppCrashes {
  /* Flag to toggle entire Crashes service */
  @State() enabled: boolean = false
  @State() memoryWarning: boolean = false
  @State() hasCrashed: boolean = false
  @State() crashReport: ErrorReport
  @State() userConfirmation?: UserConfirmation = null

  constructor() {
    this.toggleCrashes = this.toggleCrashes.bind(this);
    this.crashApp = this.crashApp.bind(this);
  }

  async componentWillLoad() {
    try {
      const { value: crashesEnabled } = await Crashes.isEnabled();
      const { value: memoryWarning } = await Crashes.hasReceivedMemoryWarningInLastSession();
      const { value: hasCrashed } = await Crashes.hasCrashedInLastSession();
      const { value: crashReport } = await Crashes.lastSessionCrashReport();

      this.enabled = crashesEnabled
      this.memoryWarning = memoryWarning
      this.hasCrashed = hasCrashed
      this.crashReport = crashReport;
    } catch (error) {
      console.error(error)
    }
  }

  async toggleCrashes(e: CustomEvent<ToggleChangeEventDetail>) {
    try {
      await Crashes.setEnabled({enable: e.detail.checked});
      this.enabled = e.detail.checked
    } catch (error) {
      this.enabled = false
      console.error(error)
    }
  }

  async crashApp() {
    try {
      await Crashes.generateTestCrash()
    } catch (error) {

    }
  }

  async presentErrorReportItemsModal(items: ErrorReportItem[]) {
    const modal = await modalController.create({
      component: 'error-report-items-modal',
      componentProps: { items },
    });
    await modal.present();
  }

  async notifyUserConfirmation(e: CustomEvent<SelectChangeEventDetail>) {
    try {
      const userConfirmation: UserConfirmation = parseInt(e.detail.value);
      await Crashes.notifyUserConfirmation({ userConfirmation });
      this.userConfirmation = userConfirmation;
    } catch (error) {
      this.userConfirmation = null;
      console.error(error);
    }
  }

  render() {
    return [
      <ion-header>
        <ion-toolbar color="primary">
          <ion-buttons slot="start">
            <ion-back-button defaultHref="/" />
          </ion-buttons>
        </ion-toolbar>
      </ion-header>,

      <ion-content class="ion-padding">
        <h3>App Center Crashes</h3>
        <p>App Center Crashes will automatically generate a crash log every time your app crashes.</p>

        <ion-list lines="full" class="ion-no-margin">
          <ion-item>
            <ion-label>Enable Crashes</ion-label>
            <ion-toggle checked={this.enabled} onIonChange={e => this.toggleCrashes(e)} />
          </ion-item>
          <ion-item>
            <ion-label>User confirmation</ion-label>
            <ion-select
              value={this.userConfirmation}
              onIonChange={this.notifyUserConfirmation}
              placeholder="Select.."
              okText="Notify"
            >
              <ion-select-option value={UserConfirmation.DONT_SEND}>Dont send</ion-select-option>
              <ion-select-option value={UserConfirmation.SEND}>Send</ion-select-option>
              <ion-select-option value={UserConfirmation.ALWAYS_SEND}>Always send</ion-select-option>
              <ion-select-option value={-1}>unknown value</ion-select-option>
            </ion-select>
          </ion-item>
          <ion-item>
            <ion-label>Memory Warning</ion-label>
            <ion-note slot="end">{this.memoryWarning.toString()}</ion-note>
          </ion-item>
          <ion-item>
            <ion-label>Crashed Prior</ion-label>
            <ion-note slot="end">{this.hasCrashed.toString()}</ion-note>
          </ion-item>
        </ion-list>

        <section>
          <header>Generate a Test Crash</header>
          <ion-button color="danger" expand="block" onClick={this.crashApp}>Let app crash</ion-button>
        </section>

        <ion-list>
          <ion-list-header lines="full">
            <ion-label>Crash Report</ion-label>
          </ion-list-header>
          { this.crashReport ? Object.keys(this.crashReport).map(key => {
            const value = this.crashReport[key];
            let renderedValue;

            // Extract items we will display in the modal when the item is clicked
            let errorReportModalItems: ErrorReportItem[];
            // If the value is an object, we need to add all key/value items to the errorReportModalItems
            const valueIsObject = value && typeof value === 'object' && value.length === undefined;
            if (valueIsObject) {
              const keys = Object.keys(value);
              renderedValue = `${keys.length} properties`;
              errorReportModalItems = keys.map(valueKey => ({ label: valueKey, value: value[valueKey] }));
            } else {
              // If the value is not an object, add the value as is to the errorReportModalItems
              renderedValue = value;
              errorReportModalItems = [{ label: key, value }];
            }

            return (
              <ion-item detail onClick={() => this.presentErrorReportItemsModal(errorReportModalItems)}>
                <ion-label>
                  <h3>{key}</h3>
                  <p>{renderedValue}</p>
                </ion-label>
              </ion-item>
            );
          }) : null}

        </ion-list>

      </ion-content>,
    ];
  }
}
