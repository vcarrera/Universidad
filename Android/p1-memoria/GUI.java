package carrera.v.button1;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class GUI {
    private static final String TAG = "gui";
    private int MATCH_PARENT = TableLayout.LayoutParams.MATCH_PARENT;
    private int WRAP_CONTENT = TableLayout.LayoutParams.WRAP_CONTENT;
    private int column = Data.COLUMN_AVERAGE;
    private int row = Data.ROW_AVERAGE;
    private int nbut = column * row;
    private Game g;
    private Activity a;
    private Infobut memBut1 = null;
    private Infobut memBut2 = null;
    private Button buttonagain;
    private RadioGroup rg;
    private boolean endgame = false;
    private boolean phrasal = false;
    private Data d;
    private Drawable draw;

    //final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
    public GUI(Activity a) {
        this.a = a;
        Log.d(TAG, "gui-game");
        guiMenu();
    }

    void texttoast(String s) {
        int time = Toast.LENGTH_SHORT;
        Toast msg = Toast.makeText(a, s, time);
        msg.show();
    }

    private void addradiobut(RadioGroup rg, Data.Difficult d, String txt, boolean status) {
        RadioButton radiobut = new RadioButton(a);
        radiobut.setTag(d);
        radiobut.setText(txt);
        radiobut.setActivated(status);
        radiobut.setGravity(Gravity.CENTER);
        radiobut.setOnClickListener(new Selector());
        rg.addView(radiobut);
    }

    public void guiMenu() {
        phrasal = false;
        Log.d(TAG, "guiMenu1");
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT );
        TableLayout tableLayout = new TableLayout(a);
        tableLayout.setLayoutParams(tableParams);
        CheckBox cb = new CheckBox(a);
        cb.setText(Data.PHRASAL_VERB);
        cb.setOnClickListener(new checkcontrol());
        tableLayout.addView(cb);
        rg = new RadioGroup(a);
        addradiobut(rg, Data.Difficult.easy, Data.EASY, false);
        addradiobut(rg, Data.Difficult.average, Data.AVERAGE, true);
        addradiobut(rg, Data.Difficult.hard, Data.HARD, false);
        tableLayout.addView(rg);
        Log.d(TAG, "guiMenu2");
        TableRow.LayoutParams especialParams = new TableRow.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1.0f);
        TableRow tableRow = new TableRow(a);
        tableRow.setLayoutParams(especialParams);
        Button buttonstart = new Button(a);
        buttonstart.setLayoutParams(especialParams);
        buttonstart.setText(Data.B_START);
        buttonstart.setOnClickListener(new Start());
        buttonstart.setGravity(Gravity.CENTER);
        draw=buttonstart.getBackground();
        draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTryagain), PorterDuff.Mode.MULTIPLY);
        Log.d(TAG, "guiMenu3");
        tableRow.addView(buttonstart);
        tableLayout.addView(tableRow);
        a.setContentView(tableLayout);
    }

    public void guiGame() {
        Log.d(TAG, "guigame1");
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1.0f);
        TableRow.LayoutParams butParams = new TableRow.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1.0f);
        TableLayout tableLayout = new TableLayout(a);
        tableLayout.setLayoutParams(tableParams);
        int i, j;
        Integer k = 0;
        Log.d(TAG, "guigame2");
        for (i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(a);
            tableRow.setLayoutParams(rowParams);
            tableRow.setGravity(Gravity.CENTER);
            for (j = 0; j < column; j++) {
                Button but = new Button(a);
                but.setLayoutParams(butParams);
                Log.d(TAG, "k value " + k);
                but.setOnClickListener(new Infobut(k++, but));
                draw=but.getBackground();
                draw.setColorFilter(Color.parseColor(Data.ColorHexButtongame), PorterDuff.Mode.MULTIPLY);
                tableRow.addView(but);
            }
            tableLayout.addView(tableRow);
        }
        Log.d(TAG, "guigame3");
        TableRow.LayoutParams especialParams = new TableRow.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1.0f);
        TableRow tableRow = new TableRow(a);
        tableRow.setLayoutParams(rowParams);
        especialParams.span = column;
        buttonagain = new Button(a);
        buttonagain.setLayoutParams(especialParams);
        buttonagain.setText(Data.B_TRY_AGAIN);
        buttonagain.setOnClickListener(new Tryagain());
        buttonagain.setGravity(Gravity.CENTER);
        draw=buttonagain.getBackground();
        draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTryagain), PorterDuff.Mode.MULTIPLY);
        buttonagain.setAlpha(0);
        Log.d(TAG, "guigame4");
        tableRow.addView(buttonagain);
        tableLayout.addView(tableRow);
        a.setContentView(tableLayout);
        Log.d(TAG, "guigame5");
        if (phrasal) {
            d = new Data();
        }
        this.g = new Game(nbut);
    }

    private void turnfaill() {
        if (!(memBut2 == null)) {
            memBut1.button.setText("");
            draw=memBut1.button.getBackground();
            draw.setColorFilter(Color.parseColor(Data.ColorHexButtongame), PorterDuff.Mode.MULTIPLY);
            memBut2.button.setText("");
            draw=memBut2.button.getBackground();
            draw.setColorFilter(Color.parseColor(Data.ColorHexButtongame), PorterDuff.Mode.MULTIPLY);
            memBut2 = null;
        } else if (endgame) {
            //mp.stop();
            guiMenu();
            endgame = false;
        } else {
            texttoast(d.phrasal_list.get(g.getValue(memBut1.id)).t);
        }
        buttonagain.setAlpha(0);
    }

    private class checkcontrol implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            phrasal = !phrasal;
        }
    }

    private class Tryagain implements View.OnClickListener {
        public void onClick(View button) {
            turnfaill();
        }
    }

    private class Start implements View.OnClickListener {
        public void onClick(View button) {
            guiGame();
        }
    }

    private class Selector implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch ((Data.Difficult) v.getTag()) {
                case easy:
                    column = Data.COLUMN_EASY;
                    row = Data.ROW_EASY;
                    break;
                case average:
                    column = Data.COLUMN_AVERAGE;
                    row = Data.ROW_AVERAGE;
                    break;
                case hard:
                    column = Data.COLUMN_HARD;
                    row = Data.ROW_HARD;
                    break;
                default:
            }
            nbut = column * row;
        }
    }

    private class Infobut implements View.OnClickListener {
        private Integer id;
        private Button button;
        private boolean found = false;

        public Infobut(Integer id, Button button) {
            this.id = id;
            this.button = button;
        }

        public Integer getId() {
            return id;
        }

        public void onClick(View button) {
            int time;
            Toast msg;
            Integer id = this.getId();
            Log.d(TAG, "id " + id);
            if (!(memBut2 == null)) {
                turnfaill();
                return;
            }
            if (phrasal) {
                if (g.getId(id) % 2 == 0) {
                    this.button.setText(d.phrasal_list.get(g.getValue(id)).p);
                } else {
                    this.button.setText(d.phrasal_list.get(g.getValue(id)).m);
                }
            } else {
                if (g.getId(id) % 2 == 0) {
                    this.button.setText((Data.Eng.values()[g.getValue(id)]).toString());
                } else {
                    this.button.setText((Data.Es.values()[g.getValue(id)]).toString());
                }
            }
            switch (g.mechanics(id)) {
                case MEMO:
                    memBut1 = this;
                    this.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonSearching), PorterDuff.Mode.MULTIPLY);
                    if (phrasal) {
                        buttonagain.setText(Data.B_TRANSLATE);
                        buttonagain.setAlpha(100);
                    }
                    break;
                case FAIL:
                    memBut2 = this;
                    this.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonSearching), PorterDuff.Mode.MULTIPLY);
                    texttoast(Data.OTHER_MOV);
                    buttonagain.setText(Data.B_TRY_AGAIN);
                    buttonagain.setAlpha(100);
                    break;
                case LINK:
                    memBut1.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    this.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    memBut2 = null;
                    texttoast(Data.TWIN);
                    break;
                case ENDGAME:
                    memBut1.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    this.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    texttoast(Data.WIN + g.getMoves().toString());
                    endgame = true;
                    buttonagain.setText(Data.B_OTHER_GAME);
                    buttonagain.setAlpha(100);
                    //mp.start();
                    break;
                case OLD:
                    break;
                default:
            }
        }
    }
}