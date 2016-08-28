package pascal_parser;

import pascal_parser.lexer.Lexer;

import java.io.PushbackReader;

class PrintLexer extends Lexer
{
  PrintLexer(PushbackReader reader)
  {
    super(reader);
  }

  protected void filter()
  {
    System.out.println(token.getClass() +
                       ", state : " + state.id() +
                       ", text : [" + token.getText() + "]");
  }
}
