package carrera.v.button1;


public class Data {
    static final String EASY =      "Facil      2x2";
    static int COLUMN_EASY=2;
    static int ROW_EASY=2;
    static final String AVERAGE =  "Medio      3x4";
    static int COLUMN_AVERAGE=3;
    static int ROW_AVERAGE=4;
    static final String HARD =      "Dificil    4x10";
    static int COLUMN_HARD=4;
    static int ROW_HARD=10;

    enum Difficult{
        easy,
        average,
        hard
    }

    static final String WIN = "Total de movimientos: ";
    static final String OTHER_MOV = "pulse siguiente intento";
    static final String TWIN = "pareja encontrada";

    static final String TRY_AGAIN = "Siguiente Intento";
    static final String OTHER_GAME = "¿Jugar de nuevo?";

    static final String START = "Comenzar";

    static final String ColorHexButtongame = "#DA358A";
    static final String ColorHexButtonTryagain = "#0F0FE5";
    static final String ColorHexButtonTwin = "#059F1C";
    static final String ColorHexButtonSearching = "#FFA500";

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
        veinte
    }


}
