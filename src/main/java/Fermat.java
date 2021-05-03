import javafx.scene.Node;
import parcs.AM;
import parcs.AMInfo;

import java.math.BigInteger;

class Fermat implements AM {

  private static final BigInteger ONE = BigInteger.ONE;

  public void run(AMInfo info) {
    Node nn = (Node)info.parent.readObject();
    System.out.println("[" + nn.getId() + "] Build started.");
    BigInteger n = (BigInteger) info.parent.readObject();
    System.out.println("n="+n);
    BigInteger res1 = FermatFactor(n);
    System.out.println("res1="+res1);
    BigInteger res2 = n.divide(res1);
    System.out.println("res2="+res2);
    var p = info.createPoint();
    System.out.println("p created");
    var c = p.createChannel();
    p.execute("Algorithm");
    c.write(n);
    info.parent.write(res1);
    info.parent.write(res2);
  }

  private BigInteger FermatFactor(BigInteger n) {
    var a = sqrt(n);
    var b2 = a.multiply(a).subtract(n);
    while (!isSquare(b2)) {
      a = a.add(ONE);
      b2 = a.multiply(a).subtract(n);
    }
    return a.subtract(sqrt(b2));
  }

  private BigInteger sqrt(BigInteger x) {
    if (x.compareTo(BigInteger.ZERO) < 0) {
      throw new IllegalArgumentException("Negative argument.");
    }

    if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) {
      return x;
    }
    BigInteger two = BigInteger.valueOf(2L);
    BigInteger y;
    // starting with y = x / 2 avoids magnitude issues with x squared
    y = x.divide(two);
    while (y.compareTo(x.divide(y)) > 0) {
      y = ((x.divide(y)).add(y)).divide(two);
    }
    if (x.compareTo(y.multiply(y)) == 0) {
      return y;
    } else {
      return y.add(BigInteger.ONE);
    }
  }

  private boolean isSquare(BigInteger n) {
    var sqr = sqrt(n);
    return sqr.multiply(sqr).equals(n) || (sqr.add(ONE)).multiply(sqr.add(ONE)).equals(n);
  }
}