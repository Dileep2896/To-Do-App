package com.technologybit.todo.Category.Helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.technologybit.todo.R;

import java.util.Objects;

public class DialogAdd extends AppCompatDialogFragment {

    public EditText etAddCategory;
    private DialogAddListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_input, null);

        builder.setView(view)
                .setTitle("Add Category")
                .setPositiveButton("ADD", (dialogInterface, i) -> {
                    String category = etAddCategory.getText().toString();
                    listener.addTexts(category);
                });

        etAddCategory = view.findViewById(R.id.etAddCategory);
        etAddCategory.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etAddCategory.requestFocus();

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogAddListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Must implement DialogAddListener");
        }
    }

    public interface DialogAddListener {
        default void addTexts(String Category) {

        }
    }

}
