class Main {
  public static void main (String [] args) {

    // on teste si le parseur est OK :
    try {
      new Parser();
    } catch(Exception e) {
      e.printStackTrace();
    }

    // on lance l'interface
    Interface window = new Interface();
  }
}
