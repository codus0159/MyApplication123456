package cookmap.cookandroid.com.myapplication123456;

/**
 * Created by 507 on 2017-12-07.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Recog extends AppCompatActivity implements TextToSpeech.OnInitListener {
    protected Button btRecog, btTts;
    protected EditText etTts;
    protected TextToSpeech tts;
    protected ArrayList<String> arName, arPhoneNum;
    protected final int nNameSize = 3;
    protected static final int RECOG_CODE = 1234;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RECOG_CODE) {
                ArrayList<String> arStr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String sRecog = arStr.get(0);
                Toast.makeText(getApplicationContext(), sRecog, Toast.LENGTH_SHORT).show();
                int nPos = arName.indexOf(sRecog);
                if (nPos == -1) {
                    int speak = tts.speak(sRecog + "는 없는 이름입니다", TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    String sPhoneNum = arPhoneNum.get(nPos);
                    Toast.makeText(getApplicationContext(), sRecog, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sPhoneNum));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recog_activity);
        btRecog = (Button) findViewById(R.id.btRecog);
        btRecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 인식중.");
                startActivityForResult(intent, RECOG_CODE);
            }
        });
        btTts = (Button) findViewById(R.id.btTts);
        btTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = etTts.getText().toString();
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(str, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
        etTts = (EditText) findViewById(R.id.etTts);
        tts = new TextToSpeech(this, this);
        arName = new ArrayList<String>(nNameSize);
        arPhoneNum = new ArrayList<String>(nNameSize);
        arName.add("장채연");
        arPhoneNum.add("01058061213");
        arName.add("이수진");
        arPhoneNum.add("01020858578");
        arName.add("성수민");
        arPhoneNum.add("01055330762");
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(0.5f);
            tts.setSpeechRate(1.0f);
        }
    }
}