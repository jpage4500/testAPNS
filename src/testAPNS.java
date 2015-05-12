import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;

public class testAPNS {

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Send test APNS (push) messages to iOS or OSX devices");
            System.out.println("usage: testAPNS {production|sandbox} <certificate.p12> <cert password> <device token> <JSON payload file>");
            System.out.println("- {production|sandbox} - APNS environment to use; must match the .p12 certificate to work!");
            System.out.println("- <certificate.p12> - path to .p12 certificate file; generate this at developer.apple.com");
            System.out.println("- <cert password> - .p12 certificate password");
            System.out.println("- <device token> - APNS token given to device after registering for APNS");
            System.out.println("- <JSON payload file> - path to file containing JSON payload");
            System.out.println("eg: java testAPNS sandbox certificate.p12 test ba2856ca792191957f8a9239cf013 apns-test-1.txt");
            System.exit(0);
        }

        String env = args[0];
        String certName = args[1];
        String certPass = args[2];
        String token = args[3];
        String jsonName = args[4];

        if (!env.equals("production") && !env.equals("sandbox")) {
            System.out.println("bad environment: " + env + ". use 'production' or 'sandbox'");
            System.exit(0);
        } else if (!new File(certName).exists()) {
            System.out.println("file: " + certName + " does not exist!");
            System.exit(0);
        } else if (!new File(jsonName).exists()) {
            System.out.println("file: " + jsonName + " does not exist!");
            System.exit(0);
        }

        ApnsServiceBuilder serviceBuilder = APNS.newService().withCert(certName, certPass);
        if (env.equals("production")) {
            serviceBuilder = serviceBuilder.withProductionDestination();
        } else {
            serviceBuilder = serviceBuilder.withSandboxDestination();
        }

        ApnsService service = serviceBuilder.build();

        String json = readFile(jsonName);
        //String jsonCompressed = json.replaceAll("\\s+", "");
        System.out.println("sending APNS (" + json.length() + " bytes) to device: " + token);
        System.out.println(json);

        service.push(token, json);
    }

    static String readFile(String path) {
        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("error reading file: " + path + ": " + e.getMessage());
            System.exit(0);
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
