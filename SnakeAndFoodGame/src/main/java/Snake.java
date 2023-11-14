import java.util.LinkedList;

public class Snake {
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
