package com.nekechs.shpee.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nekechs.shpee.checkers.core.CheckersGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView chessTextThingy = (TextView) findViewById(R.id.textView);

        CheckersGame game = new CheckersGame();
        chessTextThingy.setText(game.toString());
    }
}