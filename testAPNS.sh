#!/bin/sh

cd "$(dirname "$0")"

# Send test APNS (push) messages to iOS or OSX devices
# usage: testAPNS {production|sandbox} <certificate.p12> <cert password> <device token> <JSON payload file>
# - {production|sandbox} - APNS environment to use; must match the .p12 certificate to work!
# - <certificate.p12> - path to .p12 certificate file; generate this at developer.apple.com
# - <cert password> - .p12 certificate password
# - <device token> - APNS token given to device after registering for APNS
# - <JSON payload file> - path to file containing JSON payload

java -classpath bin:lib/apns-1.0.0.Beta7-SNAPSHOT-jar-with-dependencies.jar testAPNS $*