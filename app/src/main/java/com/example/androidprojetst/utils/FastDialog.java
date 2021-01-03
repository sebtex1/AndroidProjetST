package com.example.androidprojetst.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.androidprojetst.R;

public class FastDialog {

    public static final int PROGRESS_DIALOG = 0;
    public static final int SIMPLE_DIALOG = 1;

    public static void showDialog(Context c, int typeDialog, String message) {
        if (c != null) {
            ProgressDialog progressDialog;
            switch (typeDialog) {
                case PROGRESS_DIALOG:
                    // Looper.prepare();
                    progressDialog = new ProgressDialog(c);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage(message);
                case SIMPLE_DIALOG:

                    AlertDialog.Builder builder = new AlertDialog.Builder(c);

                    builder.setMessage(message);
                    builder.setCancelable(false);

                    builder.setPositiveButton(R.string.ok, null);

                    builder.create().show();
            }
        }
    }

    public static void showDialog(Context c, int typeDialog, String message,
                                  DialogInterface.OnClickListener callbk) {
        if (c != null) {
            ProgressDialog progressDialog;
            switch (typeDialog) {
                case PROGRESS_DIALOG:
                    // Looper.prepare();
                    progressDialog = new ProgressDialog(c);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage(message);

                case SIMPLE_DIALOG:
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);

                    builder.setMessage(message);

                    builder.setPositiveButton(R.string.ok, callbk);
                    builder.setCancelable(false);

                    builder.create().show();
            }
        }
    }

    public static void showDialog(Context c, int typeDialog, String message,
                                  DialogInterface.OnClickListener callbk, DialogInterface.OnClickListener callbk2) {
        if (c != null) {
            ProgressDialog progressDialog;
            switch (typeDialog) {
                case PROGRESS_DIALOG:
                    // Looper.prepare();
                    progressDialog = new ProgressDialog(c);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage(message);

                case SIMPLE_DIALOG:
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setMessage(message);

                    builder.setPositiveButton(R.string.ok, callbk);
                    builder.setNegativeButton(R.string.cancel, callbk2);
                    builder.setCancelable(false);

                    builder.create().show();
            }
        }
    }

    public static Dialog showProgressDialog(Context c, String message) {
        ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
