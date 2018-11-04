package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.android.fastorder.R;

import model.CartItem;
import util.DownloadImageTask;

public class CartAdapter extends BaseAdapter {
    private List<CartItem> listCart;
    private LayoutInflater layoutInflater;
    private Context context;

    public CartAdapter(List<CartItem> listCart, Context context) {
        this.listCart = listCart;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public List<CartItem> getListCart() {
        return listCart;
    }

    @Override
    public int getCount() {
        return listCart.size();
    }

    @Override
    public Object getItem(int position) {
        return listCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listCart.get(position).getProduct().getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.product_cart_item, null);
            holder = new CartAdapter.ViewHolder();
            holder.productName = convertView.findViewById(R.id.txtProductName);
            holder.price = convertView.findViewById(R.id.txtPrice);
            holder.imgView = convertView.findViewById(R.id.imageProduct);
            holder.quantity = convertView.findViewById(R.id.txtQuantity);
            holder.totalPrice = convertView.findViewById(R.id.txtProductTotalPrice);
            convertView.setTag(holder);
        } else {
            holder = (CartAdapter.ViewHolder) convertView.getTag();
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        CartItem cart = this.listCart.get(position);

        holder.productName.setText(cart.getProduct().getProductName());
        holder.price.setText("Price: " + formatter.format(cart.getProduct().getPrice()));
        new DownloadImageTask(holder.imgView).execute(
                "https://images.theconversation.com/files/126820/original/image-20160615-14016-njqw65.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=926&fit=clip");
        holder.quantity.setText("x " + cart.getQuantity());
        holder.totalPrice.setText("= " + formatter.format(cart.getTotalPrices()) + " đ");

        return convertView;
    }


    static class ViewHolder {
        private ImageView imgView;
        private TextView productName;
        private TextView price;
        private TextView quantity;
        private TextView totalPrice;


    }
}
