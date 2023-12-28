package Speech;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.PrintStream;

public class TextToSpeech {
    private static final String VOICES_KEY = "freetts.voices";
    private static final String VOICE_VALUE = "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";

    public static void Speech(String text) throws Exception {
        try {
            System.setProperty(VOICES_KEY, VOICE_VALUE);

            Voice voice = VoiceManager.getInstance().getVoice("kevin16");
            if (voice != null) {
                voice.allocate();
                voice.speak(text);
                voice.deallocate();
            }
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        }
    }
}