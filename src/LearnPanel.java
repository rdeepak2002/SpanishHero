import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rdeep on 6/9/2017.
 */
public class LearnPanel extends JPanel implements ActionListener
{
    private CardLayout cardLayout;

    private JLabel title;
    private JTextArea informationArea;
    private JButton closeButton;
    private JScrollPane scrollPane;

    private int frameWidth;
    private int frameHeight;
    private int scale;
    private int level;

    public LearnPanel(CardLayout cl, int frameWidth, int frameHeight)
    {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.cardLayout = cl;

        setLayout(new BorderLayout());

        title = new JLabel("LEARN!");
        informationArea = new JTextArea("NULL");
        closeButton = new JButton("GO TO GAME");

        closeButton.addActionListener(this);

        scrollPane = new JScrollPane(informationArea);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(0);
        scrollPane.getVerticalScrollBar().setValue(0);

        refreshPanel(0);

        verticalScrollBar.setValue(0);
        scrollPane.getVerticalScrollBar().setValue(0);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }

    public void refreshPanel(int level)
    {
        Font trb;   // declare and initialize Font trb, Verdana Bold
        this.level = level;
        scale = frameWidth/1000;

        trb = new Font("Verdana", Font.BOLD, (int)(20*scale));

        // store as char
        // e = \u00e9
        // o = \u00f3
        // i = \u00ed
        // a = \u00e1
        // u = \u00fa
        // n = \u00f1

        char accentE = '\u00e9';
        char accentO = '\u00f3';
        char accentI = '\u00ed';
        char accentA = '\u00e1';
        char accentU = '\u00fa';


        if(level == 1)
        {
            if(scale == 2)
            {
                informationArea.setText("The Spanish simple present tense (el presente or el presente del indicativo) " +
                        "can be used to talk about habitual actions, routines, things happening now or in the near future, " +
                        "universal truths, facts, hypotheticals, lapses of time, and for ordering in restaurants and stores. \n\n" +
                        "To conjugate an -ar verb, remove the infinitive ending (-ar) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t\t-o\n" +
                        "t"+accentU+"                     \t\t\t\t-as\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-a\n" +
                        "nosotros               \t\t\t-amos\n" +
                        "vosotros               \t\t\t-"+accentA+"is\n" +
                        "ustedes / ellos / ellas\t\t\t-an\n\n" +
                        "To conjugate an -er verb, remove the infinitive ending (-er) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t\t-o\n" +
                        "t"+accentU+"                     \t\t\t\t-es\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-e\n" +
                        "nosotros               \t\t\t-emos\n" +
                        "vosotros               \t\t\t-"+accentE+"is\n" +
                        "ustedes / ellos / ellas\t\t\t-en\n\n" +
                        "To conjugate an -ir verb, remove the infinitive ending (-ir) and add the ending that matches the subject. " +
                        "You can find these endings in the table below.\n" +
                        "yo                     \t\t\t\t-o\n" +
                        "t"+accentU+"                     \t\t\t\t-es\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-e\n" +
                        "nosotros               \t\t\t-imos\n" +
                        "vosotros               \t\t\t-"+accentI+"s\n" +
                        "ustedes / ellos / ellas\t\t\t-en\n" +
                        "NOTE: The ir endings are similar to the er endings, but the nosotros and vosotros forms are different.\n\n" +
                        "" +
                        "Examples:\n" +
                        "I talk (hablar)      \t habl + o = hablo\n" +
                        "He eats (comer)      \t com + e = come\n" +
                        "We leave (salir)     \t sal + imos = salimos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs that change their stem in all the forms except the nosotros and vosotros forms. Some of them are: \n" +
                        "dormir (o to ue)\n" +
                        "encontrar (o to ue)\n" +
                        "venir (e to i)\n\n" +
                        "\nGOOD LUCK!");
            }
            else if(scale == 1)
            {
                informationArea.setText("The Spanish simple present tense (el presente or el presente del indicativo) " +
                        "can be used to talk about habitual actions, routines, things happening now or in the near future, " +
                        "universal truths, facts, hypotheticals, lapses of time, and for ordering in restaurants and stores. \n\n" +
                        "To conjugate an -ar verb, remove the infinitive ending (-ar) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t-o\n" +
                        "t"+accentU+"                     \t\t\t-as\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-a\n" +
                        "nosotros               \t\t\t-amos\n" +
                        "vosotros               \t\t\t-"+accentA+"is\n" +
                        "ustedes / ellos / ellas\t\t\t-an\n\n" +
                        "To conjugate an -er verb, remove the infinitive ending (-er) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t-o\n" +
                        "t"+accentU+"                     \t\t\t-es\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-e\n" +
                        "nosotros               \t\t\t-emos\n" +
                        "vosotros               \t\t\t-"+accentE+"is\n" +
                        "ustedes / ellos / ellas\t\t\t-en\n\n" +
                        "To conjugate an -ir verb, remove the infinitive ending (-ir) and add the ending that matches the subject. " +
                        "You can find these endings in the table below.\n" +
                        "yo                     \t\t\t-o\n" +
                        "t"+accentU+"                     \t\t\t-es\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-e\n" +
                        "nosotros               \t\t\t-imos\n" +
                        "vosotros               \t\t\t-"+accentI+"s\n" +
                        "ustedes / ellos / ellas\t\t\t-en\n" +
                        "NOTE: The ir endings are similar to the er endings, but the nosotros and vosotros forms are different.\n\n" +
                        "" +
                        "Examples:\n" +
                        "I talk (hablar)      \t habl + o = hablo\n" +
                        "He eats (comer)      \t com + e = come\n" +
                        "We leave (salir)     \t sal + imos = salimos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs that change their stem in all the forms except the nosotros and vosotros forms. Some of them are: \n" +
                        "dormir (o to ue)\n" +
                        "encontrar (o to ue)\n" +
                        "venir (e to i)\n\n" +
                        "\nGOOD LUCK!");
            }
        }
        else if(level == 2)
        {
            if(scale == 2)
            {
                informationArea.setText("The Spanish preterite tense (el pret"+accentE+"rito) is used to describe actions completed at a point in the past." +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t\t-"+ accentE +"\n" +
                        "t"+accentU+"                     \t\t\t\t-aste\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-"+accentO+"\n" +
                        "nosotros               \t\t\t-amos\n" +
                        "vosotros               \t\t\t-asteis\n" +
                        "ustedes / ellos / ellas\t\t\t-aron\n\n" +
                        "To conjugate an -er or -ir verb, remove the infinitive ending (-er or -ir) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t\t-"+accentI+"\n" +
                        "t"+accentU+"                     \t\t\t\t-iste\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-i"+accentO+"\n" +
                        "nosotros               \t\t\t-imos\n" +
                        "vosotros               \t\t\t-isteis\n" +
                        "ustedes / ellos / ellas\t\t\t-ieron\n\n" +
                        "Examples:\n" +
                        "I talked (hablar)      \t habl + "+accentE+" = habl"+accentE+"\n" +
                        "He ate (comer)      \t com + "+accentO+" = com"+accentO+"\n" +
                        "We left (salir)     \t sal + imos = salimos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs. Here are some of them: \n" +
                        "ser (era, eras, era, "+accentE+"ramos, eran)\n" +
                        "ir (iba, ibas, iba, "+accentI+"bamos, iban)\n\n" +
                        "\nGOOD LUCK!");
            }
            else if(scale == 1)             // NOTE: FIX SCALING WITH THIS
            {
                informationArea.setText("The Spanish preterite tense (el pret"+accentE+"rito) is used to describe actions completed at a point in the past." +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t-"+ accentE +"\n" +
                        "t"+accentU+"                     \t\t\t-aste\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-"+accentO+"\n" +
                        "nosotros               \t\t\t-amos\n" +
                        "vosotros               \t\t\t-asteis\n" +
                        "ustedes / ellos / ellas\t\t\t-aron\n\n" +
                        "To conjugate an -er or -ir verb, remove the infinitive ending (-er or -ir) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t-"+accentI+"\n" +
                        "t"+accentU+"                     \t\t\t-iste\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-i"+accentO+"\n" +
                        "nosotros               \t\t\t-imos\n" +
                        "vosotros               \t\t\t-isteis\n" +
                        "ustedes / ellos / ellas\t\t\t-ieron\n\n" +
                        "Examples:\n" +
                        "I talked (hablar)      \t habl + "+accentE+" = habl"+accentE+"\n" +
                        "He ate (comer)      \t com + "+accentO+" = com"+accentO+"\n" +
                        "We left (salir)     \t sal + imos = salimos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs that change their stem in all the forms. Some of them are: \n" +
                        "tener (tuv)\n" +
                        "estar (estuv)\n\n" +
                        "\nGOOD LUCK!");
            }
        }
        else if(level == 3)
        {
            if(scale == 2)
            {
                informationArea.setText("The Spanish imperfect tense (el imperfecto) is used to describe past habitual actions or to talk " +
                        "about what someone was doing when they were interrupted by something else.\n" +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t\t-aban\n" +
                        "t"+accentU+"                     \t\t\t\t-abas\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-aba\n" +
                        "nosotros               \t\t\t-"+accentA+"bamos\n" +
                        "vosotros               \t\t\t-abais\n" +
                        "ustedes / ellos / ellas\t\t\t-aban\n\n" +
                        "To conjugate an -er or -ir verb, remove the infinitive ending (-er or -ir) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t\t-"+accentI+"a\n" +
                        "t"+accentU+"                     \t\t\t\t-"+accentI+"as\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-i"+accentI+"a\n" +
                        "nosotros               \t\t\t-"+accentI+"amos\n" +
                        "vosotros               \t\t\t-"+accentI+"ais\n" +
                        "ustedes / ellos / ellas\t\t\t-"+accentI+"an\n\n" +
                        "Examples:\n" +
                        "I used to talk (hablar)      \t habl + aba = hablaba\n" +
                        "He used to eat (comer)      \t com + "+accentI+"a = com"+accentI+"a\n" +
                        "We used to cook (cocinar)     \t cocin + "+accentA+"bamos = cocin"+accentA+"bamos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs. Here are some of them: \n" +
                        "ser (era, eras, era, "+accentE+"ramos, eran)\n" +
                        "ir (iba, ibas, iba, "+accentI+"bamos, iban)\n\n" +
                        "There are some irregular verbs that change their stem in all the forms. Some of them are: \n" +
                        "tener (tuv)\n" +
                        "ver (ve)\n" +
                        "estar (estuv)\n\n" +
                        "\nGOOD LUCK!");
            }
            else if(scale == 1)             // NOTE: FIX SCALING WITH THIS
            {
                informationArea.setText("The Spanish imperfect tense (el imperfecto) is used to describe past habitual actions or to talk " +
                        "about what someone was doing when they were interrupted by something else.\n" +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t-aban\n" +
                        "t"+accentU+"                     \t\t\t-abas\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-aba\n" +
                        "nosotros               \t\t\t-"+accentA+"bamos\n" +
                        "vosotros               \t\t\t-abais\n" +
                        "ustedes / ellos / ellas\t\t\t-aban\n\n" +
                        "To conjugate an -er or -ir verb, remove the infinitive ending (-er or -ir) and add the ending that matches the subject. " +
                        "You can find these endings in the table below. \n" +
                        "yo                     \t\t\t-"+accentI+"a\n" +
                        "t"+accentU+"                     \t\t\t-"+accentI+"as\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-i"+accentI+"a\n" +
                        "nosotros               \t\t\t-"+accentI+"amos\n" +
                        "vosotros               \t\t\t-"+accentI+"ais\n" +
                        "ustedes / ellos / ellas\t\t\t-"+accentI+"an\n\n" +
                        "Examples:\n" +
                        "I used to talk (hablar)      \t habl + aba = hablaba\n" +
                        "He used to eat (comer)      \t com + "+accentI+"a = com"+accentI+"a\n" +
                        "We used to cook (cocinar)     \t cocin + "+accentA+"bamos = cocin"+accentA+"bamos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs. Here are some of them: \n" +
                        "ser (era, eras, era, "+accentE+"ramos, eran)\n" +
                        "ir (iba, ibas, iba, "+accentI+"bamos, iban)\n\n" +
                        "There are some irregular verbs that change their stem in all the forms. Some of them are: \n" +
                        "tener (tuv)\n" +
                        "ver (ve)\n" +
                        "estar (estuv)\n\n" +
                        "\nGOOD LUCK!");
            }

        }
        else if(level == 4)
        {
            informationArea.setText("Spanish has two past tenses: preterite and imperfect. Most verbs can be put into either tense, " +
                    "depending upon the meaning. You will also learn the basic difference between the preterite and the imperfect, so " +
                    "that you can begin using them correctly.\n\n" +
                    "Remember the acronyms SLIC and HOTCAMPSS to know the difference between the preterite and imperfect tenses.\n" +
                    "Only use the preterite for:\n" +
                    "S\tSudden Change\n" +
                    "L\tLimited Actions\n" +
                    "I\tIsolated Events\n" +
                    "C\tChain of Events\n\n" +
                    "Only use imperfect for:\n" +
                    "H\tHabitual Actions\n" +
                    "O\tOngoing Events\n" +
                    "T\tTime\n" +
                    "C\tCharacteristics\n" +
                    "A\tAge\n" +
                    "M\tMental State\n" +
                    "P\tPhysical State\n" +
                    "S\tSimultaneous Events\n" +
                    "S\tSetting the Scene\n");
        }
        else if(level == 5)
        {
            if(scale == 2)
            {
                informationArea.setText("The Spanish present perfect (el pret"+accentE+"rito perfect compuesto) is used to talk about things that " +
                        "started in the past and which continue or repeat in the present. It's also used to talk about things that have " +
                        "happened in the recent past.\n\n" +
                        "To formula for this is: present indicative of haber + past participle of another verb\n" +
                        "To get the past participle of an ar verb, you keep the stem and add ado.\n" +
                        "To get the past participle of an ir or er verb, you keep the stem and add ido.\n" +
                        "Example: bailar --> bailado\n" +
                        "Example: comer --> comido\n" +
                        "There are many irregulars. They are:\n" +
                        "decir : dicho\n" +
                        "hacer : hecho\n" +
                        "escribir : escrito\n" +
                        "romper : roto\n" +
                        "ver : visto\n" +
                        "abrir : abierto\n" +
                        "cubrir : cubierto\n" +
                        "poner : puesto\n" +
                        "morir : muerto\n" +
                        "volver : vuelto\n" +
                        "devolver : devuelto\n" +
                        "The present indicatives of haber are:\n" +
                        "yo                     \t\the\n" +
                        "t" + accentU + "                     \t\thas\n" +
                        "usted / " + accentE + "l / ella      \tha\n" +
                        "nosotros               \themos\n" +
                        "vosotros               \thab" + accentE + "is\n" +
                        "ustedes / ellos / ellas\than\n\n" +
                        "Examples:\n" +
                        "I have spoken: he hablado\n" +
                        "He has written: ha escrito\n\n" +
                        "GOOD LUCK!");
            }
            else
            {
                informationArea.setText("The Spanish present perfect (el pret"+accentE+"rito perfect compuesto) is used to talk about things that " +
                        "started in the past and which continue or repeat in the present. It's also used to talk about things that have " +
                        "happened in the recent past.\n\n" +
                        "To formula for this is: present indicative of haber + past participle of another verb\n" +
                        "To get the past participle of an ar verb, you keep the stem and add ado.\n" +
                        "To get the past participle of an ir or er verb, you keep the stem and add ido.\n" +
                        "Example: bailar --> bailado\n" +
                        "Example: comer --> comido\n" +
                        "There are many irregulars. They are:\n" +
                        "decir : dicho\n" +
                        "hacer : hecho\n" +
                        "escribir : escrito\n" +
                        "romper : roto\n" +
                        "ver : visto\n" +
                        "abrir : abierto\n" +
                        "cubrir : cubierto\n" +
                        "poner : puesto\n" +
                        "morir : muerto\n" +
                        "volver : vuelto\n" +
                        "devolver : devuelto\n" +
                        "The present indicatives of haber are:\n" +
                        "yo                     \t\t\the\n" +
                        "t" + accentU + "                     \t\t\thas\n" +
                        "usted / " + accentE + "l / ella      \t\t\tha\n" +
                        "nosotros               \t\t\themos\n" +
                        "vosotros               \t\t\thab" + accentE + "is\n" +
                        "ustedes / ellos / ellas\t\t\than\n\n" +
                        "Examples:\n" +
                        "I have spoken: he hablado\n" +
                        "He has written: ha escrito\n\n" +
                        "GOOD LUCK!");
            }
        }
        else if(level == 6)
        {
            if(scale == 2)
            {
                informationArea.setText("There are two ways to form the future tense in Spanish: the informal future (ir + a + infinitive) " +
                        "and the simple future (el futuro + simple). The simple future, unlike the informal future, " +
                        "is expressed in a single word.\n" +
                        "To form the simple future tense, simply add the correct ending to the infinitive of the verb. All verb conjugations " +
                        "(-ar, -er, and -ir) have the same endings in the simple future tense.\n" +
                        "yo                     \t\t\t\t-"+accentE+"\n" +
                        "t"+accentU+"                     \t\t\t\t-"+accentA+"s\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-"+accentA+"\n" +
                        "nosotros               \t\t\t-"+accentA+"emos\n" +
                        "vosotros               \t\t\t-"+accentE+"is\n" +
                        "ustedes / ellos / ellas\t\t\t-"+accentA+"n\n\n" +
                        "Examples:\n" +
                        "I will talk (hablar)      \t hablar + "+accentE+" = hablar"+accentE+"\n" +
                        "He will eat (comer)      \t comer + "+accentA+" = comer"+accentA+"\n" +
                        "We will live (vivr)     \t vivir + emos = viviremos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs that change their forms. Some of them are: \n" +
                        "tener (tendr)\n" +
                        "poner (pondr)\n" +
                        "querer (querr)\n" +
                        "hacer (har)\n" +
                        "decir (dir)\n" +
                        "vender (vendr)\n" +
                        "salir (saldr)\n\n" +
                        "\nGOOD LUCK!");
            }
            else
            {
                informationArea.setText("There are two ways to form the future tense in Spanish: the informal future (ir + a + infinitive) " +
                        "and the simple future (el futuro + simple). The simple future, unlike the informal future, " +
                        "is expressed in a single word.\n" +
                        "To form the simple future tense, simply add the correct ending to the infinitive of the verb. All verb conjugations " +
                        "(-ar, -er, and -ir) have the same endings in the simple future tense.\n" +
                        "yo                     \t\t\t-"+accentE+"\n" +
                        "t"+accentU+"                     \t\t\t-"+accentA+"s\n" +
                        "usted / "+accentE+"l / ella      \t\t\t-"+accentA+"\n" +
                        "nosotros               \t\t\t-"+accentA+"emos\n" +
                        "vosotros               \t\t\t-"+accentE+"is\n" +
                        "ustedes / ellos / ellas\t\t\t-"+accentA+"n\n\n" +
                        "Examples:\n" +
                        "I will talk (hablar)      \t hablar + "+accentE+" = hablar"+accentE+"\n" +
                        "He will eat (comer)      \t comer + "+accentA+" = comer"+accentA+"\n" +
                        "We will live (vivr)     \t vivir + emos = viviremos\n\n" +
                        "Irregular Verbs:\n" +
                        "There are some irregular verbs that change their forms. Some of them are: \n" +
                        "tener (tendr)\n" +
                        "poner (pondr)\n" +
                        "querer (querr)\n" +
                        "hacer (har)\n" +
                        "decir (dir)\n" +
                        "vender (vendr)\n" +
                        "salir (saldr)\n\n" +
                        "\nGOOD LUCK!");
            }
        }

        title.setFont(trb);
        informationArea.setFont(trb);
        closeButton.setFont(trb);

        informationArea.setLineWrap(true);
        informationArea.setWrapStyleWord(true);
        informationArea.setEditable(false);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(0);
        scrollPane.getVerticalScrollBar().setValue(0);

        repaint();

        verticalScrollBar.setValue(0);
        scrollPane.getVerticalScrollBar().setValue(0);
    }

    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();

        if(command.equals(closeButton.getText()))
        {
            cardLayout.show(super.getParent(), "Game");
        }
    }
}
