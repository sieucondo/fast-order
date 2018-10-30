package fast.sieu.fastorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Product;
import utils.CustomListAdapter;

public class ListMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        List<Product> image_details = getListData();
        final ListView LISTMENU = (ListView) findViewById(R.id.listMenu);
        LISTMENU.setAdapter(new CustomListAdapter(this, image_details));

        // Khi người dùng click vào các ListItem
        LISTMENU.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = LISTMENU.getItemAtPosition(position);
                Product product = (Product) o;
                Toast.makeText(ListMenuActivity.this, "Selected : " + product.getProductName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Product> getListData() {
        List<Product> list = new ArrayList<>();
        Product com = new Product(1, 1, "Cơm", 40);
        Product bun = new Product(2, 3, "Bún", 50);

        list.add(com);
        list.add(bun);

        return list;
    }
}
