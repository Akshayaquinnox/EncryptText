package encrypttextplugin;

import org.eclipse.swt.SWT;  
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptTextPlugin {

    private static final String AES = "AES";
    
    private static final byte[] SECRET_KEY = "1234567890123456".getBytes(); 
    
    public static void main(String[] args) {
    	
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Encrypt Text Plugin");
        shell.setSize(400, 400);
        shell.setLayout(new GridLayout(2, false));

        Label inputLabel = new Label(shell, 0);
        inputLabel.setText("Enter Text:");

        Text textField = new Text(shell, 0);

        Button encryptButton = new Button(shell, SWT.PUSH);
        encryptButton.setText("Encrypt");
 
        Label resultLabel = new Label(shell, SWT.WRAP);
        resultLabel.setText("CipherText");
        resultLabel.setSize(500, 500);

        encryptButton.addListener(SWT.Selection, e -> {
            String input = textField.getText();
            if (!input.isEmpty()) {
                try {
                    String encryptedText = encrypt(input, SECRET_KEY);
                    resultLabel.setText(encryptedText);
                } catch (Exception ex) {
                    resultLabel.setText("Encryption Error: " + ex.getMessage());
                }
            } else {
                resultLabel.setText("Please enter text to encrypt.");
            }
        });

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }

    private static String encrypt(String data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
