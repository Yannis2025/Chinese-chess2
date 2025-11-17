package xiangqi.ui.register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class validator {
    private static final String UserFile = "UserInformation";
    public static boolean validate(String username, String password){
        try(BufferedReader reader=new BufferedReader(new FileReader(UserFile))){
            String line;
            //逐一比对输入信息与txt中的用户名与密码是否匹配,直到最后一行
            while ((line=reader.readLine())!=null){
                if (line.equals(username+","+password))
                    //要求txt文件中的用户信息每行都以"username,password"格式储存
                    return true;
            }
        }catch (IOException e){
            return false;
        }
        return false;//没一行匹配就return false
    }

/*public static boolean testValidate(String username, String password){
    // 检查文件
    File file = new File(UserFile);
    System.out.println("文件绝对路径: " + file.getAbsolutePath());
    System.out.println("文件是否存在: " + file.exists());

    if (!file.exists()) {
        System.out.println("请把 UserInformation.txt 放在这个目录: " + System.getProperty("user.dir"));
        return false;
    }
    System.out.println("调试：开始验证 " + username + "/" + password);

    try(BufferedReader reader=new BufferedReader(new FileReader(UserFile))){
        String line;
        int lineCount = 0;
        while ((line=reader.readLine())!=null){
            lineCount++;
            System.out.println("调试：第" + lineCount + "行: " + line);
            if (line.equals(username+","+password)) {
                System.out.println("调试：匹配成功！");
                return true;
            }
        }
        System.out.println("调试：遍历了 " + lineCount + " 行，没有匹配");
    }catch (IOException e){
        System.out.println("调试：文件读取错误: " + e.getMessage());
        return false;
    }
    return false;
}*/
//fuck!这一段搞我好久,原来是不用带txt后缀!

}
