package xiangqi;

import xiangqi.ui.Game.GameFrame;
import javax.swing.*;

/**
 * 用于测试的类，直接打开象棋界面，无需登录。
 * 可以快速启动游戏进行功能测试。
 */
public class OpenChessFrame {
    public static void main(String[] args) {
        // 使用 SwingUtilities 确保线程安全
        SwingUtilities.invokeLater(() -> {
            // 直接创建游戏界面，使用默认设置
            // 参数说明：标题，是否登录（false 表示游客），用户名（"Guest"），红方昵称，黑方昵称
            GameFrame gameFrame = new GameFrame(
                    "中国象棋测试版",
                    false,           // 非登录状态
                    "Guest",         // 游客身份
                    "测试红方",      // 红方昵称
                    "测试黑方"       // 黑方昵称
            );

            // 显示游戏窗口
            gameFrame.setVisible(true);

        });
    }
}