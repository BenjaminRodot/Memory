import sun.lwawt.macosx.CSystemTray;

import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.Timer;

public class ControlButton implements ActionListener {

    Fenetre fen;

    public ControlButton() {}

    public ControlButton(Fenetre fen) {
        this.fen = fen;
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public void actionPerformed(ActionEvent e) {
        int nbColonne = fen.nbColonne;
        JButton button = (JButton) e.getSource();
        int x = button.getX();
        x=x/151;
        int y = button.getY();
        y=y/145;
        button.setIcon(fen.listeImages[y*nbColonne+x]);

        ControlTimer controltimer = new ControlTimer(fen);
        Timer time = new Timer(10, controltimer);

        if (fen.nbImageTrouvee==-1) {
            fen.nbImageTrouvee = 0;
            time.start();
        }

        if (fen.ImageClique[0]==null)
            fen.ImageClique[0]=button;
        else {
            if (fen.ImageClique[0]!=button) {
                if (!fen.ImageClique[0].getIcon().equals(button.getIcon())) {
                    fen.ImageClique[0].setIcon(fen.listeDosImages[0]);
                    button.setIcon(fen.listeDosImages[0]);
                    fen.nbEssais--;
                    fen.Essais.setText("Essais restants" + Integer.toString(fen.nbEssais));
                } else {
                    fen.ImageClique[0].setDisabledIcon(fen.ImageClique[0].getIcon());
                    fen.ImageClique[0].setEnabled(false);
                    button.setDisabledIcon(button.getIcon());
                    button.setEnabled(false);
                    fen.nbImageTrouvee+=2;

                    if (fen.nbImageTrouvee >= fen.nbCartes*2){
                        System.out.println("fini");
                        time.stop();

                        File doc = new File("score/"+fen.nbColonne);
                        double score1;
                        double score2;
                        double score3;
                        try {
                            Scanner obj = new Scanner(doc);
                            score1 = Double.parseDouble(obj.nextLine());
                            score2 = Double.parseDouble(obj.nextLine());
                            score3 = Double.parseDouble(obj.nextLine());
                            if (score1>fen.seconde)
                                score1=fen.seconde;
                            else{
                                if (score2>fen.seconde)
                                    score2=fen.seconde;
                                else{
                                    if (score3>fen.seconde)
                                        score3=fen.seconde;
                                }
                            }
                            FileWriter writer = new FileWriter("score/"+fen.nbColonne);
                            System.out.println(score1+"\n"+score2+"\n"+score3);
                            writer.write(score1+"\n"+score2+"\n"+score3);
                            writer.close();

                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                fen.ImageClique = new JButton[1];
            }
        }
    }
}