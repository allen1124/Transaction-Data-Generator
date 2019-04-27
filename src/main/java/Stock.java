public class Stock {
    private String ISINCode;
    private String stockName;
    private String stockCode;
    private String category;
    private int boardLot;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getISINCode() {
        return ISINCode;
    }

    public void setISINCode(String ISINCode) {
        this.ISINCode = ISINCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getBoardLot() {
        return boardLot;
    }

    public void setBoardLot(int boardLot) {
        this.boardLot = boardLot;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
