package com.example.app;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;
import javafx.scene.control.Alert;

// phát âm
public class Speaker {
    private static final String API_KEY = "e087180b714c4af48d7b30dbb9200f06";
    private static final String AUDIO_PATH = String.valueOf(Main.class.getResource("media/audio.wav"));

    public static String language = "en-gb";
    public static String Name = "Linda";

    public static void speakWord(String word) throws Exception {
        Thread thread = new Thread(() -> {
            try {
                VoiceProvider tts = new VoiceProvider(API_KEY);

                VoiceParameters params = new VoiceParameters(word, Languages.English_UnitedStates);
                params.setCodec(AudioCodec.WAV);
                params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
                params.setLanguage(language);
                params.setVoice(Name);
                params.setBase64(false);
                params.setSSML(false);
                params.setRate(0);

                byte[] voice = tts.speech(params);

                // Play the voice
                playAudio(voice);

                FileOutputStream fos = new FileOutputStream(AUDIO_PATH);
                fos.write(voice, 0, voice.length);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText("Error");
                alert1.setContentText("Lỗi kết nối mạng !");
                alert1.show();
            }
        });

        thread.start();
    }

    private static void playAudio(byte[] audioData) throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioData));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000); // Wait for audio to finish playing
        clip.close();
        audioInputStream.close();
    }

}