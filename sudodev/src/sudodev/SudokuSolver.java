package sudodev;
public class SudokuSolver {
    public static void main(String[] args) {
        int[][] initialGrid = {
            {1, 9, 5, 7, 3, 8, 4, 6, 2},
            {2, 6, 8, 4, 0, 9, 5, 7, 3},
            {3, 7, 4, 5, 2, 6, 9, 1, 0},
            {7, 5, 1, 3, 8, 2, 0, 4, 9},
            {4, 0, 9, 6, 5, 1, 2, 8, 7},
            {8, 2, 6, 9, 0, 7, 3, 5, 1},
            {5, 8, 2, 1, 9, 4, 7, 0, 6},
            {9, 0, 7, 8, 6, 3, 1, 2, 5},
            {6, 1, 3, 2, 0, 5, 8, 9, 4}
        };

        SudokuGrid sudokuGrid = new SudokuGrid(initialGrid);
        SudokuSolver solver = new SudokuSolver();

        System.out.println("Sudoku Tahtası (Başlangıç Durumu):");
        sudokuGrid.printGrid();

        System.out.println("\nÇözüm:");
        if (solver.solveSudoku(sudokuGrid)) {
            System.out.println("Sudoku çözüldü.");
            sudokuGrid.printGrid();
        } else {
            System.out.println("Bu sudoku çözülemez!!!");
        }
    }

    public boolean solveSudoku(SudokuGrid sudokuGrid) {
        return solve(sudokuGrid.getGrid(), sudokuGrid);
    }
    
    private boolean solve(int[][] grid, SudokuGrid sudokuGrid) {
        int[] emptyCell = findEmptyCell(grid);

        if (emptyCell == null) {
            // sudoku tamamlandı
            return true;
        }

        int row = emptyCell[0];
        int column = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(grid, row, column, num)) {
                grid[row][column] = num;

                if (solve(grid, sudokuGrid)) {
                    return true;
                }

                grid[row][column] = 0; 
            }
        }

        return false;
    }

    private boolean isSafe(int[][] grid, int row, int column, int num) {
        // satır kontrolü
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // sütun kontrolü
        for (int i = 0; i < 9; i++) {
            if (grid[i][column] == num) {
                return false;
            }
        }

        // kutunun kontrolü
        int boxStartRow = row - row % 3;
        int boxStartCol = column - column % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + boxStartRow][j + boxStartCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private int[] findEmptyCell(int[][] grid) {
        int[] cell = new int[2];
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (grid[row][column] == 0) {
                    cell[0] = row;
                    cell[1] = column;
                    return cell;
                }
            }
        }
        return null;
    }
}
//hocam sayıları değiştirdiğinizde bu sudoku çözülemez çıktısı alıyoruz.

