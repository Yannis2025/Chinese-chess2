package xiangqi.ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Promptpopup extends JLabel {
    private ImageIcon originalImage;
    int width;// = 621;
    int height ;//= 413;
    int time;
    public Promptpopup(String address ,int width,int height,int time) {
        this.width=width;
        this.height=height;
        this.time=time;
        originalImage=new ImageIcon("src/resources/Popup/"+address+".png");
        setSize(width,height);
        ImageIcon scaleImage = new ImageIcon(originalImage.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
        this.setIcon(scaleImage);
        setLocation(100,200);
        ActionListener listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Promptpopup.this.setVisible(false);
            }
        };
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Promptpopup.this.setVisible(false);
            }
        });
        Timer timer = new Timer(time,listener);
        timer.setRepeats(false); // 只执行一次
        timer.start();
        setLayout(null);//使用绝对定位
    }
}



