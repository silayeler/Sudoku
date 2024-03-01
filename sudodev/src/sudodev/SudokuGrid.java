package sudodev;
public class SudokuGrid {
    private int[][] grid;
    private int[][] startGrid; // başlangıç durumu

    // sabitlerimiz
    private static final int SIZE = 9; // ızgara boyutu
    private static final int DIGIT_RANGE = 9; // rakam aralığı

    public SudokuGrid(int[][] initialGrid) {
        this.grid = new int[SIZE][SIZE];
        this.startGrid = new int[SIZE][SIZE];

        // giriş durumunu kopyala
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.grid[i][j] = initialGrid[i][j];
                this.startGrid[i][j] = initialGrid[i][j];
            }
        }
    }

    public int[][] getGrid() {
        return grid;
    }

    public void printGrid() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    public boolean isSolvable() {
        SudokuSolver solver = new SudokuSolver();
        int[][] tempGrid = new int[SIZE][SIZE];

        // board ı kopyala
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tempGrid[i][j] = this.grid[i][j];
            }
        }

        // çözüm denemesi yap
        return solver.solve(tempGrid, this);
    }

    public void setNumber(int row, int column, int number) {
        // sayiyi değiştirirken başlangıç durumunu güncelle
        this.startGrid[row][column] = number;
        this.grid[row][column] = number;
    }

    public boolean isNumberChangeAllowed(int row, int column) {
        // başlangıç durumunda değişiklik yapılmasına izin verilip verilmediğini kontrol et
        return startGrid[row][column] == 0;
    }

    public SudokuGrid copy() {
        int[][] copiedGrid = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copiedGrid[i][j] = this.grid[i][j];
            }
        }

        return new SudokuGrid(copiedGrid);
    }

    // yerel Point sınıfı tanımı  !!!! point ile sudokunun x y koordinatlarina ulastık.
    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Point findEmptyCell() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (grid[row][column] == 0) {
                    return new Point(row, column);
                }
            }
        }
        return new Point(-1, -1);
    }

    public void fillCell(int r, int c, int digit) {
        grid[r][c] = digit;
    }

    public boolean givesConflict(int r, int c, int digit) {
        return rowConflict(r, digit) || colConflict(c, digit) || boxConflict(r, c, digit);
    }

    private boolean rowConflict(int r, int digit) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[r][i] == digit) {
                return true;
            }
        }
        return false;
    }

    private boolean colConflict(int c, int digit) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][c] == digit) {
                return true;
            }
        }
        return false;
    }

    private boolean boxConflict(int r, int c, int digit) {
        int boxStartRow = r - r % 3;
        int boxStartCol = c - c % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + boxStartRow][j + boxStartCol] == digit) {
                    return true;
                }
            }
        }
        return false;
    }
}
