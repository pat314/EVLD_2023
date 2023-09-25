public class Word {
    /**
     * English word.
     */
    public StringBuffer wordTarget = new StringBuffer();
    /**
     * Vietnamese word.
     */
    public Word() {};
    public StringBuffer wordExplain = new StringBuffer();
    public Word (StringBuffer wordTarget, StringBuffer wordExplain) {
        this.wordTarget = wordTarget;
        toLowerCase();
        this.wordExplain = wordExplain;
    }
    public void toLowerCase() {
        if (wordTarget != null) {
            for (int i = 0; i < wordTarget.length(); i++) {
                char c = wordTarget.charAt(i);
                if (Character.isUpperCase(c)) {
                    wordTarget.setCharAt(i, Character.toLowerCase(c));
                }
            }
        }
    }
    public void setWordTarget(StringBuffer wordTarget) {
        this.wordTarget = wordTarget;
        toLowerCase();
    }
    public StringBuffer getWordTarget() {
        return wordTarget;
    }
    public StringBuffer getWordExplain() {
        return wordExplain;
    }
    public void setWordExplain(StringBuffer wordExplain) {
        toLowerCase();
        this.wordExplain = wordExplain;
    }
}
