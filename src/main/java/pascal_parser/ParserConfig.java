package pascal_parser;

public class ParserConfig {

    private boolean dumpAst = false;
    private String[] args;

    public boolean isDumpAst() {
        return dumpAst;
    }

    public void setDumpAst(boolean dumpAst) {
        this.dumpAst = dumpAst;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
