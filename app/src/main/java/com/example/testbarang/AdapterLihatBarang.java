package com.example.testbarang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdapterLihatBarang extends RecyclerView.Adapter<AdapterLihatBarang.ViewHolder> {

    private ArrayList<Barang> daftarBarang;
    private Context context;
    private DatabaseReference database;

    public AdapterLihatBarang(ArrayList<Barang> barangs, Context ctx) {
        /*
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarBarang = barangs;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Inisiasi View
         * Disini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
         * Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*
         *  Menampilkan data pada view
         */
        final String name = daftarBarang.get(position).getNama();
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 *  untuk latihan Selanjutnya , jika ingin membaca detail data
                 */
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /*
                 *  untuk latihan Selanjutnya ,fungsi Delete dan Update data
                 */
                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.inflate(R.menu.menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String nama = daftarBarang.get(position).getNama();
                        String id = daftarBarang.get(position).getKode();
                        String key = daftarBarang.get(position).getMainKey();

                        switch (item.getItemId()) {
                            case R.id.editData:
                                Bundle bundle = new Bundle();
                                bundle.putString("id", id);
                                bundle.putString("key", key);
                                bundle.putString("nama", nama);

                                Intent intent = new Intent(context, UpdateBarang.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case R.id.hapusData:
                                showDeleteDialog(key, nama);
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
        holder.tvTitle.setText(name);
    }

    @Override
    public int getItemCount() {
        /*
         * Mengembalikan jumlah item pada barang
         */
        return daftarBarang.size();
    }

    private void showDeleteDialog(final String id, String nama) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Hapus Data");
        dialogBuilder.setMessage("Apakah anda ingin menghapus data " + nama + "?");
        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Delete
                delete(id);
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public void delete(String id) {
        try {
            database = FirebaseDatabase.getInstance().getReference().child("Barang").child(id);
            database.removeValue();
            Toast.makeText(context, "Data berhasil diperbaharui",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }
}

