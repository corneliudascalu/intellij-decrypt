package org.corneliudascalu.intellijdecrypt;

import com.intellij.ide.passwordSafe.impl.providers.EncryptionUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @author Corneliu Dascalu <corneliu.dascalu@osf-global.com>
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            File fXmlFile = null;
            String password = null;
            if (args.length == 0 || args[0].equalsIgnoreCase("-help")) {
                System.out.println("-help Display this help message.\n-p <password> Specify the password\n-f " +
                        "<path> (Optional) specify the path to the security.xml file. Default to ~/" +
                        ".IdeaIC13/config/options/security.xml");
                System.exit(0);
            }
            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equalsIgnoreCase("-f")) {
                    fXmlFile = new File(args[i + 1]);
                } else if (args[i].equalsIgnoreCase("-p")) {
                    password = args[i + 1];
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
                    System.out.println(passwords.item(i).getAttributes().item(0).getNodeValue());
                    try {
                        System.out.println(EncryptionUtil.decryptText(EncryptionUtil.genPasswordKey(password),
                                fromHex(passwords.item(i).getAttributes().item(0).getNodeValue())));
                    } catch (IllegalStateException e) {
                        System.out.println("Not a password");
                    }
                }
            } else {
                System.out.println("Unexpected xml file structure or no passwords stored");
            }
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    private static String toHex(byte[] bytes) {
        return bytes == null ? null : new String(Hex.encodeHex(bytes));
    }

    private static byte[] fromHex(String hex) throws DecoderException {
        return hex == null ? null : Hex.decodeHex(hex.toCharArray());
    }
}
