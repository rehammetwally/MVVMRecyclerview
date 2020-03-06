package com.rehammetwally.mvvmrecyclerview.viewmodels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rehammetwally.mvvmrecyclerview.models.NicePlace;
import com.rehammetwally.mvvmrecyclerview.repositories.NicePlaceRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    //MutableLiveData is a sub class of LiveData
    // MutableLiveData can be changed
    //LiveData is immutable it can't be changed
    //you can observe LiveData
    //it can't changed directly but it can changed indirectly
    private MutableLiveData<List<NicePlace>> nicePlaces;
    private NicePlaceRepository nicePlaceRepository;
    private MutableLiveData<Boolean> isUpdating=new MutableLiveData<>();


    public void init(){
        if (nicePlaces != null)
            return;
        nicePlaceRepository=NicePlaceRepository.getInstance();
        nicePlaces=nicePlaceRepository.getNicePlaces();

    }

    public void addNewValue(final NicePlace nicePlace){
        isUpdating.setValue(true);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<NicePlace> currentPlaces=nicePlaces.getValue();
                currentPlaces.add(nicePlace);
                nicePlaces.postValue(currentPlaces);
                isUpdating.setValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public LiveData<List<NicePlace>> getNicePlaces(){
        return nicePlaces;
    }
    public LiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }
}
