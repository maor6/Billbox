package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.myapplication.ProductsListAdapter;
import java.util.ArrayList;
import DataStructures.Product;


public class CreateBillActivity extends AppCompatActivity {

    CardView search;
    Button finishBt;
    ArrayList<Product> products;
    ListView itemsList;
    ProductsListAdapter productsListAdapter;
    TextView totalPriceView;
    double totalToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);

        products = new ArrayList<>();
        itemsList = (ListView) findViewById(R.id.productsList);
        totalToPay = 0;
        totalPriceView = (TextView) findViewById(R.id.totalToPay) ;
        search = (CardView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CreateBillActivity.this, SearchProductActivity.class);
                startActivityForResult(intent, 1);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                Product product = (Product) data.getParcelableExtra("product");
                this.products.add(product);
                totalToPay +=product.getPrice();
                totalPriceView.setText(String.valueOf(totalToPay));
                productsListAdapter = new ProductsListAdapter(this, R.layout.bill_products_list, products);
                itemsList.setAdapter(productsListAdapter);
            }
        }
    }
}