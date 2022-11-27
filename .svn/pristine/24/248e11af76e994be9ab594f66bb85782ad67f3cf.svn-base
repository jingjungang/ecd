package com.ukang.clinic.fragments;
/**
 * 一般资料
 * jjg
 * 2016年6月16日 10:48:52
 */
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinicaltrial.view.Mdate;

public class GenernalInfos extends Fragment {

    View root;

    @ViewInject(R.id.radioGroup)
    // 西医诊断
    private RadioGroup radioGroup;
    @ViewInject(R.id.r1)
    private RadioButton r1;
    @ViewInject(R.id.r2)
    private RadioButton r2;
    @ViewInject(R.id.des)
    private EditText des;

    // 发病时间
    @ViewInject(R.id.date1)
    private Mdate date1;
    // 确认时间
    @ViewInject(R.id.date2)
    private Mdate date2;

    @ViewInject(R.id.radioGroup_1)
    private RadioGroup radioGroup_1;// 过敏史
    @ViewInject(R.id.n_illness)
    private RadioButton n_illness;
    @ViewInject(R.id.y_illness)
    private RadioButton y_illness;
    @ViewInject(R.id.des1)
    private EditText des1;

    @ViewInject(R.id.radioGroup_2)
    private RadioGroup radioGroup_2;// 既往疾病史
    @ViewInject(R.id.y_old_illness)
    private RadioButton y_old_illness;
    @ViewInject(R.id.n_old_illness)
    private RadioButton n_old_illness;
    @ViewInject(R.id.des2)
    private EditText des2;

    @ViewInject(R.id.submit)
    private Button submit;
    Context mContent;
    private RequestThread rThread;
    MWDApplication application;
    private int wmdiagnosis, historyofallergy, historyofdisease;

    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.root = paramLayoutInflater.inflate(R.layout.layout_genanal_case, paramViewGroup, false);
        ViewUtils.inject(this, this.root);
        mContent = getActivity();
        application = ((MWDApplication) mContent.getApplicationContext());
        submit.setOnClickListener(commitBtn);
        ((MainActivity)getActivity()).setSubmitVisibily(submit);
        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataByPost();
    }

    private View.OnClickListener commitBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onEdit();
        }
    };

    private void getDataByPost() {
        if (MWDUtils.isNetworkConnected(mContent)) {
            ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
            localArrayList.add(new BasicNameValuePair("pid", application.pid));
            localArrayList.add(new BasicNameValuePair("token", Constant.token));
            this.rThread = new RequestThread(localArrayList, "http", "post", Constant.GENARAL_DATA_URL, mHandler);
            this.rThread.start();
        }

    }

    Handler mHandler = new Handler() {

        public void handleMessage(Message paramAnonymousMessage) {
            try {
                String str = paramAnonymousMessage.obj.toString();
                JSONObject localJSONObject = new JSONObject(str);
                str = localJSONObject.getString("info");
                if (localJSONObject.getString("status").toString().equals("1")) {
                    localJSONObject = new JSONObject(str);
                    setLocalData(localJSONObject);
                }
            } catch (JSONException localJSONException) {
                localJSONException.printStackTrace();
            }
        }
    };

    /**
     * 提交请求
     */
    private void onEdit() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("token", Constant.token);
        params.addBodyParameter("pid", application.pid);
        wmdiagnosis = getWmdiagnosis();
        params.addBodyParameter("wmdiagnosis", wmdiagnosis + "");
        if (wmdiagnosis == 2) {
            params.addBodyParameter("wmdiagnosisinfo", des.getText().toString().trim());
        }
        params.addBodyParameter("timeofonset", date1.getText());
        params.addBodyParameter("diagnosistime", date2.getText());
        historyofallergy = getHistoryofallergy();
        params.addBodyParameter("historyofallergy", historyofallergy + "");
        if (historyofallergy == 1) {
            params.addBodyParameter("historyofallergyinfo", des1.getText().toString().trim());
        }
        historyofdisease = getHistoryofdisease();
        params.addBodyParameter("historyofdisease", historyofdisease + "");
        if (historyofdisease == 1) {
            params.addBodyParameter("historyofdiseaseinfo", des2.getText().toString().trim());
        }
        XThread thread = new XThread(getActivity(), 0, params, Constant.GENARAL_DATA_EDIT_URL, xHandler);
        thread.setShowDia(true);
        thread.start();
    }

    private Handler xHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            try {
                JSONObject json = new JSONObject(result);
                int status = json.getInt("status");
                if (status == 1) {
                    Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取西医诊断选项
     */
    private int getWmdiagnosis() {
        if (r1.isChecked()) {
            return 1;
        } else if (r2.isChecked()) {
            return 2;
        }
        return 1;
    }

    /**
     * 获取过敏史选项
     */
    private int getHistoryofallergy() {
        if (y_illness.isChecked()) {
            return 1;
        } else if (n_illness.isChecked()) {
            return 2;
        }
        return 1;
    }

    /**
     * 获取疾病史选项
     */
    private int getHistoryofdisease() {
        if (y_old_illness.isChecked()) {
            return 1;
        } else if (n_old_illness.isChecked()) {
            return 2;
        }
        return 1;
    }

    /**
     * 查看数据
     */
    protected void setLocalData(JSONObject jo) {
        String temp = "";
        try {
            temp = jo.getString("wmdiagnosis");
            if (temp.equals("1")) {
                r1.setChecked(true);
                r2.setChecked(false);
            } else {
                r1.setChecked(false);
                r2.setChecked(true);
                des.setText(jo.getString("wmdiagnosisinfo"));
            }

            date1.setText(jo.getString("timeofonset"));
            date2.setText(jo.getString("diagnosistime"));

            temp = jo.getString("historyofallergy");
            if (temp.equals("1")) {
                y_illness.setChecked(true);
                n_illness.setChecked(false);
                des1.setText(jo.getString("historyofallergyinfo"));
            } else {
                y_illness.setChecked(false);
                n_illness.setChecked(true);
            }
            temp = jo.getString("historyofdisease");
            if (temp.equals("1")) {
                y_old_illness.setChecked(true);
                n_old_illness.setChecked(false);
                des2.setText(jo.getString("historyofdiseaseinfo"));
            } else {
                y_old_illness.setChecked(false);
                n_old_illness.setChecked(true);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*
         * radioGroup; r1; r2; des; date1; date2; n_illness; y_illness; des1; y_old_illness; n_old_illness; des2;
         */
    }
}
