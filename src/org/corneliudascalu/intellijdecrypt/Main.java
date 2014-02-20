package org.corneliudascalu.intellijdecrypt;

import com.intellij.ide.passwordSafe.impl.providers.EncryptionUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * @author Corneliu Dascalu <corneliu.dascalu@osf-global.com>
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println(EncryptionUtil.decryptText(EncryptionUtil.genPasswordKey("password"),
                    fromHex("a392f09d21357ac48fd5987a95de926f")));
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
