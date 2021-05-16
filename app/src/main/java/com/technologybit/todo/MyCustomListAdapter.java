package com.technologybit.todo;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class MyCustomListAdapter extends ArrayAdapter<PasswordManagerList> {

    Context context;
    int resource;
    List<PasswordManagerList> passList;

    public MyCustomListAdapter(Context context, int resource, List<PasswordManagerList> passList) {
        super(context, resource, passList);

        this.context = context;
        this.resource = resource;
        this.passList = passList;

    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint({"ViewHolder", "InflateParams"})
        View view = inflater.inflate(R.layout.my_list_view, null);

        GradientDrawable bgShape = (GradientDrawable) view.getBackground();

        TextView username = view.findViewById(R.id.tvUsername);
        TextView password = view.findViewById(R.id.tvPasswordField);
        ImageButton ib = view.findViewById(R.id.ibCopy);

        PasswordManagerList ps = passList.get(position);

        username.setText("User     " + ps.getUsername());
        password.setText("Pass     " + ps.getPassword());

        if (position % 2 == 0) {
            bgShape.setColor(Color.rgb(57, 62, 70));
            username.setTextColor(Color.rgb(238, 238, 238));
            password.setTextColor(Color.rgb(0, 173, 181));
        } else {
            bgShape.setColor(Color.rgb(216, 227, 231));
            username.setTextColor(Color.rgb(19, 44, 51));
            password.setTextColor(Color.rgb(18, 110, 130));
            ib.setColorFilter(Color.rgb(57, 62, 70));
        }

        view.findViewById(R.id.ibCopy).setOnClickListener(view1 -> {
            ClipboardManager clipboard = (ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Password", ps.getPassword());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(),
                    "Password Copied To Clipboard", Toast.LENGTH_SHORT).show();
        });

        return view;

    }
}
