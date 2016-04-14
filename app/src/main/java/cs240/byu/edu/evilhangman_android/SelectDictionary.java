package cs240.byu.edu.evilhangman_android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectDictionary extends AppCompatActivity
{
    List<String> fileList;
    AssetManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dictionary);

    }

    @Override
    public void onResume() {

        super.onResume();

        Resources res = getResources();
        am = res.getAssets();

        String[] files = null;
        try {
            files = am.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileList = null;
        if(files != null) {
            fileList = new ArrayList<String>();
            for(String file : files) {
                if(file.endsWith(".txt"))
                    fileList.add(file);
            }
            if(fileList.size() > 0) {
                ArrayAdapter<String> fileAdapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_expandable_list_item_1, fileList);
                ListView listView = (ListView) findViewById(R.id.files);
                listView.setAdapter(fileAdapter);
                listView.setOnItemClickListener(fileClickListener);
                fileAdapter.notifyDataSetChanged();
            }
        }

    }

    private AdapterView.OnItemClickListener fileClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            String fileSelected = fileList.get(i);
            Intent resultIntent = new Intent();
            resultIntent.putExtra(MainActivity.fileSelectedKey, fileSelected);
            Toast.makeText(getBaseContext(),fileSelected + " is the dictionary file to be loaded",Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    };
}