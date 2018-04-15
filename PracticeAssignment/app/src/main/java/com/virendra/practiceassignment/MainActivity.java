package com.virendra.practiceassignment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.virendra.practiceassignment.Adapter.RecyclerAdapter;
import com.virendra.practiceassignment.Model.DisplayData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.tb_mainToolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_mainRecycler)
    RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private Realm realm;
    private RetrofitApi api;
    private ProgressDialog mProgressDialog;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        if (isInternetConnected()) {
            showProgressDialog();
            initRecyclerView();
            initRealm();
            initRetrofit();
            fetchDataFromApi();
        } else {
            showCheckConnectionDialog();
        }
    }

    private void showCheckConnectionDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Please check that you have an active internet connection before running this application.");
        alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogBuilder.setCancelable(false);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnected();
        }
        return false;
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Fetching data, Please wait...");
        mProgressDialog.show();
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(RetrofitApi.class);
    }

    private void fetchDataFromApi() {
        mCompositeDisposable = new CompositeDisposable();
        Observable<List<DisplayData>> observableResponse = api.getPhotos();

        observableResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DisplayData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<DisplayData> displayData) {
                        realm.executeTransactionAsync(
                                realm -> realm.insert(displayData),
                                () -> {
                                    Log.d(TAG, "Insert successful");
                                    dismissProgressDialog();
                                    readAndDisplayData();
                                },
                                error -> Log.e(TAG, "onError(Realm): ", error));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError(Observer): ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete(Observer): Completed");
                    }
                });
    }

    private void dismissProgressDialog() {

        if (mProgressDialog != null && !this.isFinishing()) {
            mProgressDialog.dismiss();
        }
    }

    private void readAndDisplayData() {
        RealmResults<DisplayData> photos = realm.where(DisplayData.class).findAll();
        mAdapter = new RecyclerAdapter(photos);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
