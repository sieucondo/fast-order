package com.android.fastorder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import adapter.CartAdapter;
import model.Cart;
import model.CartItem;
import model.Product;
import util.ProductCart;
import util.ProductPayment;

public class CartActivity extends AppCompatActivity {
    CartAdapter productCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar cartToolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(cartToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        ListView listViewProduct = findViewById(R.id.listProductCart);//Tao listView
        productCartAdapter = new CartAdapter(getListData(), this);//Truyen list vao adapter.
        listViewProduct.setAdapter(productCartAdapter);//Truyen adapter vao listView
        updateOrderInfo();//Cap nhat giao dien cart
    }


    //Quay lai activity cu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void clickToOrder(View view) {
        if (ProductCart.getCart().getListProduct().size() > 0) {
            createJsonOrder();
            CreateBill();
            updateOrderInfo();
            Toast.makeText(CartActivity.this, "Thanks for you order :)", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(CartActivity.this, "Add something to your cart!", Toast.LENGTH_SHORT).show();
        }
    }

    public String createJsonOrder() {
        Gson gsonBuilder = new GsonBuilder().create();
        String json;
        Cart cart = ProductCart.getCart();
        List<Product> listProduct = new ArrayList<>();
//        for(CartItem p: cart.getListProduct()){
//            listProduct.add(p.getProduct());
//        }
        json = gsonBuilder.toJson(cart.getListProduct());
        Log.e("Json", json);
        return json;
    }

    public void CreateBill() {
        Cart bill = ProductPayment.getBill();
        Cart cart = ProductCart.getCart();
        List<CartItem> listItem = bill.getListProduct();
        for (CartItem c : cart.getListProduct()) {
            if (listItem.contains(c)) {
                int index = listItem.indexOf(c);
                int quantity = listItem.get(index).getQuantity();
                quantity += c.getQuantity();
                listItem.get(index).setQuantity(quantity);
            } else {
                listItem.add(c);
            }
        }
        bill.setCartInfo();
        productCartAdapter.getListCart().clear();
        productCartAdapter.notifyDataSetChanged();
        cart.clear();
        cart.setCartInfo();
    }

    public void clickToRemove(View view) {
        View parentRow = (View) view.getParent();//Nhan view cha cua btn
        ListView listView = (ListView) parentRow.getParent();//Nhan view cha cua itemView
        final int position = listView.getPositionForView(parentRow);//Lay vi tri itemView tu view cha
        showDialogRemove(position);
    }

    public void clickToEdit(View view) {
        View parentRow = (View) view.getParent();//Nhan view cha cua btn
        ListView listView = (ListView) parentRow.getParent();//Nhan view cha cua itemView
        final int position = listView.getPositionForView(parentRow);//Lay vi tri itemView tu view cha

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_dialog);

        final TextView txtQuantity = dialog.findViewById(R.id.txtEditQuantity);
        int quantity = productCartAdapter.getListCart().get(position).getQuantity();
        txtQuantity.setText(String.valueOf(quantity));


        Button btnIncrease = dialog.findViewById(R.id.btnIncrease);
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                quantity += 1;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        Button btnDecrease = dialog.findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                quantity -= 1;
                if (quantity < 1) {
                    quantity = 1;
                }
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productCartAdapter.getListCart().get(position).setQuantity(Integer.parseInt(txtQuantity.getText().toString()));
                productCartAdapter.notifyDataSetChanged();

                Cart cart = ProductCart.getCart();
                cart.setListProduct(productCartAdapter.getListCart());
                cart.setCartInfo();
                updateOrderInfo();
                dialog.dismiss();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setTitle("Edit Product Quantity");
        dialog.setCancelable(false);
        dialog.show();

    }


    private List<CartItem> getListData() {
        Cart cart = ProductCart.getCart();
        List<CartItem> listProducts = cart.getListProduct();
        return listProducts;
    }


    public void updateOrderInfo() {
        NumberFormat formatter = new DecimalFormat("#,###");
        TextView txtTotalItem = findViewById(R.id.txtTotalProduct);
        TextView txtTotalPrice = findViewById(R.id.txtTotalPrice);
        Cart cart = ProductCart.getCart();

        String txtProduct;
        if (cart.getTotalQuantity() > 1) {
            txtProduct = "Products";
        } else {
            txtProduct = "Product";
        }

        txtTotalItem.setText(cart.getTotalQuantity() + " " + txtProduct);
        txtTotalPrice.setText(formatter.format(cart.getTotalPrice()) + " đ");
    }

    public void showDialogRemove(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Product");
        builder.setMessage(R.string.confirm_remove_product);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                productCartAdapter.getListCart().remove(position);//Xoa vi tri theo positon trong listProduct cua adapter
                productCartAdapter.notifyDataSetChanged();//Gui thong bao cho list view la adapter da thay doi view.

                Cart cart = ProductCart.getCart();
                cart.setListProduct(productCartAdapter.getListCart());

                updateOrderInfo();
                Toast.makeText(CartActivity.this, "Thanks for you order", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
