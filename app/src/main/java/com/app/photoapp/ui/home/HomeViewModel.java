package com.app.photoapp.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.app.photoapp.data.model.TemplateModel;
import com.app.photoapp.utils.TemplateLoader;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final MutableLiveData<List<TemplateModel>> templates = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public HomeViewModel(Application application) {
        super(application);
        loadTemplates();
    }

    private void loadTemplates() {
        isLoading.setValue(true);
        new Thread(() -> {
            List<TemplateModel> list = TemplateLoader.loadTemplates(getApplication());
            templates.postValue(list);
            isLoading.postValue(false);
        }).start();
    }

    public LiveData<List<TemplateModel>> getTemplates() { return templates; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
}
