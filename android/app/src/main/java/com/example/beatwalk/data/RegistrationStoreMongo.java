package com.example.beatwalk.data;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.beatwalk.MainActivity;
import com.example.beatwalk.MyApplication;
import com.example.beatwalk.Retrofit.IMyService;
import com.example.beatwalk.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegistrationStoreMongo implements RegistrationStore {
    private static final String FILE_NAME = "data.txt";
    HashMap<String, User> data;
    static RegistrationStore instance = new RegistrationStoreMongo();
    Context currentContext;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    boolean status, containsStatus;
    User currentUser, defaultUser;



    private RegistrationStoreMongo () {

        defaultUser = new User("waiting on server", "", "waiting on server", 1111, "waiting on server");
        currentUser = defaultUser;

        data  = new HashMap<String, User>();
        currentContext = MyApplication.getAppContext();

        status = false;
        containsStatus = true;
//        File root = currentContext.getExternalFilesDir(null);
//        File file = new File(root, "data.txt");


        //Init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

    }

    public static RegistrationStore getInstance() {
        return instance;
    }



    @Override
    public void addUser(String email, String password, String name, int year, String phone) {
        User newUser = new User(email, password, name, year, phone);
        data.put(email, newUser);

        String text = newUser.toString();
        FileOutputStream fos = null;



        compositeDisposable.add(iMyService.registerUser(email, name, password, Integer.toString(year), phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {

                    }
                }));


    }

    private void setStatus(boolean bool, int statusVar) {
        if (statusVar == 1) {
            status = bool;
        } else {
            containsStatus = bool;
        }
    }

    @Override
    public boolean verifyLogin(String name, String password) {
        if (name == null) {
            return false;
        }

        if (password == null) {
            return false;
        }

        final String serverResponse;
        final boolean returnVal;


        compositeDisposable.add(iMyService.loginUser(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        if (response.contains("successful")) {
                            setStatus(true, 1);

                        }
                    }
                }));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (status == true) {
            status = false;
            currentUser = defaultUser;
            getLoggedInUser(name);
            setUserMap();

            return true;
        }
        return false;
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

    private void setUserMap() {
        compositeDisposable.add(iMyService.allUsers("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        JSONArray jsonArr = new JSONArray(response);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            String name = jsonArr.getJSONObject(i).getString("firstName");
                            String email = jsonArr.getJSONObject(i).getString("email");
                            String phone = jsonArr.getJSONObject(i).getString("phone");
                            String year = jsonArr.getJSONObject(i).getString("year");
                            boolean professor;
                            professor = year.contains("p") || year.trim().length() == 0;
                            String rating = jsonArr.getJSONObject(i).getString("rating");
                            if (!professor) {
                                User currUser = new User(email, "", name, Integer.valueOf(year), phone, Double.valueOf(rating));
                                putUser(email, currUser);
                            }
                        }
                    }
                }));
    }

    private void putUser(String email, User user) {
        data.put(email, user);
    }

    private void setCurrentUser(String o) {
        try {
            JSONObject jObj = new JSONObject(o);
            Iterator<String> iter = jObj.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                Log.v(key, jObj.get(key).toString());
            }
            String email = jObj.getString("email");
            String name = jObj.getString("firstName");
            String year = jObj.getString("year");
            String phone = jObj.getString("phone");
            User user = new User(email, "", name, Integer.valueOf(year), phone);
            currentUser = user;
        } catch (JSONException e) {
            e.printStackTrace();
            currentUser = defaultUser;
        }
    }

    @Override
    public boolean accountExists(String name) {
        if (name != null) {
            compositeDisposable.add(iMyService.contains(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String response) throws Exception {
                            if (response.contains("false")) {
                                setStatus(false, 2);
                            }
                        }
                    }));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!containsStatus) {
                status = true;
                currentUser = defaultUser;
                return false;
            }
            return true;
        }

        throw new NullPointerException("Null username");
    }

    @Override
    public String getName(String email) {
        if (email.equals(currentUser.getEmail())) {
            return currentUser.getName();
        }
        return data.get(email).getName();
    }



    @Override
    public int getClassYear(String email) {
        if (email.equals(currentUser.getEmail())) {
            return currentUser.getClassYear();
        }
        return data.get(email).getClassYear();
    }

    @Override
    public String getPhone(String email) {
        if (email.equals(currentUser.getEmail())) {
            return currentUser.getPhoneNumber();
        }
        return data.get(email).getPhoneNumber();
    }

    @Override
    public void modifyUser(String email, String name, int year, String phone) {

        currentUser.setName(name);
        currentUser.setClassYear(year);
        currentUser.setPhoneNumber(phone);

        compositeDisposable.add(iMyService.modifyUser(email, name, Integer.toString(year), phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {

                    }
                }));
    }

    private String currentUsersString() {
        String returnVal = "";
        for (String userEmail: data.keySet()) {
            returnVal += data.get(userEmail).toString() + "\n";
        }
        return returnVal;
    }

    @Override
    public Map<String, String> getUsers() {
        Map<String, String> returnVal = new HashMap<String, String>();
        for (String s: data.keySet()) {
            returnVal.put(s, data.get(s).getName());
        }
        return returnVal;
    }

    @Override
    public void rateUser(final String email, int rating) {
        compositeDisposable.add(iMyService.addRating(email, rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        data.get(email).setRating(Double.valueOf(response));
                    }
                }));
    }

    @Override
    public double getRating(String email) {
        return data.get(email).getRating();
    }

}
