import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class Main {
    private static Board chessBoard;

    private Main() {
    }

    private static final int LOWER = 3;
    private static final int HIGHER = 1000;

    public static void main(String[] args) {
        try {
            File input = new File("input.txt");
            File output = new File("output.txt");
            Scanner scanner = new Scanner(System.in);

            int n = scanner.nextInt();
            chessBoard = new Board(n);
            if (n < LOWER || n > HIGHER) {
                throw new InvalidBoardSizeException();
            }

            int m = scanner.nextInt();
            if (m < 2 || m > n * n) {
                throw new InvalidNumberOfPiecesException();
            }

            ChessPiece[] pieces = new ChessPiece[m];
            int amountOfKings = 0;
            int amountOfPieces = 0;
            while (m > 0) {
                String piece = scanner.next();
                PieceColor pieceColor = PieceColor.parse(scanner.next());
                ChessPiece chessPiece = null;

                int x;
                int y;
                x = scanner.nextInt();
                y = scanner.nextInt();
                PiecePosition piecePosition = new PiecePosition(x, y);

                if (x < 1 || y < 1 || x > n || y > n) {
                    throw new InvalidPiecePositionException();
                }

                switch (piece) {
                    case "Knight":
                        chessPiece = new Knight(piecePosition, pieceColor);
                        break;
                    case "King":
                        amountOfKings++;
                        chessPiece = new King(piecePosition, pieceColor);
                        break;
                    case "Pawn":
                        chessPiece = new Pawn(piecePosition, pieceColor);
                        break;
                    case "Bishop":
                        chessPiece = new Bishop(piecePosition, pieceColor);
                        break;
                    case "Rook":
                        chessPiece = new Rook(piecePosition, pieceColor);
                        break;
                    case "Queen":
                        chessPiece = new Queen(piecePosition, pieceColor);
                        break;
                    default:
                        throw new InvalidPieceNameException();
                }

                pieces[amountOfPieces] = chessPiece;
                if (chessBoard.getPiece(piecePosition) == null) {
                    chessBoard.addPiece(chessPiece);
                } else {
                    throw new InvalidPiecePositionException();
                }
                amountOfPieces++;
                m--;
            }
            if (amountOfKings > 2) {
                throw new InvalidGivenKingsException();
            }
            /*if (amountOfPieces != m) {
                throw new InvalidNumberOfPiecesException();
            }*/
            for (ChessPiece chessPiece : pieces) {
                System.out.println(chessBoard.getPiecePossibleMovesCount(chessPiece) + " "
                        + chessBoard.getPiecePossibleCapturesCount(chessPiece));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}

enum PieceColor {
    WHITE,
    BLACK;

    public static PieceColor parse(String color) throws InvalidPieceColorException {
        switch (color) {
            case "White":
                return PieceColor.WHITE;
            case "Black":
                return PieceColor.BLACK;
            default:
                throw new InvalidPieceColorException();
        }
    }
}

class Board {
    private Map<String, ChessPiece> positionsToPieces = new HashMap<>();
    private int size;

    Board(int boardSize) {
        size = boardSize;
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(positionsToPieces, size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(positionsToPieces, size);
    }

    public void addPiece(ChessPiece piece) {
        PiecePosition position = piece.getPosition();
        positionsToPieces.put(position.toString(), piece);
    }

    public ChessPiece getPiece(PiecePosition position) {
        return positionsToPieces.get(position.toString());
    }
}

class PiecePosition {
    private int x;
    private int y;

    PiecePosition(int onX, int onY) {
        x = onX;
        y = onY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return Integer.toString(x) + "," + Integer.toString(y);
    }
}

interface BishopMovement {
    default int getDiagonalMovesCount(PiecePosition position, PieceColor color,
                                      Map<String, ChessPiece> positions, int boardSize) {
        return 0;
    }

    default int getDiagonalCapturesCount(PiecePosition position, PieceColor color,
                                         Map<String, ChessPiece> positions, int boardSize) {
        return 0;
    }
}

interface RookMovement {
    default int getOrthogonalMovesCount(PiecePosition position, PieceColor color,
                                        Map<String, ChessPiece> positions, int boardSize) {
        return 0;
    }

    default int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
                                           Map<String, ChessPiece> positions, int boardSize) {
        return 0;
    }
}

abstract class ChessPiece {
    protected PiecePosition position;
    protected PieceColor color;

    ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
        position = piecePosition;
        color = pieceColor;
    }

    public PiecePosition getPosition() {
        return position;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);
}

class Knight extends ChessPiece {
    Knight(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return 0;
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return 0;
    }
}

class King extends ChessPiece {
    King(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    private final int[] xMove = {0, 1, 1, -1, -1, 0, 1, -1};
    private final int[] yMove = {1, 0, 1, -1, 0, -1, -1, 1};
    private final int moves = 8;

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int i = 0; i < moves; i++) {
            int x = getPosition().getX();
            int y = getPosition().getY();
            ChessPiece cage = positions.get((new PiecePosition(x + xMove[i], y + yMove[i])).toString());
            if (x + xMove[i] > 0 && x + xMove[i] <= boardSize && y + yMove[i] > 0 && y + yMove[i] <= boardSize
                    && cage == null) {
                count++;
            }
        }
        count += getCapturesCount(positions, boardSize);
        return count;
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int i = 0; i < moves; i++) {
            int x = getPosition().getX();
            int y = getPosition().getY();
            ChessPiece cage = positions.get((new PiecePosition(x + xMove[i], y + yMove[i])).toString());
            if (cage != null && cage.getColor() != getColor()) {
                count++;
            }
        }
        return count;
    }
}

class Pawn extends ChessPiece {
    Pawn(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = getPosition().getX();
        int y = getPosition().getY();

        if (getColor() == PieceColor.WHITE && y + 1 <= boardSize) {
            ChessPiece cage = positions.get((new PiecePosition(x, y + 1)).toString());
            if (cage == null) {
                count++;
            }
        } else if (getColor() == PieceColor.BLACK && y - 1 > 0) {
            ChessPiece cage = positions.get((new PiecePosition(x, y - 1)).toString());
            if (cage == null) {
                count++;
            }
        }
        count += getCapturesCount(positions, boardSize);

        return count;
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = getPosition().getX();
        int y = getPosition().getY();

        if (getColor() == PieceColor.WHITE && y + 1 <= boardSize) {
            ChessPiece cageLeft = positions.get((new PiecePosition(x - 1, y + 1)).toString());
            if (cageLeft != null && cageLeft.getColor() != getColor()) {
                count++;
            }
            ChessPiece cageRight = positions.get((new PiecePosition(x + 1, y + 1)).toString());
            if (cageRight != null && cageRight.getColor() != getColor()) {
                count++;
            }
        }

        if (getColor() == PieceColor.BLACK && y - 1 > 0) {
            ChessPiece cageLeft = positions.get((new PiecePosition(x - 1, y - 1)).toString());
            if (cageLeft != null && cageLeft.getColor() != getColor()) {
                count++;
            }
            ChessPiece cageRight = positions.get((new PiecePosition(x + 1, y - 1)).toString());
            if (cageRight != null && cageRight.getColor() != getColor()) {
                count++;
            }
        }

        return count;
    }
}

class Bishop extends ChessPiece implements BishopMovement {
    Bishop(PiecePosition position, PieceColor color) {
        super(position, color);
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
