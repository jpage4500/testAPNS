# testAPNS
java app and shell scripts to quickly test sending APNS messages to iOS or OSX devices. 

Uses the latest version of notnoop APNS library https://github.com/notnoop/java-apns

#usage
````
usage: testAPNS {production|sandbox} <certificate.p12> <cert password> <device token> <JSON payload file>
- {production|sandbox} - APNS environment to use; must match the .p12 certificate to work!
- <certificate.p12> - path to .p12 certificate file; generate this at developer.apple.com
- <cert password> - .p12 certificate password
- <device token> - APNS token given to device after registering for APNS
- <JSON payload file> - path to file containing JSON payload
````

Example:
````
./testAPNS.sh sandbox my-sandbox-apns.p12 test 2856ca792191957f8a9239cf013a89373dcf7bb2f6a9ef41ca6af02fca88b4 payload-1.txt
````

Output:
````
sending APNS (120 bytes) to device: 2856ca792191957f8a9239cf013a89373dcf7bb2f6a9ef41ca6af02fca88b4
{
    "aps": {
        "alert": "message",
        "badge": 99,
        "sound": "default"
    },
    "myField": 54758
}
````

# Notes

- Your iOS or OSX build will use the sandbox APNS server if you build with the 'development' certificate
http://stackoverflow.com/questions/1943722/iphone-apns-device-tokens-in-sandbox-vs-production

- To generate a .p12 certificate here's what I did:
  - login to https://developer.apple.com/ and download the provisioning certificate (.cer)
  - import into keychain
  - select both imported items and 'export 2 items..' and save as .p12 file
  - note the password you use to export the .p12 file!
