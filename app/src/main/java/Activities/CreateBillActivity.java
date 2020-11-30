package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;



public class CreateBillActivity extends AppCompatActivity {

    CardView search;
    Button finishBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        search = (CardView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateBillActivity.this, SearchProductActivity.class);
                startActivity(intent);
            }
        });
        finishBt = (Button) findViewById(R.id.finishbt);
        finishBt.setOnClickListener(new View.OnClickListener() { // on "סיים" clicked
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateBillActivity.this, NFCBussinesActivity.class);
//                Product product = new Product();
//                //TODO change the reciept fields by the textViews
//                Log.d("mytag", "i got here");
//                intent.putExtra("product", (Parcelable) product); //????? problem here
                startActivity(intent);
            }
        });
    }
}