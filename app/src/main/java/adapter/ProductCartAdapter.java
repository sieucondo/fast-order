package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.android.fastorder.R;
import model.Product;

public class ProductCartAdapter extends BaseAdapter {
    private List<Product> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductCartAdapter(Context aContext,  List<Product> listData) {
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
        ProductAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.product_cart_item, null);
            holder = new ProductAdapter.ViewHolder();
            holder.productName = convertView.findViewById(R.id.txtProductName);
            holder.price = convertView.findViewById(R.id.txtPrice);
            holder.imgView = convertView.findViewById(R.id.imageProduct);
            convertView.setTag(holder);
        } else {
            holder = (ProductAdapter.ViewHolder) convertView.getTag();
        }

        Product product = this.listData.get(position);
        int imageId = product.getImageId();

        holder.productName.setText(product.getProductName());
        holder.price.setText("Price: " + product.getPrice());
        holder.imgView.setImageResource(R.mipmap.ic_launcher_round);

        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
//    public int getMipmapResIdByName(String resName)  {
//        String pkgName = context.getPackageName();
//
//        // Trả về 0 nếu không tìm thấy.
//        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
//        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
//        return resID;
//    }

    static class ViewHolder {
        ImageView imgView;
        TextView productName;
        TextView price;
    }
}
