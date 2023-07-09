package com.example.memorengandroid.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.memorengandroid.view.Pages.MainAreaPage;

public class CustomAlertBox {
    private Context context;

    public CustomAlertBox(Context context) {
        this.context = context;
    }

    public void OpenAlertBoxExit(Context context, String message, Boolean Exit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        TextView myMsg = new TextView(context);
        myMsg.setText("\n\n" + message);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        builder.setView(myMsg);

        builder.setCancelable(true);

        builder.setNegativeButton("İptal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Exit) {
                            dialog.cancel();
                        } else {
                            Intent intent = new Intent(context, MainAreaPage.class);
                            context.startActivity(intent);
                        }
                    }
                });

        builder.setPositiveButton("Evet", (DialogInterface.OnClickListener) (dialog, which) -> {
            if (Exit) {
                Intent intent = new Intent(context, MainAreaPage.class);
                context.startActivity(intent);
            } else {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void OpenAlertBoxError(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        TextView myMsg = new TextView(context);
        myMsg.setText("\n\n" + message);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        builder.setView(myMsg);

        builder.setCancelable(false);

        builder.setPositiveButton("Tamam", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
