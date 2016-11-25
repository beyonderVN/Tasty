package com.vnwarriors.tastyclarify.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.vnwarriors.tastyclarify.model.PostModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 11/22/2016.
 */

public class CloneDataUtils {
    private static final String TAG = "CloneDataUtils";
    public static List<PostModel> getRateList(String fileName, Context context){
        Gson gson = new Gson();
        List<PostModel> rateList = new ArrayList<>();
        String rateListString =  FileUtils.readFromfile(fileName, context);

        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(rateListString).getAsJsonObject().getAsJsonArray("posts");

        Type listType = new TypeToken<List<PostModel>>() {}.getType();
        rateList = new Gson().fromJson(jsonArray, listType);


        JsonElement element = gson.toJsonTree(rateList, listType);

        String result = element.getAsJsonArray().toString();
        Log.d(TAG, "rateList: "+result);
        return  rateList;
    }
//    public static List<Transaction> getTransactionList(String fileName, Context context){
//        List<Transaction> transactionList = new ArrayList<>();
//        String rateListString =  FileUtils.readFromfile(fileName,context);
//        try {
//            JSONArray jsonArray = new JSONArray(rateListString);
//            if (jsonArray != null) {
//                for (int i=0;i<jsonArray.length();i++){
//                    Transaction transaction = new Transaction();
//                    transaction.setProduct(new Product(jsonArray.getJSONObject(i).getString("sku")));
//                    transaction.setAmount(jsonArray.getJSONObject(i).getDouble("amount"));
//                    transaction.setCurrency(new Currency(jsonArray.getJSONObject(i).getString("currency")));
//                    transactionList.add(transaction);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return  transactionList;
//    }
}
