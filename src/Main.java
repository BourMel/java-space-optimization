class Main {
  public static void main (String [] args) {
    System.out.println("Hello world!");
    try {
      new Parser();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
