public class Points {
    private final int rowNumber;
    private final int columnNumber;
    private Points parent;
    public String direction;

    public Points(int rowNumber, int columnNumber){
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }

    public Points getParent() {
        return parent;
    }

    public void setParent(Points parent) {
        this.parent = parent;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

}
