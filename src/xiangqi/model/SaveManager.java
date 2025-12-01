package xiangqi.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SaveManager {
    private static final String SAVE_DIR = "saves";//保存文件的目录名(directory)为saves,常量一般大写
    private static final String SAVE_EXTENSION=".save";//存档文件的类型,文件后缀(File Extension)为save

    public SaveManager(){
        File dir =new File(SAVE_DIR); //创建了一个 File 对象 dir，指向路径为 SAVE_DIR 的目录,会出现在与src同级位置(默认)
        if (!dir.exists()){
            //使用 exists() 方法检查该目录是否存在。
            boolean created=dir.mkdir();//如果目录不存在，使用 mkdirs() 方法创建多级目录。
            System.out.println("存档目录创建: " + (created ? "成功" : "失败"));
        }

    }

    public boolean saveGame(String username,ChessBoardModel model,String redNickname, String blackNickname){
        if (username.equals("Guest")){
            return false;
        }

        //try声明需要自动关闭的资源+(资源创建链);
        try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(getSaveFilePath(username)))){
            //用Map映射
            Map<String, Object> saveData = new HashMap<>();
            saveData.put("pieces",model.getPieces());
            saveData.put("isRedTurn",model.isRedTurn());
            saveData.put("username",username);
            saveData.put("redNickname", redNickname);
            saveData.put("blackNickname", blackNickname);
            oos.writeObject(saveData);//序列化写入,把整个Map对象序列化写入文件
            oos.flush();
            return true;

        }catch (IOException e){
            return false;
        }
    }

    //加载存档
    public Map<String,Object> loadGame(String username){
        //禁止游客用户加载存档
        if (username.equals("Guest")){
            return null;
        }

        //如果该用户存档文件不存在,返回null
        File saveFile = new File(getSaveFilePath(username));
        if (!saveFile.exists()){
            return null;
        }
        //反序列化,需要强转
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))){
            Map<String,Object> saveData=(Map<String, Object>) ois.readObject();
            return saveData;
        }catch (IOException | ClassNotFoundException e){
            return null;
        }
    }
    //检查存档是否存在
    public boolean hasSaveFile(String username){
        return !username.equals("Guest")&&new File(getSaveFilePath(username)).exists();
    }
    //获取文件路径
    private String getSaveFilePath(String username){
        //返回saves\用户名.save
        return SAVE_DIR+File.separator+username+SAVE_EXTENSION;
        //File.separator,跨平台路径分隔符
    }
    public boolean deleteSaveFile(String username) {
        if (username.equals("Guest")) {
            return false;
        }
        File saveFile = new File(getSaveFilePath(username));
        if (saveFile.exists()) {
            return saveFile.delete();
        }
        return true; // 文件不存在也算删除成功
    }
}
