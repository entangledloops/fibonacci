import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

/**
 * Created by snd on 8/11/2015.
 */
public class ComplexityFormula
{
  public static final BigInteger two    = new BigInteger("" + 2);
  public static final BigInteger three  = new BigInteger("" + 3);
  public static final BigInteger four   = new BigInteger("" + 4);
  public static final BigInteger five   = new BigInteger("" + 5);
  public static final BigInteger seven  = new BigInteger("" + 7);
  public static final BigInteger eight  = new BigInteger("" + 8);
  public static final BigInteger twelve = new BigInteger("" + 12);

  // f(n) =(4/3)(2n + 3n^2 + n^3) = (8n + 12n^2 + 4n^3)/3
  public static BigInteger f(final BigInteger n)
  {
    final BigInteger nn = n.multiply(n);
    final BigInteger nnn = nn.multiply(n);
    final BigInteger term1 = n.multiply(eight);
    final BigInteger term2 = nn.multiply(twelve);
    final BigInteger term3 = nnn.multiply(four);
    final BigInteger numerator = term1.add(term2.add(term3));
    return numerator.divide(three);
  }

  public static void main(String[] args)
  {
    BufferedWriter writer;
    try { writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("graph-100000.csv"), "UTF-8")); }
    catch (Exception e) { e.printStackTrace(); return; }

    BigInteger result, fn, gn = f(three);
    for (int i=5; i < 1000000; i += 2)
    {
      fn = f(new BigInteger("" + i));
      //if (i % 2 == 0) { continue; }//gn = fn; continue; } // evens are never prime

      result = fn.divide( gn.gcd(fn.add(gn)) ); // result = f(n) / gcd( f(n)+g(n), g(n) )
      while (!result.equals(two) && result.mod(two).equals(BigInteger.ZERO)) result = result.divide(two);
      while (!result.equals(three) && result.mod(three).equals(BigInteger.ZERO)) result = result.divide(three);
      while (!result.equals(five) && result.mod(five).equals(BigInteger.ZERO)) result = result.divide(five);
      while (!result.equals(seven) && result.mod(seven).equals(BigInteger.ZERO)) result = result.divide(seven);

      String out = i + "," + result.toString(10) + "\n";
      try { writer.write(out); } catch (Exception e) { e.printStackTrace(); }
      System.out.print(out);

      gn = fn;
    }

    try { writer.close(); } catch (Exception e) { e.printStackTrace(); }
  }
}
