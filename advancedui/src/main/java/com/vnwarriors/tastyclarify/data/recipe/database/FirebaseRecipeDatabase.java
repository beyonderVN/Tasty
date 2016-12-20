package com.vnwarriors.tastyclarify.data.recipe.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.vnwarriors.tastyclarify.model.Post;
import com.vnwarriors.tastyclarify.utils.rx.FirebaseObservableListeners;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Long on 12/20/2016.
 */

public class FirebaseRecipeDatabase {
    private static final int DEFAULT_LIMIT = 1000;
    static final String POST_REFERENCE = "posts";
    private final DatabaseReference messagesDB;
    private final FirebaseObservableListeners firebaseObservableListeners;

    public FirebaseRecipeDatabase(FirebaseDatabase firebaseDatabase, FirebaseObservableListeners firebaseObservableListeners) {
        messagesDB = firebaseDatabase.getReference(POST_REFERENCE);
        this.firebaseObservableListeners = firebaseObservableListeners;
    }


    public Observable<List<Post>> observeRecipes(String type) {
        return firebaseObservableListeners.listenToValueEvents(messagesInChannel(type).limitToLast(DEFAULT_LIMIT), toPostList());
    }

    public void sendMessage(String type, Post post) {
        messagesDB.push().setValue(post); //TODO handle errors
    }

    private Query messagesInChannel(String type) {
        if(type.equals("0")){
            return messagesDB;
        }
        return messagesDB.orderByChild("tipCategories").equalTo(String.valueOf(type));
    }

    private Func1<DataSnapshot, List<Post>> toPostList() {
        return new Func1<DataSnapshot, List<Post>>() {
            @Override
            public List<Post> call(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                List<Post> posts = new ArrayList<>();
                for (DataSnapshot child : children) {
                    Post post = child.getValue(Post.class);
                    posts.add(post);
                }
                return posts;
            }
        };
    }
}
