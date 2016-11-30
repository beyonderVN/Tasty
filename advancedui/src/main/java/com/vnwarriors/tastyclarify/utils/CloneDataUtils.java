package com.vnwarriors.tastyclarify.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.vnwarriors.tastyclarify.model.Comment;
import com.vnwarriors.tastyclarify.model.PostModel;
import com.vnwarriors.tastyclarify.model.UserModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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
    }public static List<PostModel> getRateListWithComments(String fileName, Context context){
        Gson gson = new Gson();
        List<PostModel> rateList = new ArrayList<>();
        String rateListString =  FileUtils.readFromfile(fileName, context);

        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(rateListString).getAsJsonObject().getAsJsonArray("posts");

        Type listType = new TypeToken<List<PostModel>>() {}.getType();
        rateList = new Gson().fromJson(jsonArray, listType);
        for (PostModel postModel : rateList){

            Log.d(TAG, "onClick: " + postModel.getTipName());
            List<Comment> comments = new ArrayList<Comment>();
            String[] names = {"Ezeal","Leona","Corgi","Lucian","Olaf","Kennen"};
            String[] messages = {"I will cook it!","Delicious!","Thank you!","I love this recipe!","Yummy!","Ahihi!"};
            for (int i = 0; i < 10; i++) {
                UserModel userModel = new UserModel();
                userModel.setId("gSUNZWLvLmS5vdu7YTcQlXEDX5p1");
                Random r = new Random();
                int i1 = (r.nextInt(names.length-1));
                userModel.setName(names[i1]);
                userModel.setPhoto_profile("http://fanexpodallas.com/wp-content/uploads/550w_soaps_silhouettesm2.jpg");
                Comment comment = new Comment();
                comment.userModel = userModel;
                Random r2 = new Random();
                int i2 = (r2.nextInt(messages.length-1));
                comment.message = messages[i2];
                comment.createAt = "" + Calendar.getInstance().getTime().getTime();
                comment.updateAt = "" + Calendar.getInstance().getTime().getTime();
                comments.add(comment);
            }
            postModel.setTipComments(comments);

        }

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
