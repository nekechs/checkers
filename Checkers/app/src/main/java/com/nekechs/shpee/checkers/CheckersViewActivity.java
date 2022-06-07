package com.nekechs.shpee.checkers;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nekechs.shpee.checkers.core.Game;
import com.nekechs.shpee.checkers.core.Move;
import com.nekechs.shpee.checkers.core.MoveParser;
import com.nekechs.shpee.checkers.views.CheckersView;

import java.util.Optional;

public class CheckersViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);

        TextView statusText = (TextView) findViewById(R.id.gameStatusText);

        Game game = new Game();
        game.setListener(new Game.GameListener() {
            @Override
            public void onIllegalMove() {
                statusText.setText("Illegal move detected");
            }

            @Override
            public void onCheckmate() {
                statusText.setText(R.string.checkmate_detect);
                System.out.println(Game.produceMoveStringList(game.moveList));
            }

            @Override
            public void onCaptureMove() {
                statusText.setText(R.string.capture_detect);
                System.out.println(Game.produceMoveStringList(game.moveList));
            }

            @Override
            public void onStalemate() {
                statusText.setText(R.string.stalemate_detect);
                System.out.println(Game.produceMoveStringList(game.moveList));
            }

            @Override
            public void onNormalMove() {
                statusText.setText(R.string.normal_detect);
                System.out.println(Game.produceMoveStringList(game.moveList));
            }
        });

        CheckersView checkersView = (CheckersView) findViewById(R.id.checkers_view);
        checkersView.setGame(game);

        checkersView.invalidate();

        Button button = (Button) findViewById(R.id.undoButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.undoMove();
                checkersView.invalidate();
            }
        });

        // Now, need to set up the Text
//        TextInputEditText moveInput = (TextInputEditText) findViewById(R.id.move_input_field);
//
//        moveInput.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int code, KeyEvent keyEvent) {
//                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && code == KeyEvent.KEYCODE_ENTER) {
//                    String input = moveInput.getText().toString();
//                    Optional<Move> moveOptional = MoveParser.parseMove(MoveParser.parseTextMoveList(input));
//
//                    moveOptional.ifPresent(game::processMoveRequest);
//                    moveInput.getText().clear();
//
//                    checkersView.invalidate();
//                    return true;
//                }
//                return false;
//            }
//        });
    }
}
