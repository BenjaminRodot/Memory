import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.math.*;

public class ControlTimer implements ActionListener {

    Fenetre fen;

    public ControlTimer() {}

    public ControlTimer(Fenetre fen) {
        this.fen = fen;
    }

    public void actionPerformed(ActionEvent e) {
        fen.seconde+=0.01;
        fen.seconde = Math.round(fen.seconde * 100.0)/100.0;
        fen.Temps.setText("Temps : "+Double.toString(fen.seconde));
    }
}