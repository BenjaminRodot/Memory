import javax.swing.*; // Pour les composants graphiques que l'on ajoutera dans la méthode creerWidget
import java.awt.*;    // Pour la JFrame
import java.util.Random;
import java.io.File;

public class Fenetre extends JFrame {
    public int nbColonne;
    public int nbCases;
    public int nbCartes;
    public int nbImages;
    public ImageIcon[] listeImages;
    public ImageIcon[] listeDosImages;
    public JButton[] ImageClique;
    public int nbImageTrouvee;

    public JMenuBar barMenu;
    public JMenu menu;
    public JMenuItem itemInterface1;
    public JMenuItem itemInterface2;
    public JMenu itemDimension;
    public JMenuItem itemDimension3;
    public JMenuItem itemDimension4;
    public JMenuItem itemDimension5;

    public JPanel GrillePanel;

    public JPanel EssaisPanel;
    public JLabel Essais;
    public int nbEssais;

    public JPanel PanelGeneral;
    public JPanel PanelGeneral2;

    public boolean inTab(int entier, int[] tab){
        for (int i=0; i<tab.length; i++){
            if (tab[i]==entier)
                return true;
        }
        return false;
    }

    public void shuffle(ImageIcon[] tab){
        int longueur = tab.length;
        for (int i=0; i<longueur; i++){
            Random r = new Random();
            int cpt = r.nextInt(longueur);
            ImageIcon temp=tab[i];
            tab[i]=tab[cpt];
            tab[cpt]=temp;
        }
    }

    public ImageIcon[] listeImages(){
        String chemin = "../img";
        ImageIcon [] listeImages=new ImageIcon[18];
        try{
            File folder=new File(chemin);
            int cpt=0;
            for (final File fileEntry : folder.listFiles()) {
                listeImages[cpt]=new ImageIcon(chemin+'/'+fileEntry.getName()) ;
                cpt++ ;
            }
        }catch(Exception e){ e.printStackTrace(); }
        return listeImages;
    }

     public ImageIcon[] listeImagesAleatoire(){
        ImageIcon[] result = new ImageIcon[nbCases];
        ImageIcon[] listeImages = listeImages();
        ImageIcon death = new ImageIcon("../imgSpecial/death.png");
        int[] temp = new int[nbCartes];

        Random r = new Random();
        int cpt = 1;
        temp[0]=cpt;
        result[0]=listeImages[cpt];
        result[0+nbCartes]=listeImages[cpt];

        for (int i=1; i<nbCartes; i++){
            r = new Random();
            cpt = r.nextInt(nbImages);
            while (inTab(cpt, temp)){
                cpt = r.nextInt(nbImages);
            }
            temp[i]=cpt;
            result[i]=listeImages[cpt];
            result[i+nbCartes]=listeImages[cpt];
        }
        if (nbCases%2==1)
            result[nbCases-1]=death;
        shuffle(result);
        return result;
    }

    public ImageIcon[] listeDosImages(){
        ImageIcon[] result = new ImageIcon[nbCases];
        ImageIcon backCard = new ImageIcon("../imgSpecial/backCard.png");
        for (int i=0; i<nbCases; i++)
            result[i]=backCard;
        return result;
    }

    public void initAttribut(int nbColonne){
        setSize(500,400);
        this.nbColonne = nbColonne;
        nbCases=nbColonne*nbColonne;
        nbCartes = nbCases/2;
        nbImages = 16;
        nbEssais = nbCases/2;
        nbImageTrouvee=-1;
        ImageClique = new JButton[1];
        listeDosImages = listeDosImages();
        listeImages= listeImagesAleatoire();

        /*---------INIT MENU----------*/

        ControlMenu controlMenu = new ControlMenu(this);
        barMenu = new JMenuBar();
        menu = new JMenu("Options");
        itemDimension = new JMenu("Dimension");
        itemDimension3 = new JMenuItem("3");
        itemDimension4 = new JMenuItem("4");
        itemDimension5 = new JMenuItem("5");

        itemDimension3.addActionListener(controlMenu);
        itemDimension4.addActionListener(controlMenu);
        itemDimension5.addActionListener(controlMenu);

        /*---------INIT GRILLE----------*/

        GrillePanel = new JPanel(new GridLayout(nbColonne,nbColonne,5,5));
        GrillePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        ControlButton control = new ControlButton(this);
        for (int i=0; i<nbCases; i++){
            JButton button = new JButton(listeDosImages[i]);
            button.addActionListener(control);
            GrillePanel.add(button);
        }

        /*---------INIT ESSAIS----------*/

        String nbEssaisStr = Integer.toString(nbEssais);
        EssaisPanel = new JPanel();
        EssaisPanel.setLayout(new BoxLayout(EssaisPanel, BoxLayout.X_AXIS));
        Essais = new JLabel("Essais restants" + nbEssaisStr);

        PanelGeneral = new JPanel();
        PanelGeneral.setLayout(new BoxLayout(PanelGeneral, BoxLayout.Y_AXIS));

        PanelGeneral2 = new JPanel();
    }

    public void ajouterWidget(){

        /*---------PANEL MENU----------*/

        itemDimension.add(itemDimension3);
        itemDimension.add(itemDimension4);
        itemDimension.add(itemDimension5);
        menu.add(itemDimension);

        barMenu.add(menu);
        setJMenuBar(barMenu);

        /*---------PANEL GRILLE----------*/



        /*---------PANEL ESSAIS----------*/

        EssaisPanel.add(Essais);

        /*---------PANEL GENERAL----------*/

        PanelGeneral.add(GrillePanel);
        PanelGeneral.add(EssaisPanel);

        PanelGeneral2.add(PanelGeneral);
        setContentPane(PanelGeneral2);
    }

    public void changerDimension(int dimension){
        initAttribut(dimension);
        ajouterWidget();
        pack();
        setVisible(true);
    }

    public Fenetre() {
        initAttribut(4);
        ajouterWidget();
        pack();
        setVisible(true);                                // Affiche la fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
    }
}