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
    int messageId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        Button next = findViewById(R.id.button2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase rootRef = new Firebase("https://fir-connect-29314.firebaseio.com/");
                String name = ((EditText)findViewById(R.id.editText2)).getText().toString();
                String id = name;
                messageId = messageId + 1;
                String message = ((EditText)findViewById(R.id.message)).getText().toString();
                // Assuming the user is already logged in
                rootRef.child("messages").child(("message" + messageId)).child("message").setValue(message);
                rootRef.child("messages").child("message" + messageId).child("sender").setValue(name);
                rootRef.child("users").child(id).setValue(name);
                rootRef.child("messages").child("message" + messageId).child("sender").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        TextView messageList = (TextView) findViewById(R.id.textView);
                        messageList.append(snapshot.getValue().toString() + "\n");

                        // System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                    }
                    @Override public void onCancelled(FirebaseError error) { }
                });

                rootRef.child("messages").child("message" + messageId).child("message").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        TextView messageList = (TextView) findViewById(R.id.textView);
                        messageList.append(snapshot.getValue().toString() + "\n\n");

                        // System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                    }

                    @Override public void onCancelled(FirebaseError error) { }
                });

            }
        });

    }

}
