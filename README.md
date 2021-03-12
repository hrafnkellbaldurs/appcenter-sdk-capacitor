# App Center SDK for Capacitor

> ### :rotating_light: Project is in active development and not ready to be used. Check back later. :rotating_light:

App Center is mission control for mobile apps. Get faster release cycles, higher-quality apps, and the insights to build what users want.

## Plugins

The Capacitor App Center SDK consists of a several plugins so you can use any or all of the supported App Center services:

| Package | Source | Version |
| --- | --- | --- |
| [`@capacitor-community/appcenter-shared`](https://capacitorjs.com/docs/v3/apis/action-sheet) | [`./appcenter-shared`](./appcenter-shared) | [![npm badge](https://img.shields.io/npm/v/@capacitor-community/appcenter-shared?style=flat-square)](https://www.npmjs.com/package/@capacitor-community/appcenter-shared)
| [`@capacitor-community/appcenter-analytics`](https://capacitorjs.com/docs/v3/apis/appcenter-analytics) | [`./appcenter-analytics`](./appcenter-analytics) | [![npm badge](https://img.shields.io/npm/v/@capacitor-community/appcenter-analytics?style=flat-square)](https://www.npmjs.com/package/@capacitor-community/appcenter-analytics)
| [`@capacitor-community/appcenter-crashes`](https://capacitorjs.com/docs/v3/apis/appcenter-crashes) | [`./appcenter-crashes`](./appcenter-crashes) | [![npm badge](https://img.shields.io/npm/v/@capacitor-community/appcenter-crashes?style=flat-square)](https://www.npmjs.com/package/@capacitor-community/appcenter-crashes)

## Example Mobile App

You can get familiar with SDK quickly by cloning this repository and running sample app in `example` directory. For further instructions please refer to [example's README](example/README.md)

## 1. Installation

Add the App Center plugin(s) that fit your needs directly from the CLI:

```bash
npm i @capacitor-community/appcenter-analytics @capacitor-community/appcenter-shared
npx cap sync
```

This will install two of the plugins available today.

## 2. Link the SDK

To get it working in your app you will need to add some configuration values to your app configuration in `capacitor.config.json` file. See list of available parameters below

- `AppSecret` - _(required)_ App secret which enables App Center to map this app to the right user account

Example:

```json
{
  ...
  "plugins": {
    "Analytics": {
      "iosAppSecret": "0000-0000-0000-0000-000000000000",
      "androidAppSecret": "0000-0000-0000-0000-000000000000",
      "transmissionInterval": "10",
      "enableInJs": false
    }
  }
}
```

Notice that it's likely that Android and iOS platforms will be associated with different applications on App Center portal so you would need to add this preference twice - one for Android and another for iOS.

### Config Options

These options can be used in `capacitor.config.json`

| name                  | type              | default                              |
| --------------------- | ----------------- | ------------------------------------ |
| iosAppSecret          | `string`          | `"0000-0000-0000-0000-000000000000"` |
| androidAppSecret      | `string`          | `"0000-0000-0000-0000-000000000000"` |
| transmissionInterval  | `string`          | `"3"`                                |
| enableInJs            | `boolean`         | `false`                              |

## 3. Configure Preferences
Coming Soon...