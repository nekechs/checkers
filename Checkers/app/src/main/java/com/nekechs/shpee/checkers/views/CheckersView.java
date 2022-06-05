package com.nekechs.shpee.checkers.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.nekechs.shpee.checkers.R;
import com.nekechs.shpee.checkers.core.Game;
import com.nekechs.shpee.checkers.core.Move;
import com.nekechs.shpee.checkers.core.PieceState;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CheckersView extends View {
    private final int darkSquareColor;// = R.color.cyan_800;
    private final int lightSquareColor;// = R.color.cyan_100;
    private final int moveHighlightColor;// = R.color.purple_200;
    private final int pieceHighlightColor;

    private float squareSideLength;
    private float boardSideLength;
    public int width;
    public int height;
    private float xOrigin;
    private float yOrigin;

    private Game checkersGame;

    private final Map<String, Bitmap> bmpPieceMap;// = new HashMap<>();
    private static Bitmap boardBMP;

    private Optional<PositionVector> selectionPosition;
    private Map<PositionVector, Move> positionToMoveMap;

    private static final int[] iconSetIDs = {
            R.drawable.black_king,
            R.drawable.white_king,
            R.drawable.black_pawn,
            R.drawable.white_pawn
    };

    private static final String[] pieceStrings = {"bk", "wk", "bp", "wp"};

    public CheckersView(Context context, AttributeSet attributes) {
        super(context, attributes);
        Resources res = getResources();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributes,
                R.styleable.CheckersView,
                0, 0
        );

        try {
            darkSquareColor = a.getColor(R.styleable.CheckersView_darkSquareColor, res.getColor(R.color.cyan_800));
            lightSquareColor = a.getColor(R.styleable.CheckersView_lightSquareColor, res.getColor(R.color.cyan_100));
            moveHighlightColor = a.getColor(R.styleable.CheckersView_highlightLightColor, res.getColor(R.color.purple_200));
            pieceHighlightColor = a.getColor(R.styleable.CheckersView_highlightDarkColor, res.getColor(R.color.purple_500));

        } finally {
            a.recycle();
        }

        bmpPieceMap = new HashMap<>();
        positionToMoveMap = new HashMap<>();

        for(int i = 0; i < iconSetIDs.length; i++) {
            bmpPieceMap.put(pieceStrings[i], generateBitmapFromSVG(500, iconSetIDs[i]));
        }

        selectionPosition = Optional.empty();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        selectionPosition = getSquareFromPos(x, y);
        selectionPosition.ifPresent(position -> positionToMoveMap = checkersGame.getAllValidMovesAtPosition(position));

        invalidate();

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculateDimensions();
        drawBoard(canvas);

        selectionPosition.ifPresent(positionVector -> drawHighlighedSquare(canvas, positionVector));

        if(checkersGame != null) {
            drawSuggestedMoves(canvas);
            drawPieces(canvas);
        }
    }

    public void setGame(Game game) {
        this.checkersGame = game;
    }

    /**
     * Creates a square bitmap given the ID of a SVG based icon. Useful for chess pieces.
     * @param size The size of the square which the bitmap is gonna be based on
     * @param iconID The icon of the ID that you would like to get a bitmap for
     * @return A Bitmap instance that is generated from an SVG
     */
    protected Bitmap generateBitmapFromSVG(int size, int iconID) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), iconID);
        Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas tmpCanvas = new Canvas(bmp);

        assert drawable != null;
        drawable.setBounds(0, 0, tmpCanvas.getWidth(), tmpCanvas.getHeight());
        drawable.draw(tmpCanvas);

        RectF rectangle = new RectF(0, 0, size, size);

        tmpCanvas.drawBitmap(bmp, null, rectangle, null);
        return bmp;
    }

    private void drawBoard(Canvas canvas) {
        Paint myPaint = new Paint();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                myPaint.setColor( (i + j) % 2 == 0 ? lightSquareColor : darkSquareColor);
                drawSquareAtPosition(canvas, myPaint, j, i);
                // canvas.drawRect(xOrigin + i * 50, yOrigin + j * 50, xOrigin + (i + 1) * 50, yOrigin + (j + 1) * 50, myPaint);
            }
        }
    }

    private void drawHighlighedSquare(Canvas canvas, PositionVector position) {
        Paint highlightPaint = new Paint();
        highlightPaint.setColor(pieceHighlightColor);
        drawSquareAtPosition(canvas, highlightPaint, position.getRow(), position.getCol());
    }

    private void drawSuggestedMoves(Canvas canvas) {
        Paint highlightPaint = new Paint();
        highlightPaint.setColor(moveHighlightColor);

        positionToMoveMap.keySet()
                .forEach(position -> drawSquareAtPosition(canvas,
                        highlightPaint,
                        position.getRow(),
                        position.getCol()));

    }

    private void drawPieces(Canvas canvas) {
        List<PieceState> stateList = checkersGame.getLatestPieceList();
        stateList.forEach(pieceState -> drawPieceAtPosition(canvas, pieceState.getPiece().toString(), pieceState.getPosition()));
    }

    private void drawPieceAtPosition(Canvas canvas, String pieceString, PositionVector position) {
        drawPieceAtPosition(canvas, pieceString, position.getRow(), position.getCol());
    }

    private void drawPieceAtPosition(Canvas canvas, String pieceString, int row, int col) {
        if(pieceString == null) return;

        // Now we actually do the thing to draw the piece.
        RectF dstSquare = new RectF(xOrigin + col * squareSideLength, yOrigin + row * squareSideLength,
                xOrigin + (col + 1) * squareSideLength, yOrigin + (row + 1) * squareSideLength);

        Bitmap bmp = bmpPieceMap.get(pieceString);

        canvas.drawBitmap(bmp, null, dstSquare, null);
    }

    private void drawSquareAtPosition(Canvas canvas, Paint paint, int row, int col) {
        canvas.drawRect(xOrigin + col * squareSideLength, yOrigin + row * squareSideLength,
                xOrigin + (col + 1) * squareSideLength, yOrigin + (row + 1) * squareSideLength,
                paint);
    }

    protected void generateBoardBitmap() {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap((int)boardSideLength, (int)boardSideLength, conf);
        Canvas canvas = new Canvas(bmp);

        Paint myPaint = new Paint();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                myPaint.setColor( (i + j) % 2 == 0 ? lightSquareColor : darkSquareColor);
                canvas.drawRect(xOrigin + i * squareSideLength, yOrigin + j * squareSideLength,
                        xOrigin + (i + 1) * squareSideLength, yOrigin + (j + 1) * squareSideLength,
                        myPaint);
                // canvas.drawRect(xOrigin + i * 50, yOrigin + j * 50, xOrigin + (i + 1) * 50, yOrigin + (j + 1) * 50, myPaint);
            }
        }

        boardBMP = bmp;
    }

    private Optional<PositionVector> getSquareFromPos(float x, float y) {
        int row = (int) (y / squareSideLength);
        int col = (int) ((x - xOrigin) / squareSideLength);

        if(row < 0 || row > 7 || col < 0 || col > 7) {
            return Optional.empty();
        }

        return Optional.of(new PositionVector(row, col));
    }

    private void calculateDimensions() {
        //TODO: Find out the reason why getMeasuredWidth/Height() is returning 0 always.
//        width = Math.max(400, getMeasuredWidth());
//        height = Math.max(400, getMeasuredHeight());
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        boardSideLength = Math.min(width, height);
        yOrigin = 0;
        xOrigin = (width - boardSideLength) / 2f;

        squareSideLength = boardSideLength / 8f;
    }
}
