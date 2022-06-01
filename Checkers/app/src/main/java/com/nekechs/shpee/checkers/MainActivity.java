package com.nekechs.shpee.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nekechs.shpee.checkers.core.Game;
import com.nekechs.shpee.checkers.core.Move;
import com.nekechs.shpee.checkers.core.MoveParser;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView chessTextThingy = (TextView) findViewById(R.id.textView);

        Game game = new Game();
        chessTextThingy.setText(game.toString());

        final Button testMoveButton = (Button) findViewById(R.id.move_test_button);
        final Button dupeButton = (Button) findViewById(R.id.print_button);
        final Button undoButton = (Button) findViewById(R.id.undo_button);

        final EditText textInput = (EditText) findViewById(R.id.move_text_input);

        testMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = textInput.getText().toString();
                Optional<Move> moveOptional = MoveParser.parseMove(MoveParser.parseTextMoveList(input));

                moveOptional.ifPresent(game::processMoveRequest);
                textInput.getText().clear();

                chessTextThingy.setText(game.toString());
//                System.out.println(game);
            }
        });

        dupeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chessTextThingy.setText(game.toString());
//                System.out.println(game);
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.undoMove();

                chessTextThingy.setText(game.toString());
//                System.out.println(game);
            }
        });
    }
}