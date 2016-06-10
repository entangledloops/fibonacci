import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;

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
