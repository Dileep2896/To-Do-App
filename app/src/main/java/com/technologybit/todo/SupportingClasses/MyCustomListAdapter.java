package com.technologybit.todo.SupportingClasses;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.technologybit.todo.Password.PasswordManagerList;
import com.technologybit.todo.R;

import java.util.List;

public class MyCustomListAdapter extends ArrayAdapter<PasswordManagerList> {

    Context context;
    int resource;
    List<PasswordManagerList> passList;
    boolean auth;
    Boolean clicked = Boolean.FALSE;

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
        ImageButton ibVisibility = view.findViewById(R.id.ibVisibility);
        ImageView ivPerson = view.findViewById(R.id.ivPerson);
        ImageView ivKey = view.findViewById(R.id.ivKey);

        ibVisibility.setTag(clicked);

        PasswordManagerList ps = passList.get(position);

        ib.setAlpha((float) 0.5);
        ib.setEnabled(false);
        ib.setClickable(false);

        username.setText(ps.getUsername());
        password.setText("************");

        if (auth) {
            if (position % 2 == 0) {
                ibVisibility.setColorFilter(Color.WHITE);
            } else {
                ibVisibility.setColorFilter(Color.rgb(57, 62, 70));
            }
        } else {
            ibVisibility.setColorFilter(Color.rgb(240, 84, 84));
            password.setText("************");
            ib.setAlpha((float) 0.5);
            ib.setEnabled(false);
            ib.setClickable(false);
        }

        if (position % 2 == 0) {
            bgShape.setColor(Color.rgb(22, 135, 167));
            username.setTextColor(Color.rgb(246, 245, 245));
            password.setTextColor(Color.rgb(81, 194, 213));
            ib.setColorFilter(Color.rgb(246, 245, 245));
            ivPerson.setColorFilter(Color.rgb(216, 227, 231));
            ivKey.setColorFilter(Color.rgb(216, 227, 231));
        } else {
            bgShape.setColor(Color.rgb(216, 227, 231));
            username.setTextColor(Color.rgb(57, 62, 70));
            password.setTextColor(Color.rgb(18, 110, 130));
            ib.setColorFilter(Color.rgb(57, 62, 70));
            ivPerson.setColorFilter(Color.rgb(22, 135, 167));
            ivKey.setColorFilter(Color.rgb(22, 135, 167));
        }

        ib.setOnClickListener(view1 -> {
            ClipboardManager clipboard = (ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Password", ps.getPassword());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(),
                    "Password Copied To Clipboard", Toast.LENGTH_SHORT).show();

        });

        Log.i("Drawable", String.valueOf(ibVisibility.getColorFilter()));

        ibVisibility.setOnClickListener(view12 -> {
            if (auth) {
                if(((Boolean) ibVisibility.getTag())){
                    ibVisibility.setImageResource(R.drawable.ic_baseline_visibility_off);
                    password.setText("************");
                    ibVisibility.setTag(Boolean.FALSE);
                    ib.setAlpha((float) 0.5);
                    ib.setEnabled(false);
                    ib.setClickable(false);
                } else {
                    ibVisibility.setImageResource(R.drawable.ic_baseline_visibility);
                    password.setText(ps.getPassword());
                    ibVisibility.setTag(Boolean.TRUE);
                    ib.setAlpha((float) 1);
                    ib.setEnabled(true);
                    ib.setClickable(true);
                }
            } else {
                Toast.makeText(getContext(), "Please Authenticate Yourself.\n" +
                                "Click The Lock 🔒",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    public void authentication(boolean auth) {
        this.auth = auth;
    }

}
