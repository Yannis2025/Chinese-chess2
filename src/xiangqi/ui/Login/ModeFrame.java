/*
 * Created by JFormDesigner on Tue Dec 02 11:12:47 CST 2025
 */

package xiangqi.ui.Login;

import java.awt.*;
import javax.swing.*;

/**
 * @author yanni
 */
public class ModeFrame extends JFrame {
    public ModeFrame() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - 苏云翼
        label3 = new JLabel();
        SingleModeButton = new JButton();
        PKModeButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label3 ----
        label3.setText("\u6a21\u5f0f\u9009\u62e9");
        label3.setFont(new Font("\u6977\u4f53", Font.PLAIN, 22));
        contentPane.add(label3);
        label3.setBounds(145, 30, 115, 19);

        //---- SingleModeButton ----
        SingleModeButton.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        SingleModeButton.setText("\u5355\u4eba\u6a21\u5f0f");
        contentPane.add(SingleModeButton);
        SingleModeButton.setBounds(110, 90, 160, SingleModeButton.getPreferredSize().height);

        //---- PKModeButton ----
        PKModeButton.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        PKModeButton.setText("\u53cc\u4eba\u6a21\u5f0f");
        contentPane.add(PKModeButton);
        PKModeButton.setBounds(110, 155, 160, 30);

        contentPane.setPreferredSize(new Dimension(385, 265));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - 苏云翼
    private JLabel label3;
    private JButton SingleModeButton;
    private JButton PKModeButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
