package com.naqelexpress.naqelpointer.DB.DBObjects;

import android.database.Cursor;
import android.view.View;

import com.naqelexpress.naqelpointer.DB.DBConnections;
import com.naqelexpress.naqelpointer.GlobalVar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sofan on 13/04/2018.
 */

public class CheckPointTypeDDetail
{
    public int ID;
    public String Name;
    public String FName;
    public int CheckPointTypeDetailID;

    public CheckPointTypeDDetail(int id,  String name, String fname, int checkPointTypeDetailID)
    {
        ID = id;
        Name = name;
        FName = fname;
        CheckPointTypeDetailID = checkPointTypeDetailID;
    }

    public CheckPointTypeDDetail(){}

    public View rootView;
    public CheckPointTypeDDetail (String finalJson, View view)
    {
        this.rootView = view;
        try
        {
            DBConnections dbConnections = new DBConnections(GlobalVar.GV().context,rootView);
            JSONArray jsonArray = new JSONArray(finalJson);
            if (jsonArray.length() > 0)
            {
                //Delete the existing reasons
                Cursor result = GlobalVar.GV().dbConnections.Fill("select * from CheckPointTypeDDetail");
                if (result.getCount() > 0)
                {
                    result.moveToFirst();
                    do
                    {
                        dbConnections.deleteCheckPointTypeDDetail(Integer.parseInt(result.getString(result.getColumnIndex("ID"))));
                    }
                    while (result.moveToNext());
                }
            }

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CheckPointTypeDDetail instance = new CheckPointTypeDDetail();
                try
                {
                    instance.ID = Integer.parseInt(jsonObject.getString("id"));
                    instance.Name = jsonObject.getString("name");
                    instance.FName = jsonObject.getString("fName");
                    instance.CheckPointTypeDetailID = Integer.parseInt(jsonObject.getString("checkPointTypeDetailID"));

                    dbConnections.InsertCheckPointTypeDDetail(instance);
                }
                catch (JSONException ignored){}
            }
            GlobalVar.GV().GetCheckPointTypeDDetailList(false,0);
        }
        catch (JSONException ignored){}
    }
}