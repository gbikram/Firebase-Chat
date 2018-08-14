package local.firebaseconnect.android.firebaseconnect;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    int messageId;
    Firebase rootRef;
    TextView messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        rootRef = new Firebase("https://fir-connect-29314.firebaseio.com/");
        setContentView(R.layout.activity_main);
        Button next = findViewById(R.id.button2);
        messageList = (TextView) findViewById(R.id.textView);
        rootRef.child("lastId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageId = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.editText2)).getText().toString();
                String id = name;

                rootRef.child("lastId").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        messageId = dataSnapshot.getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                messageId++;

                String message = ((EditText)findViewById(R.id.message)).getText().toString();
                // Assuming the user is already logged in
                rootRef.child("lastId").setValue(messageId);
                rootRef.child("messages").child(("message" + messageId)).child("message").setValue(message);
                rootRef.child("messages").child("message" + messageId).child("sender").setValue(name);
                rootRef.child("users").child(id).setValue(name);
            }
        });
        rootRef.child("lastId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageId = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        rootRef.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                messageList.append(snapshot.child("message" + messageId).child("sender").getValue(String.class) + "\n");
                messageList.append(snapshot.child("message" + messageId).child("message").getValue(String.class) + "\n\n");

            }
            @Override public void onCancelled(FirebaseError error) { }
        });

    }

}
