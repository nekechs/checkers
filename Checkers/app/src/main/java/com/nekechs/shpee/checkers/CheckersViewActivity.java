package com.nekechs.shpee.checkers;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

        Game game = new Game();

        CheckersView checkersView = (CheckersView) findViewById(R.id.checkers_view);
        checkersView.setGame(game);

        checkersView.invalidate();

        // Now, need to set up the Text
        TextInputEditText moveInput = (TextInputEditText) findViewById(R.id.move_input_field);

        moveInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int code, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && code == KeyEvent.KEYCODE_ENTER) {
                    String input = moveInput.getText().toString();
                    Optional<Move> moveOptional = MoveParser.parseMove(MoveParser.parseTextMoveList(input));

                    moveOptional.ifPresent(game::processMoveRequest);
                    moveInput.getText().clear();

                    checkersView.invalidate();
                    return true;
                }
                return false;
            }
        });

        /*
        moveInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                }
                return handled;

            }
        });
        */
    }
}
