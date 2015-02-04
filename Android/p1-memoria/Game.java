package carrera.v.button1;

import android.util.Log;

import java.util.Random;

public class Game {
    private static final String TAG = "game";
    private Integer moves = 0;
    private Value arraytwin[];
    private int remaining;
    private Integer memo;
    private int nbut;

    public Game(int nbut) {
        Log.d(TAG, "Create");
        memo = -1;
        this.nbut = nbut;
        remaining = nbut / 2;
        arraytwin = twingenerator(nbut);
        shuffle(arraytwin);
    }

    /**
     * mechanics of the game
     *
     * @param now the array position of the button
     */
    public Status mechanics(Integer now) {
        Log.d(TAG, "mec (" + memo + ";" + now + ")");
        Status status;
        if (arraytwin[now].b) {
            status = Status.OLD;
        } else if (memo.equals(now)) {
            status = Status.MEMO;
        } else if (memo < 0) {
            memo = now;
            status = Status.MEMO;
        } else if (arraytwin[memo].i.equals(arraytwin[now].i)) {
            arraytwin[memo].b = true;
            arraytwin[now].b = true;
            if (--remaining == 0) {
                status = Status.ENDGAME;
            } else {
                status = Status.LINK;
            }
            memo = -1;
        } else {
            status = Status.FAIL;
            memo = -1;
        }
        moves++;
        return status;
    }

    private Value[] twingenerator(int n) {
        Log.d(TAG, "TWIN generator");
        if (n % 2 == 1) {
            throw new RuntimeException("TWIN isn't even");
        }
        Value twin[] = new Value[n];
        for (Integer i = 0; i < n / 2; i++) {
            Log.d(TAG, "TWIN" + i.toString());
            twin[2 * i] = new Value(i, false, 2 * i);
            twin[2 * i + 1] = new Value(i, false, 2 * i + 1);
        }
        return twin;
    }

    private void shuffle(Value[] twin) {
        Log.d(TAG, "shuffle generator");
        Value aux;
        Random rand = new Random();
        Integer r;
        for (int i = 0; i < twin.length; i++) {
            r = rand.nextInt(twin.length - 1);
            Log.d("Fichas_", r.toString());
            aux = twin[r];
            twin[r] = twin[i];
            twin[i] = aux;
        }
        for (Value j : twin) {
            Log.d("Fichas", j.i.toString());
        }
    }

    public Integer getValue(Integer pos) {
        Log.d(TAG, "getvalue: " + pos);
        return arraytwin[pos].i;
    }

    public int getId(Integer pos) {
        Log.d(TAG, "getId: " + pos);
        return arraytwin[pos].u;
    }

    public Integer getMoves() {
        return moves;
    }

    public enum Status {
        FAIL,
        LINK,
        MEMO,
        ENDGAME,
        OLD
    }

    private class Value {
        Integer i;
        Boolean b;
        int u;

        private Value(Integer i, Boolean b, int u) {
            this.b = b;
            this.i = i;
            this.u = u;
        }
    }
}
