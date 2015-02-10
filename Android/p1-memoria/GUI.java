package com.example.vcarrre.gamebutton1;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
    MediaPlayer mp;
    private int MATCH_PARENT = TableLayout.LayoutParams.MATCH_PARENT;
    private int WRAP_CONTENT = TableLayout.LayoutParams.WRAP_CONTENT;
    private int column = Data.COLUMN_AVERAGE;
    private int row = Data.ROW_AVERAGE;
    private int nbut = column * row;
    private Game g;
    private Activity a;
    private Gamebut memBut1 = null;
    private Gamebut memBut2 = null;
    private Button buttonTranslate;
    private RadioGroup rg;
    private boolean endgame = false;
    private boolean phrasal = false;
    private boolean ontranslate=false;
    private Data.Difficult status;
    private Data d;
    private Drawable draw;

    public GUI(Activity a) {
        status = Data.Difficult.average;
        this.a = a;
        Log.d(TAG, "gui-game");
        guiMenu();
    }

    void texttoast(String s) {
        int time = Toast.LENGTH_SHORT;
        Toast msg = Toast.makeText(a, s, time);
        msg.show();
    }

    private void addradiobut(RadioGroup rg, Data.Difficult d, String txt, Data.Difficult status) {
        RadioButton radiobut = new RadioButton(a);
        radiobut.setTag(d);
        radiobut.setText(txt);
        radiobut.setGravity(Gravity.CENTER);
        radiobut.setOnClickListener(new Selector());
        rg.addView(radiobut);
        radiobut.setChecked(d == status);
    }

    public void guiMenu() {
        mp = MediaPlayer.create(a, R.raw.sound);
        phrasal = false;
        ontranslate=false;
        Log.d(TAG, "guiMenu1");
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        TableLayout tableLayout = new TableLayout(a);
        tableLayout.setLayoutParams(tableParams);
        CheckBox cb = new CheckBox(a);
        cb.setText(Data.PHRASAL_VERB);
        cb.setOnClickListener(new checkcontrol());
        tableLayout.addView(cb);
        rg = new RadioGroup(a);
        addradiobut(rg, Data.Difficult.easy, Data.EASY, status);
        addradiobut(rg, Data.Difficult.average, Data.AVERAGE, status);
        addradiobut(rg, Data.Difficult.hard, Data.HARD, status);
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
        draw = buttonstart.getBackground();
        draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTranslateOn), PorterDuff.Mode.MULTIPLY);
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
                but.setOnClickListener(new Gamebut(k++, but));
                draw = but.getBackground();
                draw.setColorFilter(Color.parseColor(Data.ColorHexButtonBack), PorterDuff.Mode.MULTIPLY);
                tableRow.addView(but);
            }
            tableLayout.addView(tableRow);
        }
        Log.d(TAG, "guigame3");
        TableRow.LayoutParams especialParams = new TableRow.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1.0f);
        TableRow tableRow = new TableRow(a);
        tableRow.setLayoutParams(rowParams);
        especialParams.span = column;
        buttonTranslate = new Button(a);
        buttonTranslate.setLayoutParams(especialParams);
        buttonTranslate.setText(Data.B_TRANSLATE);
        buttonTranslate.setOnClickListener(new Assistant());
        buttonTranslate.setGravity(Gravity.CENTER);
        draw = buttonTranslate.getBackground();
        draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTranslateOn), PorterDuff.Mode.MULTIPLY);
        if (!phrasal){
            buttonTranslate.setAlpha(0);
        }
        Log.d(TAG, "guigame4");
        tableRow.addView(buttonTranslate);
        tableLayout.addView(tableRow);
        a.setContentView(tableLayout);
        Log.d(TAG, "guigame5");
        if (phrasal) {
            d = new Data();
        }
        this.g = new Game(nbut);
    }

    private void turnfaill() {
        memBut1.button.setText("");
        draw = memBut1.button.getBackground();
        draw.setColorFilter(Color.parseColor(Data.ColorHexButtonBack), PorterDuff.Mode.MULTIPLY);
        memBut2.button.setText("");
        draw = memBut2.button.getBackground();
        draw.setColorFilter(Color.parseColor(Data.ColorHexButtonBack), PorterDuff.Mode.MULTIPLY);
        memBut2 = null;
        if (ontranslate) {
            buttonTranslate.setText("");
        }
    }

    private class checkcontrol implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            phrasal = !phrasal;
        }
    }

    private class Assistant implements View.OnClickListener {
        public void onClick(View button) {
            if (endgame) {
                mp.stop();
                guiMenu();
                endgame = false;
            } else {
                ontranslate=!ontranslate;
            }
            if (!ontranslate){
                buttonTranslate.setText(Data.B_TRANSLATE);
                draw = buttonTranslate.getBackground();
                draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTranslateOn), PorterDuff.Mode.MULTIPLY);
            }else{
                draw = buttonTranslate.getBackground();
                draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTranslateOff), PorterDuff.Mode.MULTIPLY);
            }
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
                    status = Data.Difficult.easy;
                    break;
                case average:
                    column = Data.COLUMN_AVERAGE;
                    row = Data.ROW_AVERAGE;
                    status = Data.Difficult.average;
                    break;
                case hard:
                    column = Data.COLUMN_HARD;
                    row = Data.ROW_HARD;
                    status = Data.Difficult.hard;
                    break;
                default:
            }
            nbut = column * row;
        }
    }

    private class Gamebut implements View.OnClickListener {
        private Integer id;
        private Button button;

        public Gamebut(Integer id, Button button) {
            this.id = id;
            this.button = button;
        }

        public Integer getId() {
            return id;
        }

        public void onClick(View button) {
            Integer id = this.getId();
            Log.d(TAG, "id " + id);
            if (!(memBut2 == null)) {
                turnfaill();
                return;
            }
            if (phrasal) {
                if (ontranslate) {
                    buttonTranslate.setText(d.phrasal_list.get(g.getValue(id)).t);
                }
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
                    draw = this.button.getBackground();
                    draw.setColorFilter(Color.parseColor(Data.ColorHexButtonSearching), PorterDuff.Mode.MULTIPLY);
                    break;
                case FAIL:
                    memBut2 = this;
                    draw = this.button.getBackground();
                    draw.setColorFilter(Color.parseColor(Data.ColorHexButtonSearching), PorterDuff.Mode.MULTIPLY);
                    texttoast(Data.OTHER_MOV);
                    break;
                case LINK:
                    draw = memBut1.button.getBackground();
                    draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    draw = this.button.getBackground();
                    draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    memBut2 = null;
                    texttoast(Data.TWIN);
                    break;
                case ENDGAME:
                    draw = memBut1.button.getBackground();
                    draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    draw = this.button.getBackground();
                    draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTwin), PorterDuff.Mode.MULTIPLY);
                    texttoast(Data.WIN + g.getMoves().toString());
                    endgame = true;
                    buttonTranslate.setText(Data.B_OTHER_GAME);
                    draw = buttonTranslate.getBackground();
                    draw.setColorFilter(Color.parseColor(Data.ColorHexButtonTranslateOn), PorterDuff.Mode.MULTIPLY);
                    buttonTranslate.setAlpha(100);
                    mp.start();
                    break;
                case OLD:
                    break;
                default:
            }
        }
    }
}