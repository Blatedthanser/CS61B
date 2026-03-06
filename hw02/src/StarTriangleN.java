public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      for (int i = 0; i < N; i ++) {
         IO.println(" " .repeat(N - i) + "*".repeat(i + 1));
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}