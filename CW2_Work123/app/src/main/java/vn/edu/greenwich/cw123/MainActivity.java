package vn.edu.greenwich.cw123;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import vn.edu.greewich.cw2.R;

public class MainActivity extends AppCompatActivity {
    protected static final String _FILE_NAME = "cw_2_image_list.txt";

    protected ImageView imageView;
    protected EditText etImageLink;
    protected Button btnPrevious, btnNext, btnAdd;

    protected ArrayList<String> _imageList;
    protected int _currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        etImageLink = findViewById(R.id.etImageLink);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> addImage());
        btnNext.setOnClickListener(v -> nextImage());
        btnPrevious.setOnClickListener(v -> previousImage());

        _imageList = getImageList();
        _currentIndex = 0;

        loadImage();
    }

    protected ArrayList<String> getImageList() {
        ArrayList<String> imageList = new ArrayList<>();

        imageList.add("https://img.freepik.com/premium-vector/cute-leopard-gecko-cartoon-sitting_188253-4018.jpg?w=740");
        imageList.add("https://img.freepik.com/free-vector/cute-monkey-holding-banana-cartoon-vector-icon-illustration-animal-nature-icon-concept-isolated_138676-5020.jpg?t=st=1669660138~exp=1669660738~hmac=e576b83ba77881939bdfe8d3c7ff7467e779e674df6455d60b92c2fd0ff837d5");

        getImageListFromFile(imageList);

        Toast.makeText(this, "Get list successfully.", Toast.LENGTH_SHORT).show();

        return imageList;
    }

    protected void loadImage() {
        Picasso.with(this).load(_imageList.get(_currentIndex)).into(imageView);
    }

    protected void addImage() {
        String imageURL = etImageLink.getText().toString();

        _imageList.add(imageURL);
        writeURLToFile(imageURL);

        etImageLink.setText("");

        Toast.makeText(this, "Added successfully.", Toast.LENGTH_SHORT).show();
    }

    protected void nextImage() {
        ++_currentIndex;
        loadImage();
    }

    protected void previousImage() {
        --_currentIndex;
        loadImage();
    }

    protected void writeURLToFile(String url) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(_FILE_NAME, MODE_APPEND));
            outputStreamWriter.write(url);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getImageListFromFile(ArrayList<String> imageList) {
        try {
            InputStream inputStream = openFileInput(_FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String url = "";
                while ((url = bufferedReader.readLine()) != null) {
                    imageList.add(url);
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}