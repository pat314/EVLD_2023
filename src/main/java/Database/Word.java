package Database;

public class Word {
    private String Word_target;
    private String Word_explain;

    public Word() {
        Word_target = "";
        Word_explain = "";
    }

    public String getWord_target() {
        return Word_target;
    }

    public void setWord_target(String word_target) {
        Word_target = word_target;
    }

    public String getWord_explain() {
        return Word_explain;
    }

    public void setWord_explain(String word_explain) {
        Word_explain = word_explain;
    }
}