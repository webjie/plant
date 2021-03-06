package person.jack.plant.activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import person.jack.plant.R;
import person.jack.plant.common.AppContext;
import person.jack.plant.fragment.PlantsAddFragment;
import person.jack.plant.http.HttpClient;
import person.jack.plant.ui.swipebacklayout.SwipeBackActivity;
import person.jack.plant.utils.PlantInfoDialog;

public class PlantInfoActivity extends SwipeBackActivity implements View.OnClickListener {

    private Button btnBack;
    private TextView textHeadTitle;
    private List<String> plantList = new ArrayList<String>();

    private ListView lv_planList;

    private EditText et_plantName;
    private Button btn_plantSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plant_info);
        textHeadTitle=(TextView)findViewById(R.id.textHeadTitle);
        textHeadTitle.setText("植物列表");
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
       btnBack.setOnClickListener(this);
        initView();
        httpPlantInfo();
    }

    public void httpPlantInfo() {
        HttpClient.getRequest("https://api.apishop.net/common/plantFamily/queryPlantList" +
                "?apiKey=laUuwV4e99fe7400a5ea670e5c6cb78b74c84eeccbe3af4&pageSize=608", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("addPlant", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("addPlant", result);
                jsonPlantInfo(result);
            }
        });
    }

    public void jsonPlantInfo(String text) {
        try {
            JSONObject jsonObject = new JSONObject(text);
            String result = jsonObject.getString("result");
            JSONObject jsonObject1 = new JSONObject(result);
            String plants = jsonObject1.getString("plantList");
            JSONArray array = new JSONArray(plants);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                plantList.add(name);
               // Log.d("addPlant", name);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String> arrayAdapter =
                            new ArrayAdapter<String>(PlantInfoActivity.this,
                                    android.R.layout.simple_list_item_1, plantList);
                    lv_planList.setAdapter(arrayAdapter);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView() {


        lv_planList = (ListView) findViewById(R.id.lv_planList);
        lv_planList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    int toInfo=getIntent().getIntExtra("toInfo",0);
                    if (toInfo == 1) {
                        Intent intent=new Intent(PlantInfoActivity.this, MainActivity.class);
                        intent.putExtra("result",2);
                        intent.putExtra("type",plantList.get(position));
                        startActivity(intent);
                        finish();

                    }if(toInfo==2){
                        try{
                            Log.d("plantInfo", "onItemClick: 更新植物");

                            Intent intent=getIntent();
                            intent.putExtra("result",3);
                            intent.putExtra("type",plantList.get(position));
                            setResult(RESULT_OK,intent);
                            finish();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                //Log.d("listview",lv_planList.getSelectedItem().toString());
                Log.d("list",plantList.get(position));

            }
        });
        btnBack.setOnClickListener(this);
        textHeadTitle.setOnClickListener(this);
        et_plantName = (EditText) findViewById(R.id.et_plantName);
        et_plantName.setOnClickListener(this);
        btn_plantSearch = (Button) findViewById(R.id.btn_plantSearch);
        btn_plantSearch.setOnClickListener(this);
        et_plantName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    plantList.clear();
                    httpPlantInfo();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_plantSearch:

                String plantName = et_plantName.getText().toString().trim();
                if (plantName.equals("")) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    plantList.clear();
                    httpPlantSearch(plantName);
                }

                break;
            case  R.id.btnBack:
               // finish();
                showNati();
                break;
        }
    }
    /**
     * 弹出对话框
     */
    public void showNati(){


      Intent intent=new Intent(AppContext.getInstance(),EnvWarnActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification=new NotificationCompat.Builder(this) .setContentTitle("值警告")
                .setContentText("超出设定阈值，当前值,阈值范围").setSmallIcon(R.drawable.message)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);



    }
    public void httpPlantSearch(String name) {
        HttpClient.getRequest("https://api.apishop.net/common/plantFamily/queryPlantListByKeyword" +
                "?apiKey=laUuwV4e99fe7400a5ea670e5c6cb78b74c84eeccbe3af4&keyword=" + name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("addPlant", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
               // Log.d("addPlant", result);
                jsonPlantSearch(result);
            }
        });
    }

    public void jsonPlantSearch(String text) {
        try {
            JSONObject jsonObject = new JSONObject(text);
            String result = jsonObject.getString("result");
            JSONObject jsonObject1 = new JSONObject(result);
            String plants = jsonObject1.getString("plantList");
            JSONArray array = new JSONArray(plants);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                plantList.add(name);
               // Log.d("addPlant", name);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (plantList.size() == 0) {
                        Toast.makeText(PlantInfoActivity.this, "未找到", Toast.LENGTH_SHORT).show();
                    } else {

                        ArrayAdapter<String> arrayAdapter =
                                new ArrayAdapter<String>(PlantInfoActivity.this,
                                        android.R.layout.simple_list_item_1, plantList);
                        lv_planList.setAdapter(arrayAdapter);
                    }


                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
