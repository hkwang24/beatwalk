package com.example.beatwalk.data;

import android.content.Context;
import android.util.Log;

import com.example.beatwalk.MyApplication;
import com.example.beatwalk.Retrofit.IMyService;
import com.example.beatwalk.Retrofit.RetrofitClientPosts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ProjectStoreMongo implements ProjectStore {

    private static final String FILE_NAME = "projectData.txt";
    HashMap<String, Project> data;
    static ProjectStore instance = new ProjectStoreMongo();
    Context currentContext;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    Project currentUser, defaultUser;

    private ProjectStoreMongo () {

        defaultUser = new Project("waiting on server", "waiting on server", "waiting on server");
        currentUser = defaultUser;

        data = new HashMap<String, Project>();

        currentContext = MyApplication.getAppContext();

        Retrofit retrofitClientPosts = RetrofitClientPosts.getInstance();
        iMyService = retrofitClientPosts.create(IMyService.class);
    }

    public static ProjectStore getInstance() {
        instance.setUserMap();
        return instance;
    }

    @Override
    public void addProject(String id, String user, String creationDate) {
        Project project = new Project(id, user, creationDate);
        data.put(id, project);

        compositeDisposable.add(iMyService.createProject(id, user, creationDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {}
                }));
    }


    private void getLoggedInUser(String email) {
        compositeDisposable.add(iMyService.getUser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        setCurrentUser(response);
                    }
                }));
    }

    public void setUserMap() {
        compositeDisposable.add(iMyService.allProjects("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        JSONArray jsonArr = new JSONArray(response);
                        Log.v("", jsonArr.toString());
                        for (int i = 0; i < jsonArr.length(); i++) {
                            String id = jsonArr.getJSONObject(i).getString("id");
                            String user = jsonArr.getJSONObject(i).getString("user");
                            String creationDate = jsonArr.getJSONObject(i).getString("creationDate");
                            Project project = new Project(id, user, creationDate);
                            putUser(id, project);
                        }
                    }
                }));
    }

    private void putUser(String id, Project project) {
        data.put(id, project);
    }



    private void setCurrentUser(String o) {
        try {
            JSONObject jObj = new JSONObject(o);
            Iterator<String> iter = jObj.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                Log.v(key, jObj.get(key).toString());
            }
            String id = jObj.getString("id");
            String user = jObj.getString("user");
            String creationDate = jObj.getString("creationDate");
            Project project = new Project(id, user, creationDate);
            currentUser = project;
        } catch (JSONException e) {
            e.printStackTrace();
            currentUser = defaultUser;
        }
    }

    @Override
    public String getUser(String id) {
        if (id.equals(currentUser.getId())) {
            return currentUser.getUser();
        }
        return data.get(id).getUser();
    }

    @Override
    public String getCreationDate(String id) {
        if (id.equals(currentUser.getId())) {
            return currentUser.getCreationDate();
        }
        return data.get(id).getCreationDate();
    }

    @Override
    public void modifyProject(String id, String user, String creationDate) {
        currentUser.setUser(user);
        currentUser.setCreationDate(creationDate);

        compositeDisposable.add(iMyService.modifyProject(id, user, creationDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {}
                }));
    }

    @Override
    public void deleteProject(String id) {
        compositeDisposable.add(iMyService.deleteProject(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {}
                }));
    }
}
