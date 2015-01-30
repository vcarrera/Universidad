package carrera.v.button1;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class GUI {
    private static final String TAG = "gui";
    private int column =Data.COLUMN_AVERAGE;
    private int row =Data.ROW_AVERAGE;
    private int nbut = column * row;
    private Game g;
    private Activity a;
    private Infobut memBut1 = null;
    private Infobut memBut2 = null;
    private Button buttonagain;
    private RadioGroup rg;
    private boolean endgame = false;
    //final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);

    private class Tryagain implements View.OnClickListener {
        public void onClick(View button) {
            if (!(memBut2 == null)) {
                memBut1.button.setText("");
                memBut1.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtongame), PorterDuff.Mode.MULTIPLY);
                memBut2.button.setText("");
                memBut2.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtongame), PorterDuff.Mode.MULTIPLY);
                memBut2 = null;
            } else if (endgame) {
                //mp.stop();
                guiMenu();
                endgame = false;
            }
            buttonagain.setAlpha(0);
        }
    }

    private class Start implements View.OnClickListener {
        public void onClick(View button) {
            guiGame();
        }
    }

    private class Selector implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch ((Data.Difficult)v.getTag()){
                case easy:
                    column =Data.COLUMN_EASY;
                    row =Data.ROW_EASY;
                    break;
                case average:
                    column =Data.COLUMN_AVERAGE;
                    row =Data.ROW_AVERAGE;
                    break;
                case hard:
                    column =Data.COLUMN_HARD;
                    row =Data.ROW_HARD;
                    break;
                default:
            }
           nbut =column *row;
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

        private void texttoast(String s) {
            int time = Toast.LENGTH_SHORT;
            Toast msg = Toast.makeText(a, s, time);
            msg.show();
        }

        public void onClick(View button) {
            int time;
            Toast msg;
            Integer id = this.getId();
            Log.d(TAG, "id " + id);
            if (!(memBut2 == null)) {
                return;
            }

            if (g.getId(id)% 2 == 0) {
                this.button.setText((Data.Eng.values()[g.getValue(id)]).toString());
            }else{
                this.button.setText((Data.Es.values()[g.getValue(id)]).toString());
            }

            switch (g.mechanics(id)) {
                case MEMO:
                    memBut1 = this;
                    this.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonSearching), PorterDuff.Mode.MULTIPLY);
                    break;
                case FAIL:
                    memBut2 = this;
                    this.button.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonSearching), PorterDuff.Mode.MULTIPLY);
                    texttoast(Data.OTHER_MOV);
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
                    buttonagain.setText(Data.OTHER_GAME);
                    buttonagain.setAlpha(100);
                    //mp.start();
                    break;
                case OLD:
                    break;
                default:
            }
        }
    }


    public GUI(Activity a) {
        this.a = a;
        Log.d(TAG, "gui-game");
        guiMenu();
    }

    private void addradiobut(RadioGroup rg,Data.Difficult d, String txt, boolean status) {
        RadioButton radiobut = new RadioButton(a);
        radiobut.setTag(d);
        radiobut.setText(txt);
        radiobut.setActivated(status);
        radiobut.setGravity(Gravity.CENTER);
        radiobut.setOnClickListener(new Selector());
        rg.addView(radiobut);
    }

    public void guiMenu() {
        Log.d(TAG, "guiMenu1");
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableLayout tableLayout = new TableLayout(a);
        tableLayout.setLayoutParams(tableParams);
        rg = new RadioGroup(a);
        addradiobut(rg,Data.Difficult.easy, Data.EASY, false);
        addradiobut(rg,Data.Difficult.average, Data.AVERAGE, true);
        addradiobut(rg,Data.Difficult.hard, Data.HARD, false);
        tableLayout.addView(rg);
        Log.d(TAG, "guiMenu2");
        TableRow.LayoutParams especialParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        TableRow tableRow = new TableRow(a);
        tableRow.setLayoutParams(especialParams);
        Button buttonstart = new Button(a);
        buttonstart.setLayoutParams(especialParams);
        buttonstart.setText(Data.START);
        buttonstart.setOnClickListener(new Start());
        buttonstart.setGravity(Gravity.CENTER);
        buttonstart.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonTryagain), PorterDuff.Mode.MULTIPLY);
        Log.d(TAG, "guiMenu3");
        tableRow.addView(buttonstart);
        tableLayout.addView(tableRow);
        a.setContentView(tableLayout);
    }

    public void guiGame() {
        Log.d(TAG, "guigame1");
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
        TableRow.LayoutParams butParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
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
                //buttonagain.setPadding(0,0,0,0);
                Log.d(TAG, "k value " + k);
                but.setOnClickListener(new Infobut(k++, but));
                but.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtongame), PorterDuff.Mode.MULTIPLY);
                tableRow.addView(but);
            }
            tableLayout.addView(tableRow);
        }
        Log.d(TAG, "guigame3");
        TableRow.LayoutParams especialParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        TableRow tableRow = new TableRow(a);
        tableRow.setLayoutParams(rowParams);
        especialParams.span = column;
        buttonagain = new Button(a);
        buttonagain.setLayoutParams(especialParams);
        buttonagain.setText(Data.TRY_AGAIN);
        buttonagain.setOnClickListener(new Tryagain());
        buttonagain.setGravity(Gravity.CENTER);
        buttonagain.getBackground().setColorFilter(Color.parseColor(Data.ColorHexButtonTryagain), PorterDuff.Mode.MULTIPLY);
        buttonagain.setAlpha(0);
        Log.d(TAG, "guigame4");
        tableRow.addView(buttonagain);
        tableLayout.addView(tableRow);
        a.setContentView(tableLayout);
        Log.d(TAG, "guigame5");
        this.g = new Game(nbut);
    }

}
