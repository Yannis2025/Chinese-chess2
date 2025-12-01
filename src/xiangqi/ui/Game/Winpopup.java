package xiangqi.ui.Game;

import xiangqi.model.ChessBoardModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Winpopup extends JLabel {
    private ImageIcon originalImage;
    int width = 621;
    int height = 413;
    public Winpopup(String address, ChessBoardModel model,ChessBoardPanel boardPanel) {
        originalImage=new ImageIcon("src/resources/Popup/"+address+".png");
        setSize(width,height);
        ImageIcon scaleImage = new ImageIcon(originalImage.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
        this.setIcon(scaleImage);
        setLocation(100,200);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boardPanel.setVisible(false);
                model.getPieces().clear();
                model.initializePieces();
                repaint();
                Winpopup.this.setVisible(false);
                boardPanel.setVisible(true);
            }
        });
        setLayout(null);//使用绝对定位
    }
}



