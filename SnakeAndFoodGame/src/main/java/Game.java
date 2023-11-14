public class Game {
    private Snake snake;
    private Board board;
    private boolean gameOver;
    private int direction;
    public static final int DIRECTION_NONE = 0, DIRECTION_RIGHT = 1, DIRECTION_LEFT = -1, DIRECTION_UP = 2, DIRECTION_DOWN = -2;

    public Game(Snake snake, Board board) {
        this.snake = snake;
        this.board = board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Cell getNextCell(Cell currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();

        switch (direction) {
            case DIRECTION_RIGHT:
                col++;
                break;
            case DIRECTION_LEFT:
                col--;
                break;
            case DIRECTION_UP:
                row--;
                break;
            case DIRECTION_DOWN:
                row++;
                break;
        }

        Cell nextCell = this.board.getBoard()[row][col];
        return nextCell;
    }

    public void update() {
        if (!this.gameOver) {
            if (this.direction != DIRECTION_NONE) {
                Cell nextCell = getNextCell(snake.getHead());

                if (snake.gameOver(nextCell)) {
                    setDirection(DIRECTION_NONE);
                    gameOver = true;
                } else {
                    snake.move(nextCell);
                    if (nextCell.getCellType() == CellType.FOOD_CELL) {
                        snake.grow();
                        board.generateFood();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Cell initPos = new Cell(0, 0);
        Snake initSnake = new Snake(initPos);
        Board board = new Board(10, 10);
        Game newGame = new Game(initSnake, board);
        newGame.setGameOver(false);
        newGame.setDirection(DIRECTION_RIGHT);

        for (int i = 0; i < 5; i++) {
            if (i == 2)
                newGame.board.generateFood();
            newGame.update();
            if (i == 3)
                newGame.setDirection(DIRECTION_RIGHT);
            if (newGame.gameOver)
                break;
        }
    }
}
