import java.io.File;
import java.util.Map;
import java.util.Scanner;

public final class Main {
    private static Board chessBoard;

    private final int lowerBound = 3;
    private final int higherBound = 1000;

    public static void main(String[] args) {
        Main main = new Main();
        try {
            File input = new File("input.txt");
            File output = new File("output.txt");
            Scanner scanner = new Scanner(System.in);

            int n = scanner.nextInt();
            if (n < main.lowerBound || n > main.higherBound) {
                throw new InvalidBoardSizeException();
            }

            int m = scanner.nextInt();
            if (m < 2 || m > n * n) {
                throw new InvalidNumberOfPiecesException();
            }

            //реализовать while и counter, так мы узнаем есть ли лишние фигуры и сделаем эксепшн
            //все вычисления сделать в default интерфейсе

        } catch (InvalidBoardSizeException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (InvalidNumberOfPiecesException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}

enum PieceColor {
    WHITE,
    BLACK;

    public PieceColor parse(String color) {
        if ("WHITE".equals(color)) {
            return PieceColor.WHITE;
        }
        return PieceColor.BLACK;
    }
}

class Board {
    private Map<String, ChessPiece> positionsToPieces;
    private int size;

    Board(int boardSize) {
        this.size = boardSize;
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {

    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {

    }

    public void addPiece(ChessPiece piece) {

    }

    public ChessPiece getPiece(PiecePosition position) {

    }
}

class PiecePosition {
    private int x;
    private int y;

    PiecePosition(int onX, int onY) {
        this.x = onX;
        this.y = onY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {

    }
}

interface BishopMovement {
    default int getDiagonalMovesCount(PiecePosition position, PieceColor color,
                                      Map<String, ChessPiece> positions, int boardSize) {

    }

    default int getDiagonalCapturesCount(PiecePosition position, PieceColor color,
                                         Map<String, ChessPiece> positions, int boardSize) {

    }
}

interface RookMovement {
    default int getOrthogonalMovesCount(PiecePosition position, PieceColor color,
                                        Map<String, ChessPiece> positions, int boardSize) {

    }

    default int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
                                           Map<String, ChessPiece> positions, int boardSize) {

    }
}

abstract class ChessPiece {
    protected PiecePosition position;
    protected PieceColor color;

    ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
        this.position = piecePosition;
        this.color = pieceColor;
    }

    public abstract PiecePosition getPosition();

    public abstract PieceColor getColor();

    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);
}

class Knight extends ChessPiece {
    Knight(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public PiecePosition getPosition() {

    }

    @Override
    public PieceColor getColor() {

    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {

    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {

    }
}

class King extends ChessPiece {
    King(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public PiecePosition getPosition() {

    }

    @Override
    public PieceColor getColor() {

    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {

    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {

    }
}

class Pawn extends ChessPiece {
    Pawn(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public PiecePosition getPosition() {

    }

    @Override
    public PieceColor getColor() {

    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {

    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {

    }
}

class Bishop extends ChessPiece implements BishopMovement {
    Bishop(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public PiecePosition getPosition() {

    }

    @Override
    public PieceColor getColor() {

    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, color, positions, boardSize);
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, color, positions, boardSize);
    }
}

class Rook extends ChessPiece implements RookMovement {
    Rook(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public PiecePosition getPosition() {

    }

    @Override
    public PieceColor getColor() {

    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(position, color, positions, boardSize);
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(position, color, positions, boardSize);
    }
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    Queen(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public PiecePosition getPosition() {

    }

    @Override
    public PieceColor getColor() {

    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(position, color, positions, boardSize) + getDiagonalMovesCount(position, color,
                positions, boardSize);
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(position, color, positions, boardSize) + getOrthogonalCapturesCount(position,
                color, positions, boardSize);
    }
}

class InvalidBoardSizeException extends Exception {
    @Override
    public String getMessage() {
        return ("Invalid board size");
    }
}

class InvalidNumberOfPiecesException extends Exception {
    @Override
    public String getMessage() {
        return ("Invalid number of pieces");
    }
}

class InvalidPieceNameException extends Exception {
    @Override
    public String getMessage() {
        return ("Invalid piece name");
    }
}

class InvalidPieceColorException extends Exception {
    @Override
    public String getMessage() {
        return ("Invalid piece color");
    }
}

class InvalidPiecePositionException extends Exception {
    @Override
    public String getMessage() {
        return ("Invalid piece position");
    }
}

class InvalidGivenKingsException extends Exception {
    @Override
    public String getMessage() {
        return ("Invalid given Kings");
    }
}

class InvalidInputException extends Exception {
    @Override
    public String getMessage() {
        return ("Invalid input");
    }
}
