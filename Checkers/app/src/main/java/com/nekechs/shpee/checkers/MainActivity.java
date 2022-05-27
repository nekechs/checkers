package com.nekechs.shpee.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nekechs.shpee.checkers.core.Game;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView chessTextThingy = (TextView) findViewById(R.id.textView);

        Game game = new Game();
        chessTextThingy.setText(game.toString());
    }
}