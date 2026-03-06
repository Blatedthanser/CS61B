public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      for (int i = 0; i < 5; i ++) {
         IO.println(" " .repeat(4 - i) + "*".repeat(i + 1));
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}