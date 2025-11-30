package xiangqi.util;//工具包

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> soundClips;
    private Map<String, byte[]> soundData; // 预加载音频数据
    private Clip backgroundMusic;
    private boolean musicEnabled = true;
    private long backgroundMusicPosition = 0;

    private SoundManager(){
        soundClips=new HashMap<>();
        soundData = new HashMap<>();
        loadSounds();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }


    private void loadSound(String name,String filePath){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            AudioFormat format =audioInputStream.getFormat();//获取音频格式
            byte[] audioData=audioInputStream.readAllBytes();//预加载
            soundData.put(name,audioData);
            // 创建Clip但不打开，等播放时再打开
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            soundClips.put(name, clip);
            audioInputStream.close();
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //加载所有音效
    private void loadSounds(){
        try {
            //加载背景音乐
            loadSound("backgroundMusic","src/resources/Sounds/backgroundMusic.wav");
            // 加载音效
            loadSound("move", "src/resources/Sounds/move.wav");
            loadSound("eat", "src/resources/Sounds/eat.wav");
            loadSound("win", "src/resources/Sounds/win.wav");
            loadSound("check", "src/resources/Sounds/check.wav");
            //初始化背景音乐引用
            backgroundMusic = soundClips.get("backgroundMusic");
        } catch (Exception e) {
            System.err.println("音频加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //重复播放
    public void playBackgroundMusic(){
        if (!musicEnabled || backgroundMusic == null)
            return;
        try {
            //如果backgroundMusic为null,尝试重新获取
            if (backgroundMusic==null){
                backgroundMusic = soundClips.get("backgroundMusic");
                if (backgroundMusic==null){
                    System.err.println("背景音乐未加载成功");
                    return;
                }
            }
            // 如果背景音乐还没有打开，先打开它
            if (!backgroundMusic.isOpen()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/Sounds/backgroundMusic.wav"));
                backgroundMusic.open(audioInputStream);
                audioInputStream.close();
            }
            backgroundMusic.setMicrosecondPosition(backgroundMusicPosition);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        }catch (Exception e) {
            System.err.println("播放背景音乐失败: " + e.getMessage());
        }
    }

    //暂停播放
    public void pauseBackgroundMusic() {
        //检查背景音乐是否存在且正在播放
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            //获取当前音乐的播放位置,至微秒
            backgroundMusicPosition = backgroundMusic.getMicrosecondPosition();
            //暂停音乐播放
            backgroundMusic.stop();
        }
    }

    //停止播放
    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusicPosition = 0;
            backgroundMusic.setMicrosecondPosition(0);
        }
    }

    //播放-优化,减少延迟
    public void playSound(String soundName) {
        //检查音乐/音效是否启用
        if (!musicEnabled)
            return;
        //在新线程中播放音效,避免阻塞ui
        new Thread(() -> {
            try {
                Clip clip = soundClips.get(soundName);
                if (clip != null) {
                    // 如果Clip已经在播放，先停止并重置
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                    if (clip.isOpen()) {
                        clip.close();
                    }
                    // 重新打开Clip（这样可以减少延迟）
                    byte[] audioData = soundData.get(soundName);
                    if (audioData != null) {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                                new File(getSoundFilePath(soundName)));
                        clip.open(audioInputStream);
                        audioInputStream.close();
                    }
                    clip.setFramePosition(0);
                    clip.start();

                    // 监听播放结束，自动关闭Clip以释放资源
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            } catch (Exception e) {
                System.err.println("播放音效失败:" + soundName + " - " + e.getMessage());
            }
        }).start();
    }

    // 获取音效文件路径
    private String getSoundFilePath(String soundName) {
        return switch (soundName) {
            case "move" -> "src/resources/Sounds/move.wav";
            case "eat" -> "src/resources/Sounds/eat.wav";
            case "win" -> "src/resources/Sounds/win.wav";
            case "check" -> "src/resources/Sounds/check.wav";
            case "backgroundMusic" -> "src/resources/Sounds/backgroundMusic.wav";
            default -> "";
        };
    }
    //切换音乐启用状态 toggle 切换
    public void toggleMusic() {
        // 切换音乐启用状态（true ↔ false）
        musicEnabled = !musicEnabled;

        // 如果音乐现在启用了
        if (musicEnabled) {
            // 播放背景音乐
            playBackgroundMusic();
        } else {
            // 暂停背景音乐
            pauseBackgroundMusic();
        }
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    //改变音乐开关状态
    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        if (enabled) {
            playBackgroundMusic();
        } else {
            pauseBackgroundMusic();
        }
    }
}
