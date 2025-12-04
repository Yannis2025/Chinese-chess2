package xiangqi;
import xiangqi.ui.Game.StartGameFrame;
import xiangqi.ui.Login.LoginFrame;

import javax.swing.*;

public class XiangqiApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            StartGameFrame startFrame = new StartGameFrame();
            startFrame.showFrame();
        });
    }
}
