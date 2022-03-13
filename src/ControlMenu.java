import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ControlMenu implements ActionListener {

    Fenetre fen;

    public ControlMenu() {}

    public ControlMenu(Fenetre fen) {
        this.fen = fen;
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        int dimension = Integer.parseInt(source.getText());
        fen.changerDimension(dimension);
    }
}