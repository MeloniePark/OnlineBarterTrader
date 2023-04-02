package com.example.onlinebartertrader;

        import android.content.Intent;
        import android.location.LocationListener;
        import android.os.Bundle;
        import android.view.View;

        import androidx.appcompat.app.AppCompatActivity;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class UserStats extends AppCompatActivity implements View.OnClickListener{

    //firebase
    FirebaseDatabase database;
    DatabaseReference userDBRef;

    String userEmailAddress;
    String userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Firebase Connection
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //get user email
        userEmailAddress = getIntent().getStringExtra("emailAddress");
        userLoggedIn = getIntent().getStringExtra("userLoggedIn");
        System.out.println(userLoggedIn);
        System.out.println(userEmailAddress);

        userDBRef = database.getReference("Users").child(userLoggedIn).child(userEmailAddress).child("items");
        System.out.println(userDBRef);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button4){
            Intent intent = new Intent(this, UserStats.class);
            intent.putExtra("emailAddress", userEmailAddress.toLowerCase());
            intent.putExtra("userLoggedIn", userLoggedIn);
            startActivity(intent);
        }
    }

    boolean checkGivenRating(String ratingFromDB, String rating) {
        return ratingFromDB.equalsIgnoreCase(rating);
    }

    boolean checkTotalAmount(String valuationFromDB, String TotalValue) {
        return valuationFromDB.equalsIgnoreCase(TotalValue);
    }
}
