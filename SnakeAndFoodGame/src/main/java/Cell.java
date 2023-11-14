public class Cell {
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

