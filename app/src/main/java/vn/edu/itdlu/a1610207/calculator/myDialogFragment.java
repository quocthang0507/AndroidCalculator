package vn.edu.itdlu.a1610207.calculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class myDialogFragment extends android.app.DialogFragment {
    String title;
    String message;
    String button;

    public myDialogFragment() {
        title = "About...";
        message = "Author: La Quốc Thắng \r\n" +
                "Student ID: 1610207\r\n" +
                "Study at: Dalat University\r\n\n" +
                "Email 1: quocthang0507@gmail.com\r\n" +
                "Email 2: quocthang_0507@yahoo.com.vn\r\n";
        button = "OK, Thank author a lot :)))";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setButton(String button) {
        this.button = button;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
