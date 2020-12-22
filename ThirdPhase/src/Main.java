
import view.View;

import javax.swing.*;

/**
 * this class is for executing the app.
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class Main {
    public static void main(String[]args)  {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // not worth my time
            }
        }
        //new MainFrame();
        View v = new View();
        Controller c = new Controller(v);
        c.initController();
    }
}
