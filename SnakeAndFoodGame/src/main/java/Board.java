public class Board {
    private Cell[][] board;
    private final int ROW_SIZE, COLUMN_SIZE;

    public Board(int rowSize, int columnSize) {
        this.ROW_SIZE = rowSize;
        this.COLUMN_SIZE = columnSize;

        this.board = new Cell[ROW_SIZE][COLUMN_SIZE];
        for (int row = 0; row < this.ROW_SIZE; row++) {
            for (int col = 0; col < this.COLUMN_SIZE; col++) {
                this.board[row][col] = new Cell(row, col);
            }
        }
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public void generateFood() {
        int row, col;

        do {
            row = (int) (Math.random() * this.ROW_SIZE);
            col = (int) (Math.random() * this.COLUMN_SIZE);
        } while (board[row][col].getCellType() != CellType.EMPTY_CELL);

        board[row][col].setCellType(CellType.FOOD_CELL);
    }
}

