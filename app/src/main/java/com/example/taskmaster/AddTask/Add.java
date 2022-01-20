package com.example.taskmaster.AddTask;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskmaster.DialogClosedListener;
import com.example.taskmaster.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Add extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";

    private EditText newTaskText;
    private Button newTaskSaveButton;
    private Button deleteButton;
    private DatabaseHandler db;

    public static Add newInstance(){
        return new Add();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get element id
        newTaskText = getView().findViewById(R.id.newTaskText);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);
        deleteButton = getView().findViewById(R.id.delete);
        // open db
        db = new DatabaseHandler(getActivity());
        db.openDatabase();
        // add task
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                TaskModel task = new TaskModel();
                task.setTask(text);
                task.setChecked(0);
                db.insertTask(task);
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogClosedListener)
            ((DialogClosedListener)activity).handleDialogClose(dialog);
    }
}

