package org.corneliudascalu.intellijdecrypt;

import com.intellij.ide.passwordSafe.impl.providers.EncryptionUtil;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author Corneliu Dascalu <corneliu.dascalu@osf-global.com>
 */
public class Main {
    private static boolean verbose = false;

    public static void main(String[] args) throws Exception {
        File fXmlFile = null;
        String password = null;
        if (args.length == 0 || args[0].equalsIgnoreCase("-help")) {
            System.out.println("-help Display this help message.\n-p <password> Specify the password\n-f " +
                    "<path> (Optional) specify the path to the security.xml file. Default to ~/" +
                    ".IdeaIC13/config/options/security.xml\n-v (Optional) print verbose logging and errors");
            System.exit(0);
        }
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equalsIgnoreCase("-f")) {
                fXmlFile = new File(args[i + 1]);
            } else if (args[i].equalsIgnoreCase("-p")) {
                password = args[i + 1];
            } else if (args[i].equalsIgnoreCase("-v")) {
                verbose = true;
            }
        }
        if (password == null) {
            System.out.println("Please specify the password");
            System.exit(1);
        }
        if (fXmlFile == null) {
            File homedir = new File(System.getProperty("user.home"));
            fXmlFile = new File(homedir, ".IdeaIC13/config/options/security.xml");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        NodeList passwords = doc.getElementsByTagName("option");
        if (passwords.getLength() > 1) {
            System.out.println("XML file OK");
            for (int i = 1; i < passwords.getLength(); i++) {
                String decrypted = null;

                try {
                    decrypted = EncryptionUtil.decryptText(EncryptionUtil.genPasswordKey(password),
                            fromHex(passwords.item(i).getAttributes().item(0).getNodeValue()));
                    System.out.println("Value:" + passwords.item(i).getAttributes().item(0).getNodeValue());
                    System.out.println("Decrypted Password:" +  decrypted);
                } catch (Exception e) {
                    if(verbose) {
                        try {
                            System.out.println("Value:" + passwords.item(i).getAttributes().item(0).getNodeValue());
                        } catch (Exception e1) {
                            e1.printStackTrace(System.out);
                        }
                        System.out.println("Error:Not a password");
                        e.printStackTrace(System.out);
                    }
                }
            }
        } else {
            System.out.println("Unexpected xml file structure or no passwords stored");
        }
    }

    private static String toHex(byte[] bytes) {
        return bytes == null ? null : new String(Hex.encodeHex(bytes));
    }

    private static byte[] fromHex(String hex) throws DecoderException {
        return hex == null ? null : Hex.decodeHex(hex.toCharArray());
    }
}
