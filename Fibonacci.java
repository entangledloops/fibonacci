/**
 * @author Stephen Dunn
 * @date 8/21/15
 *
 * This app computes the requested Nth value of the Fibonacci sequence in an
 * iterative arbitrary-precision manner, and also displays:
 * (f(n) / f(n-1)) = golden ratio approximation = ~
 *
 * It can optionally render all values calculated along the way to f(n).
 */

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

/*
<applet code="Fibonacci.class" CodeBase="" width=1200 height=800></applet>
*/

public class Fibonacci extends Applet
{
  public static        boolean     applet                = true;
  public static        boolean     verbose               = true;
  public static final  MathContext precision             = new MathContext(1000, RoundingMode.HALF_UP);
  private static final String      verboseMsg            = "Would you like to see all the calculations? (y/n): ";
  private static final String      inputNumberMsg        = "Enter the Fibonacci number you would like to know (type 'q' to quit): ";
  private static final String      parseBigIntegerErrMsg = "Oops! You didn't provide a valid number, try again: ";
  private static final String      parseBooleanErrMsg    = "Oops! I didn't understand that. Just enter 'y' or 'n': ";
  private static       String      output                = "";

  // applet stuff:
  private final  Highlighter                                highlighter  = new RowHighlighter();
  private final  DefaultHighlighter.DefaultHighlightPainter blackPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.BLACK);
  private final  DefaultHighlighter.DefaultHighlightPainter bluePainter  = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
  private        JTextArea                                  textArea     = new JTextArea(1000, 100);
  private JLabel lblF0, lblF1, lblIterations;
  private JTextArea textF0, textF1, textIterations;
  private JCheckBox chkShowWork;
  private JButton btnRun;
  private static int appletX = 10, appletY = 20, spacing = 75;
  private final Font APPLET_FONT;
  public BigInteger f0 = BigInteger.ZERO, f1 = BigInteger.ONE;

  static class FibonacciResult
  {
    public BigInteger value, prev;
    public BigDecimal ratio;
  }

  public Fibonacci()
  {
    if (applet) APPLET_FONT = new Font(Font.MONOSPACED, Font.BOLD, 12);
    else APPLET_FONT = null;

    if (applet)
    {
      setBackground(Color.BLACK);
      resize(1200, 800);
      setLayout(new BorderLayout());
      textArea.setEditable(false);
      textArea.setFont(APPLET_FONT);
      textArea.setBorder(null);
      textArea.setBackground(Color.BLACK);
      textArea.setForeground(Color.WHITE);
      textArea.setHighlighter(highlighter);
      JScrollPane scroll = new JScrollPane(textArea);
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      JPanel panel = new JPanel(new BorderLayout());
      JPanel northPanel = new JPanel(new FlowLayout());

      lblF0 = new JLabel("f(0) = ");
      textF0 = new JTextArea("0");
      textF0.setColumns(10);
      lblF1 = new JLabel("f(1) = ");
      textF1 = new JTextArea("1");
      textF1.setColumns(10);
      lblIterations = new JLabel("f(n) = ");
      textIterations = new JTextArea("47");
      textIterations.setColumns(10);
      chkShowWork = new JCheckBox("show work?");
      chkShowWork.setSelected(true);
      btnRun = new JButton("Go!");
      btnRun.addActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          runApplet();
        }
      });

      northPanel.add(lblF0);
      northPanel.add(textF0);
      northPanel.add(lblF1);
      northPanel.add(textF1);
      northPanel.add(lblIterations);
      northPanel.add(textIterations);
      northPanel.add(chkShowWork);
      northPanel.add(btnRun);

      panel.add(northPanel, BorderLayout.NORTH);
      panel.add(scroll, BorderLayout.CENTER);
      add(panel, BorderLayout.CENTER);

      runApplet();
    }
  }

  public void runApplet()
  {
    f0 = new BigInteger(textF0.getText(), 10);
    f1 = new BigInteger(textF1.getText(), 10);
    verbose = chkShowWork.isSelected();
    spacing = 75; appletX = 10; appletY = 20;
    textArea.setText("");
    output = "";
    run(new String[]{textIterations.getText()});
    repaint();
  }

  public void run(String[] args)
  {
    final Scanner scanner = applet ? null : new Scanner(System.in, "UTF-8");
    FibonacciResult result;
    BigInteger n;

    // set verbosity
    if (!applet && args.length == 1) verbose = false;
    else if (!applet && args.length > 1 && args[1].trim().equalsIgnoreCase("verbose")) verbose = true;

    // grab iterations
    if ((args.length == 0) || ((n = parseBigInteger(args[0])) == null)) n = getUserInput(scanner);

    // run
    result = fibonacci(f0, f1, n);
    if (!verbose)
    {
      String msg = "f(" + result.value.toString(10) + ") = " + result.value.toString(10);
      if (result.ratio != null)
      {
        msg += ", (" + result.value.toString(10) + "/" + result.prev.toString(10) +
            ") = " + result.ratio.toString();
      }
      msg += "\n";

      if (applet) output += msg;
      else System.out.print(msg);
    }

    if (applet) return;
    System.out.print("\n");
  }

  public static FibonacciResult fibonacci(final BigInteger f0, final BigInteger f1, final BigInteger n)
  {
    if (applet) output = "";

    BigInteger prev = f0, next = f1;
    BigInteger sum = f0.add(f1);

    final FibonacciResult result = new FibonacciResult();
    result.value = sum;

    if (verbose)
    {
      final String f0_string = "f(" + f0.toString(10) + ") = " + f0.toString(10) + "\n";
      if (applet) output += f0_string;
      else System.out.print(f0_string);

      if (n.equals(BigInteger.ZERO)) return result;

      final String f1_string = "f(" + f1.toString(10) + ") = " + f1.toString(10) + "\n";
      if (applet) output += f1_string;
      else System.out.print(f1_string);

      if (n.equals(BigInteger.ONE)) return result;
    }

    for (BigInteger i = new BigInteger("2"); i.compareTo(n) <= 0; i = i.add(BigInteger.ONE))
    {
      sum = prev.add(next);

      if (verbose || i.compareTo(n) == 0)
        result.ratio = new BigDecimal(sum).divide(new BigDecimal(next), precision);

      if (verbose)
      {
        String prefix = "f(" + i.toString(10) + ") = " + sum.toString(10) + " = " + prev.toString(10) + " + " + next.toString(10);
        int spaces = spacing - prefix.length();
        while (spaces <= 0) { spacing += 75; spaces = spacing - prefix.length(); }
        for (int j = 0; j < spaces; ++j) prefix += " ";
        final String output = prefix + sum.toString(10) + " / " + next.toString(10) + " = " + result.ratio.toString() + "\n";

        if (applet) Fibonacci.output += output;
        else System.out.print(output);
      }

      result.value = sum;
      result.prev = next;
      prev = next;
      next = sum;
    }

    return result;
  }

  public static FibonacciResult fibonacci(final BigInteger n)
  {
    return fibonacci(BigInteger.ZERO, BigInteger.ONE, n);
  }

  private static boolean inputBoolean(final Scanner scanner)
  {
    boolean result = false, done = false;
    if (!applet) System.out.print(verboseMsg);

    while (!done)
    {
      String input;
      if (applet)
        input = JOptionPane.showInputDialog(null, verboseMsg, "y");
      else input = scanner.nextLine();
      if (input == null) System.exit(0);

      input = input.trim().toLowerCase();
      if (input.equals("y") || input.equals("yes"))
      {
        result = true;
        done = true;
      }
      else if (input.equals("n") || input.equals("no"))
      {
        result = false;
        done = true;
      }
      else
      {
        if (applet)
          JOptionPane.showMessageDialog(null, parseBooleanErrMsg.replace(": ", "."), "Oops!", JOptionPane.ERROR_MESSAGE);
        else System.err.print(parseBooleanErrMsg);
      }
    }

    return result;
  }

  private static BigInteger parseBigInteger(final String s)
  {
    BigInteger n = null;
    try { n = new BigInteger(s); }
    catch (Exception e)
    {
      if (applet)
        JOptionPane.showMessageDialog(null, parseBigIntegerErrMsg.replace(": ", "."), "Oops!", JOptionPane.ERROR_MESSAGE);
      else System.err.print(parseBigIntegerErrMsg);
    }
    return n;
  }

  private static BigInteger inputBigInteger(final Scanner scanner)
  {
    BigInteger n = null;
    while (n == null)
    {
      String input;
      if (applet)
      {
        input = JOptionPane.showInputDialog(null, inputNumberMsg, "20");
      }
      else
      {
        System.out.printf(inputNumberMsg);
        input = scanner.nextLine();
      }

      if (input == null) System.exit(0);
      input = input.trim();
      if (input.equalsIgnoreCase("q")) System.exit(0);
      n = parseBigInteger(input);
    }
    return n;
  }

  private static BigInteger getUserInput(final Scanner scanner)
  {
    BigInteger n = inputBigInteger(scanner);
    verbose = inputBoolean(scanner);
    return n;
  }

  public void paint(Graphics g)
  {
    super.paint(g);
    textArea.setText(output);

    int start = 0, end = 0;
    boolean even = true;
    while ((end = output.indexOf('\n', start)) >= 0)
    {
      even = !even;
      DefaultHighlighter.DefaultHighlightPainter painter = even ? bluePainter : blackPainter;
      try { highlighter.addHighlight(start, end+1, painter); }
      catch (Throwable t) { t.printStackTrace(); }
      start = end+1;
    }
  }

  public void init()
  {

  }

  public static void main(String[] args)
  {
    Fibonacci app = new Fibonacci();
    app.run(args);
  }
}

