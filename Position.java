/* Model : トラップ使用時に使用 */
class Position {
    protected int row, col;

    public Position() {
        row = col = 0;
    }

    public Position(int x, int y) {
        row = x;
        col = y;
    }

    public void reset(int x, int y) {
        row = x;
        col = y;
    }

    public void plus(int x, int y) {
        row += x;
        col += y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void print_position() {
        System.out.println("row : " + row);
        System.out.println("col : " + col);
    }
}