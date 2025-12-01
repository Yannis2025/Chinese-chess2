package xiangqi.ui.Game;

import xiangqi.util.SoundManager;
import xiangqi.model.ChessBoardModel;
import xiangqi.model.SaveManager;
import xiangqi.model.AbstractPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

public class GameFrame extends JFrame {
    private ChessBoardModel model;
    private ChessBoardPanel boardPanel;
    private ControlPanel controlPanel;
    private SaveManager saveManager;
    private boolean isLoggedIn;
    private String username;
    private boolean gameEnded = false;
    private SoundManager soundManager;
    private String redNickname = "红方";
    private String blackNickname = "黑方";

    public GameFrame(String title, boolean isLoggedIn, String username, String redNickname, String blackNickname) {
        super(title);
        this.isLoggedIn = isLoggedIn;
        this.username = username;
        this.saveManager = new SaveManager();
        this.controlPanel = new ControlPanel();
        this.soundManager=SoundManager.getInstance();
        // 设置昵称
        this.redNickname = (redNickname != null && !redNickname.trim().isEmpty()) ? redNickname.trim() : "红方";//没有或为空----输入或默认
        this.blackNickname = (blackNickname != null && !blackNickname.trim().isEmpty()) ? blackNickname.trim() : "黑方";
        initializeGame();
        setupUI();
        setupEventListeners();
        setupWindowListener();

        //播放背景音乐:
        soundManager.playBackgroundMusic();
    }
    // 简化构造函数，用于有存档的情况
    public GameFrame(String title, boolean isLoggedIn, String username) {
        this(title, isLoggedIn, username, null, null);
    }
    // 设置昵称
    public void setRedNickname(String nickname) {
        if (nickname != null && !nickname.trim().isEmpty()) {
            this.redNickname = nickname.trim();
            // 这里后续可以在控制面板显示红方昵称
        }
    }

    // 保存游戏（总是包含昵称）
    private boolean saveGame() {
        if (isLoggedIn && !username.equals("Guest")) {
            return saveManager.saveGame(username, model, redNickname, blackNickname);
        }
        return false;
    }
    public void setBlackNickname(String nickname) {
        if (nickname != null && !nickname.trim().isEmpty()) {
            this.blackNickname = nickname.trim();
            // 这里后续可以在控制面板显示黑方昵称
        }
    }

    public void deleteSaveFile() {
        // 只有登录用户才需要删除存档
        if (isLoggedIn && !username.equals("Guest")) {
            boolean deleted = saveManager.deleteSaveFile(username);
        }
    }
    private void initializeGame() {
        // 登录用户且有存档：询问是否加载存档
        if (isLoggedIn && saveManager.hasSaveFile(username)) {
            int choice = JOptionPane.showConfirmDialog(this, "检测到有存档，是否进入存档？", "加载存档",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                loadSavedGame();
                return;
            }
        }

        // 新游戏
        startNewGame();
    }

    private void loadSavedGame() {
        Map<String, Object> saveData = saveManager.loadGame(username);
        if (saveData != null) {
            try {
                model = new ChessBoardModel();
                @SuppressWarnings("unchecked")
                List<AbstractPiece> pieces = (List<AbstractPiece>) saveData.get("pieces");
                boolean isRedTurn = (Boolean) saveData.get("isRedTurn");
                // 从存档中加载昵称
                if (saveData.containsKey("redNickname")) {
                    redNickname = (String) saveData.get("redNickname");
                }
                if (saveData.containsKey("blackNickname")) {
                    blackNickname = (String) saveData.get("blackNickname");
                }

                model.setPieces(pieces);
                model.setRedTurn(isRedTurn);
                boardPanel = new ChessBoardPanel(model, this);

                JOptionPane.showMessageDialog(this, "存档加载成功！");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "存档加载失败，开始新游戏");
                startNewGame();
            }
        } else {
            JOptionPane.showMessageDialog(this, "存档损坏，开始新游戏");
            startNewGame();
        }
    }

    private void startNewGame() {
        model = new ChessBoardModel();
        if (boardPanel == null) {
            boardPanel = new ChessBoardPanel(model, this);
        } else {
            boardPanel.setModel(model);
        }
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // 使用BorderLayout布局：棋盘在中间，控制面板在右边
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 添加组件
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.EAST);

        add(mainPanel);

        // 设置窗口大小正好容纳棋盘和控制板
        pack();
        setLocationRelativeTo(null);
        setResizable(false); // 禁止调整窗口大小
    }

    private void setupEventListeners() {
        // 悔棋按钮事件
        controlPanel.setUndoListener(e -> undoMove());

        // 音乐按钮事件
        controlPanel.setMusicListener(e -> toggleMusic());

        // 新局按钮事件
        controlPanel.setNewGameListener(e -> newGame());
    }

    private void setupWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });
    }

    private void handleWindowClosing() {
        //停止背景音乐
        soundManager.stopBackgroundMusic();
        if (gameEnded){
            this.dispose();//直接关闭游戏
            new xiangqi.ui.Login.LoginFrame().show();
            return;
        }
        // 只有登录用户且游戏未结束时才询问保存
        if (isLoggedIn && !username.equals("Guest")) {
            int choice = JOptionPane.showConfirmDialog(this, "是否保存当前游戏进度？", "保存游戏",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                boolean saved = saveGame();
                if (saved) {
                    JOptionPane.showMessageDialog(this, "游戏已保存！");
                } else {
                    JOptionPane.showMessageDialog(this, "保存失败！");
                }
            }
        }

        // 关闭游戏，返回登录界面
        this.dispose();
        new xiangqi.ui.Login.LoginFrame().show();

    }

    private void undoMove() {
        // 悔棋功能(未实现)
        JOptionPane.showMessageDialog(this, "悔棋功能待实现");
    }

    //开关音乐
    private void toggleMusic() {
        soundManager.toggleMusic();
        controlPanel.updateMusicButton(soundManager.isMusicEnabled());
    }

    private void newGame() {
        //若一方胜出,再点击newGame会直接开始新游戏
        if(gameEnded){
            startNewGame();
            boardPanel.repaint();
            return;
        }
        //中途点会先询问
        int choice = JOptionPane.showConfirmDialog(this, "开始新游戏将丢失当前进度，确定吗？", "新游戏",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
            boardPanel.repaint();
        }
    }

    public void setGameEnded(boolean gameEnded){
        this.gameEnded=gameEnded;
    }

    public boolean isUserLoggedIn() {
        return isLoggedIn;
    }

    public String getUsername() {
        return username;
    }
}