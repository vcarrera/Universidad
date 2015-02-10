package carrera.v.button1;

import java.util.ArrayList;
import java.util.Collections;

public class Data {
    static final String PHRASAL_VERB = "easter egg";
    static final String EASY = "Facil 2x2";
    static final String AVERAGE = "Medio 3x4";
    static final String HARD = "Dificil 4x10";
    static final String WIN = "Total de movimientos: ";
    static final String OTHER_MOV = "pulse siguiente intento";
    static final String TWIN = "pareja encontrada";
    static final String B_TRY_AGAIN = "Siguiente Intento";
    static final String B_OTHER_GAME = "¿Jugar de nuevo?";
    static final String B_TRANSLATE = "Traducir";
    static final String B_START = "Comenzar";

    static final String ColorHexButtongame = "#FF0000";
    static final String ColorHexButtonTryagain = "#0000FF";
    static final String ColorHexButtonTwin = "#00FF00";
    static final String ColorHexButtonSearching = "#FFA500";

    static int COLUMN_EASY = 2;
    static int ROW_EASY = 2;
    static int COLUMN_AVERAGE = 3;
    static int ROW_AVERAGE = 4;
    static int COLUMN_HARD = 4;
    static int ROW_HARD = 10;
    ArrayList<Phrasal_verbs> phrasal_list;

    public Data() {
        this.phrasal_list = new ArrayList<Phrasal_verbs>();
        phrasal_list.add(new Phrasal_verbs("Ask for", "request", "solicitar"));
        phrasal_list.add(new Phrasal_verbs("Deal with", "handle", "agarralo"));
        phrasal_list.add(new Phrasal_verbs("Find out", "discover", "descubrir"));
        phrasal_list.add(new Phrasal_verbs("Get over", "recover", "recuperarse"));
        phrasal_list.add(new Phrasal_verbs("Go ahead", "proceed", "adelante"));
        phrasal_list.add(new Phrasal_verbs("Hang on", "keep", "guardar"));
        phrasal_list.add(new Phrasal_verbs("Hang out", "meet friends", "quedar"));
        phrasal_list.add(new Phrasal_verbs("Pick up", "get", "coger"));
        phrasal_list.add(new Phrasal_verbs("Drop off", "leave", "soltar"));
        phrasal_list.add(new Phrasal_verbs("Put on", "dress", "vestirse"));
        phrasal_list.add(new Phrasal_verbs("Take off", "strip", "desnudarse"));
        phrasal_list.add(new Phrasal_verbs("Turn on", "connect", "encender"));
        phrasal_list.add(new Phrasal_verbs("Turn off", "disconnect", "apagar"));
        phrasal_list.add(new Phrasal_verbs("Turn up", "appear", "presentarse"));
        phrasal_list.add(new Phrasal_verbs("Turn down", "refuse", "rechazar"));
        phrasal_list.add(new Phrasal_verbs("Take off", "start flying", "despegar"));
        phrasal_list.add(new Phrasal_verbs("Stay away", "avoid", "alejarse"));
        phrasal_list.add(new Phrasal_verbs("Get over", "recover", "recuperarse"));
        phrasal_list.add(new Phrasal_verbs("Make up", "reconcile", "reconciliarse"));
        phrasal_list.add(new Phrasal_verbs("Put up", "tolerate", "tolerar"));
        Collections.shuffle(this.phrasal_list);
    }

    enum Difficult {
        easy,
        average,
        hard
    }

    enum Eng {
        one,
        two,
        three,
        four,
        five,
        six,
        seven,
        eight,
        nine,
        ten,
        eleven,
        twelve,
        thirteen,
        fourteen,
        fifteen,
        sixteen,
        seventeen,
        eighteen,
        nineteen,
        twenty
    }

    enum Es {
        uno,
        dos,
        tres,
        cuatro,
        cinco,
        seis,
        siete,
        ocho,
        nueve,
        diez,
        once,
        doce,
        trece,
        catorce,
        quince,
        dieciseis,
        diecisiete,
        dieciocho,
        diecinueve,
        veinte;
    }

    class Phrasal_verbs {
        String p;
        String m;
        String t;

        Phrasal_verbs(String p, String m, String t) {
            this.m = m;
            this.t = t;
            this.p = p;
        }
    }
}