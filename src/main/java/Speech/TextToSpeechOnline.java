package Speech;

import com.example.final_dictionary.AutomaticSoundController;
import com.voicerss.tts.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;

public class TextToSpeechOnline {
    public static void startPlaying(byte[] audio) {
        try {
            AudioInputStream ais = new AudioInputStream(
                    new ByteArrayInputStream(audio),
                    new javax.sound.sampled.AudioFormat(44100, 16, 2, true, false),
                    audio.length
            );
            javax.sound.sampled.AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speaker = (SourceDataLine) AudioSystem.getLine(info);
            speaker.open(format);
            speaker.start();
            int buffer;
            byte[] data = new byte[4096];
            while ((buffer = ais.read(data)) != -1) speaker.write(data, 0, buffer);
            speaker.drain();
            speaker.close();
            ais.close();
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        }
    }

    public static void textToSpeech(String text) throws Exception {
        VoiceProvider tts = new VoiceProvider("4f17198eb22e43a9bf77e5ee3bf2e5a7");

        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);

        if (AutomaticSoundController.isMan)
            params.setVoice("Mike");
        else
            params.setVoice("Mary");

        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
        byte[] voice = tts.speech(params);
        startPlaying(voice);
    }

    public static void textToSpeechVie(String text) throws Exception {
        VoiceProvider tts = new VoiceProvider("4f17198eb22e43a9bf77e5ee3bf2e5a7");

        VoiceParameters params = new VoiceParameters(text, Languages.Vietnamese);

        if (AutomaticSoundController.isMan)
            params.setVoice("Mike");
        else
            params.setVoice("Mary");

        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
        byte[] voice = tts.speech(params);
        startPlaying(voice);
    }
}