/**
 * Some of the highlighter code was plagiarized and modified from StackOverflow posts now lost.
 * If you recognize your own code snippets, send me the post link and I'll add it. Usually I
 * document my sources in a comment directly, but as this was written some time ago and I can't
 * remember where I acquired it from or why I didn't comment the link, I'm assuming I heavily
 * modified the original post or just goofed and forgot.
 */
class RowHighlighter extends DefaultHighlighter
{

  private JTextComponent component;

  /**
   * @see javax.swing.text.DefaultHighlighter#install(javax.swing.text.JTextComponent)
   */
  @Override
  public final void install(final JTextComponent c)
  {
    super.install(c);
    this.component = c;
  }

  /**
   * @see javax.swing.text.DefaultHighlighter#deinstall(javax.swing.text.JTextComponent)
   */
  @Override
  public final void deinstall(final JTextComponent c)
  {
    super.deinstall(c);
    this.component = null;
  }

  /**
   * Same algo, except width is not modified with the insets.
   *
   * @see javax.swing.text.DefaultHighlighter#paint(java.awt.Graphics)
   */
  @Override
  public final void paint(final Graphics g)
  {
    final Highlighter.Highlight[] highlights = getHighlights();
    final int len = highlights.length;
    for (int i = 0; i < len; i++)
    {
      Highlighter.Highlight info = highlights[i];
      if (info.getClass().getName().indexOf("LayeredHighlightInfo") > -1)
      {
        // Avoid allocing unless we need it.
        final Rectangle a = this.component.getBounds();
        final Insets insets = this.component.getInsets();
        a.x = insets.left;
        a.y = insets.top;
        // a.width -= insets.left + insets.right + 100;
        a.height -= insets.top + insets.bottom;
        for (; i < len; i++)
        {
          info = highlights[i];
          if (info.getClass().getName().indexOf(
              "LayeredHighlightInfo") > -1)
          {
            final Highlighter.HighlightPainter p = info
                .getPainter();
            p.paint(g, info.getStartOffset(), info
                .getEndOffset(), a, this.component);
          }
        }
      }
    }
  }
}