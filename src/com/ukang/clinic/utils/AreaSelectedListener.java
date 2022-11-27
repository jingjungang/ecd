package com.ukang.clinic.utils;

/**
 * 区域选择监听
 * jjg 2016年4月18日 22:31:02
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ukang.clinic.R;
import com.ukang.clinic.db.DBAdapater;

@SuppressWarnings("unused")
public class AreaSelectedListener implements OnItemSelectedListener {

    DBAdapater db;
    Context context;
    Spinner spin_self;
    Spinner spin_son;
    Spinner spin_grand_son;
    int index;
    int province_ID, city_ID, country_ID;
    int selectedID = -1;

    public AreaSelectedListener(Context context) {
        this.context = context;
    }

    /**
     * @Description TODO(这里用一句话描述这个方法的作用)
     * @param context
     * @param db
     * @param spin_self
     * @param spin_son
     * @param sipn_grand_son
     *            把县先清空
     * @param index
     *            1市2县
     */
    public AreaSelectedListener(Context context, DBAdapater db, Spinner spin_self, Spinner spin_son,
            Spinner spin_grand_son, int index) {
        // TODO Auto-generated constructor stub
        this.db = db;
        this.context = context;
        this.spin_self = spin_self;
        this.spin_son = spin_son;
        this.index = index;
        this.spin_grand_son = spin_grand_son;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        String sql = "", _update_name = "";
        _update_name = (String) spin_self.getSelectedItem(); // 拿到被选择项的值
        sql = "select area_id from by_area WHERE area_name ='" + _update_name + "'";
        db.open();
        try {
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                selectedID = cursor.getInt(cursor.getColumnIndex("area_id"));// 取出子类ID
                if (_update_name.equals("北京") && index == 1) { // 北京特殊处理
                    break;
                }
            }
            cursor.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (index != 3) {
            initName(selectedID, spin_son, index);
        }
        if (index == 1) { // 清空第三级目录
            ArrayAdapter<String> adapterp = new ArrayAdapter<String>(context, R.layout.myspinner,
                    new ArrayList<String>());
            adapterp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // 设置下拉列表的风格
            spin_grand_son.setAdapter(adapterp);
        }
        db.close();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * 初始化子目录
     * 
     * @Description (TODO这里用一句话描述这个方法的作用)
     * @param code
     * @param spin
     * @param classical
     */
    private void initName(int code, Spinner spin, int classical) {
        List<String> listc = new ArrayList<String>();
        try {
            String sql = "";
            if (classical == 1) {
                sql = "select area_name from by_area WHERE level ='2' and parent_area_id = " + code;
            } else {
                sql = "select area_name from by_area WHERE level ='3' and parent_area_id = " + code;
            }
            db.open();
            Cursor cursor = db.rawQuery(sql, null);
            String name = "";
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("area_name"));
                if (name.equals("")) {
                    listc.add("  ");
                }
                listc.add(name);
            }
            cursor.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(context, "数据库打开失败，请稍等重试", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.myspinner, listc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db.close();
    }

    public int getSelectedID() {
        return selectedID;
    }

}
