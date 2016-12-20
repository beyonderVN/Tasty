package com.vnwarriors.tastyclarify.data.recipe.repo;

import com.vnwarriors.tastyclarify.data.DatabaseResult;
import com.vnwarriors.tastyclarify.data.recipe.database.FirebaseRecipeDatabase;
import com.vnwarriors.tastyclarify.model.Post;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Long on 12/20/2016.
 */

public class RecipeRepo {
    private final FirebaseRecipeDatabase recipeDatabase;

    public RecipeRepo(FirebaseRecipeDatabase recipeDatabase) {
        this.recipeDatabase = recipeDatabase;
    }


    public Observable<DatabaseResult<List<Post>>> getRecipes(final String type) {
        return recipeDatabase.observeRecipes(type)
                .map(asDatabaseResult())
                .onErrorReturn(DatabaseResult.<List<Post>>errorAsDatabaseResult());
    }

    private static Func1<List<Post>, DatabaseResult<List<Post>>> asDatabaseResult() {
        return new Func1<List<Post>, DatabaseResult<List<Post>>>() {
            @Override
            public DatabaseResult<List<Post>> call(List<Post> posts) {
                return new DatabaseResult<List<Post>>(posts);
            }
        };
    }


    public void sendMessage(String type, Post post) {
        recipeDatabase.sendMessage(type, post);
    }
}
