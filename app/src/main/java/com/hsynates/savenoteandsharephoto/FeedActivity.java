package com.hsynates.savenoteandsharephoto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FeedActivity extends AppCompatActivity {

    Toolbar toolbar;
    AlertDialog.Builder alert;
    Intent intent;
    ArrayList<String> emailFromFB;
    ArrayList<String> titleFromFB;
    ArrayList<String> imageFromFB;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        toolbar = findViewById(R.id.ToolbarFeed);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Feed");

        alert = new AlertDialog.Builder(FeedActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        emailFromFB = new ArrayList<>();
        titleFromFB = new ArrayList<>();
        imageFromFB = new ArrayList<>();

        getDataFromFB();

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(emailFromFB, titleFromFB, imageFromFB);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_Post) {
            intent = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.sign_Out) {
            alert.setTitle("LOG OUT");
            alert.setMessage("Do you want to log out of this account?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    firebaseAuth.signOut();
                    intent = new Intent(FeedActivity.this, SignActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    public void getDataFromFB() {
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> map = snapshot.getData();
                        assert map != null;
                        String email = (String) map.get("email");
                        String title = (String) map.get("title");
                        String image = (String) map.get("image");

                        emailFromFB.add(email);
                        titleFromFB.add(title);
                        imageFromFB.add(image);

                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
     */

    public void getDataFromFB() {
        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();

                        String email = (String) data.get("email");
                        String title = (String) data.get("title");
                        String image = (String) data.get("image");

                        emailFromFB.add(email);
                        titleFromFB.add(title);
                        imageFromFB.add(image);

                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void addPost(View view) {
        intent = new Intent(FeedActivity.this, UploadActivity.class);
        startActivity(intent);
    }
}