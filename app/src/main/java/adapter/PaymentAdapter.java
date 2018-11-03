package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.fastorder.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import model.CartItem;

public class PaymentAdapter extends BaseAdapter {
    private List<CartItem> listCart;
    private LayoutInflater layoutInflater;
    private Context context;

    public PaymentAdapter(List<CartItem> listCart, Context context) {
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
        PaymentAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.payment_item, null);
            holder = new PaymentAdapter.ViewHolder();
            holder.productName = convertView.findViewById(R.id.txtProductPayName);
            holder.price = convertView.findViewById(R.id.txtProductPayPrice);
            holder.quantity = convertView.findViewById(R.id.txtProductPayQuantity);
            convertView.setTag(holder);
        } else {
            holder = (PaymentAdapter.ViewHolder) convertView.getTag();
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        CartItem cart = this.listCart.get(position);

        holder.productName.setText(cart.getProduct().getProductName());
        holder.price.setText(formatter.format(cart.getTotalPrices()) + " đ");
        holder.quantity.setText("" + cart.getQuantity());

        return convertView;
    }


    static class ViewHolder {
        private TextView productName;
        private TextView price;
        private TextView quantity;


    }
}
