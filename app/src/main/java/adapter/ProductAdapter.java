package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.android.fastorder.R;
import model.Product;

public class ProductAdapter extends BaseAdapter {
    private List<Product> listData;
    private LayoutInflater layoutInflater;
    private Context context;



    public ProductAdapter(Context aContext,  List<Product> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    public List<Product> getListData() {
        return listData;
    }

    public void setListData(List<Product> listData) {
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.product_list_item, null);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.txtProductName);
            holder.price = convertView.findViewById(R.id.txtPrice);
            holder.imgView = convertView.findViewById(R.id.imageProduct);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        Product product = this.listData.get(position);

        holder.productName.setText(product.getProductName());
        holder.price.setText("Price: " + formatter.format(product.getPrice()) + " đ");
        holder.imgView.setImageResource(R.mipmap.ic_launcher_round);

        return convertView;
    }


    static class ViewHolder {
        ImageView imgView;
        TextView productName;
        TextView price;


    }
}
