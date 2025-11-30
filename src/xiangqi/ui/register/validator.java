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
    //若不使用静态方法,validator的方法时就必须要先创建一个对象:
    //Validator validator = new Validator();
    //因为静态方法的特性就是1.可以没有对象就能直接通过类名调用2.属于类,所有对象共享同一个方法
    //(实例方法属于对象,每个对象都有自己的方法副本)
    public static boolean userExists(String username){
        try (BufferedReader reader=new BufferedReader(new FileReader(UserFile))){
            String line;
            while ((line=reader.readLine())!=null){
                String[] parts=line.split(",");//以","为分界,把原文本分为part[0](用户名),part[1](密码)
                if(parts.length>0&&parts[0].equals(username)){
                    return true;
                }
            }
        }catch (IOException e){
            //username与所有part[0]均不匹配,用户不存在
        }
        return false;
    }

}
