package com.vnwarriors.tastyclarify.data.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnwarriors.tastyclarify.model.UserModel;
import com.vnwarriors.tastyclarify.utils.rx.FirebaseObservableListeners;

import rx.Observable;
import rx.functions.Func1;

public class FirebaseUserDatabase implements UserDatabase {

    private final DatabaseReference usersDB;
    private final FirebaseObservableListeners firebaseObservableListeners;

    public FirebaseUserDatabase(FirebaseDatabase firebaseDatabase, FirebaseObservableListeners firebaseObservableListeners) {
        usersDB = firebaseDatabase.getReference("users");
        this.firebaseObservableListeners = firebaseObservableListeners;
    }

    @Override
    public Observable<UserModel> readUserFrom(String userId) {
        return firebaseObservableListeners.listenToSingleValueEvents(usersDB.child(userId), as(UserModel.class));
    }

    @Override
    public Observable<UserModel> observeUser(String userId) {
        return firebaseObservableListeners.listenToValueEvents(usersDB.child(userId), as(UserModel.class));
    }

    @Override
    public void writeCurrentUser(UserModel user) {
        usersDB.child(user.getId()).setValue(user); //TODO handle errors
    }


    private <T> Func1<DataSnapshot, T> as(final Class<T> tClass) {
        return new Func1<DataSnapshot, T>() {
            @Override
            public T call(DataSnapshot dataSnapshot) {
                return dataSnapshot.getValue(tClass);
            }
        };
    }
}
