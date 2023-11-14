# Snake & Food Game in Java

Created Snake and Food Game in Java using **OOPs Design**

In Snake & Food Game, we have Cell, Snake, Board and Game Logic. Now let's talk about each item individually.

## CellType

A Cell can be empty, or it may contain the food or it may be part of snake. As these Celltypes are fixed so `enum` is best datatype to store celltypes.

```java
enum CellType {
    EMPTY_CELL,
    FOOD_CELL,
    SNAKE_CELL,
}
```

## Cell

A cell have three main property it's row number, column number and cell type (EMPTY_CELL, FOOD_CELL or SNAKE_CELL). 

Initially all Cells of the board will be of type EMPTY_CELL. Also we will implement getter and setter method for celltype, row and column.

```java
class Cell {
    private final int row, column;
    private CellType type;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.setCellType(CellType.EMPTY_CELL);
    }

    public CellType getCellType() {
        return this.type;
    }

    public void setCellType(CellType cellType) {
        this.type = cellType;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}

```

## Snake

Snake will be list of cells (and all these cell will be of cell type SNAKE_CELL). 

Which will be ideal list for our problem statement? 
- We have to remove the cell from the end and add into start of the list. `LinkedList` take `O(1)` to insert and delete item from start and end, while `ArrayList` take `O(N)`. So we will use LinkedList.

Also, Here We will implement move, grow & gameOver method and getter and setter of snake. 

```java
class Snake {
    private LinkedList<Cell> snakeList = new LinkedList<>();
    private Cell head;

    public Snake(Cell initialPosition) {
        this.head = initialPosition;
        this.head.setCellType(CellType.SNAKE_CELL);
        snakeList.add(head);
    }

    public void grow() {
        snakeList.add(head);
    }

    public void move(Cell nextCell) {
        snakeList.add(nextCell);
        nextCell.setCellType(CellType.SNAKE_CELL);
        head = nextCell;

        Cell tail = snakeList.removeLast();
        tail.setCellType(CellType.EMPTY_CELL);
    }

    public boolean gameOver(Cell nextCell) {
        return nextCell.getCellType() == CellType.SNAKE_CELL;
    }

    public LinkedList<Cell> getSnakeList() {
        return this.snakeList;
    }

    public Cell getHead() {
        return this.head;
    }
}
```

## Board

Board is the grid of Cells. We will ask the number of rows and columns from the user and initialise the board using the constructor function. 

Also we will implement the `generateFood` method, which will generate the food in one cell in the entire grid. But here we have to make sure that cell is initially Empty_Cell not SNAKE_Cell.

```java
class Board {
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

```


## Game

This is the main Game class which interact with the Snake, Board and game logic. It includes methods for updating the game state, checking for game over conditions and handling user inputs.

```java

class Game {
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
```

