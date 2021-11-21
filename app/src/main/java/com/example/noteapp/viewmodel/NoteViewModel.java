package com.example.noteapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoteViewModel extends ViewModel {

    private MutableLiveData<String> creator_id;

    public MutableLiveData<String> getCreator_id() {

        if(creator_id == null) {

            creator_id = new MutableLiveData<String>();
        }

        return creator_id;
    }


}