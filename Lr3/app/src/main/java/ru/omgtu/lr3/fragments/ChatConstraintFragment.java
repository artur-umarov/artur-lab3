package ru.omgtu.lr3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.omgtu.lr3.R;

public class ChatConstraintFragment extends Fragment {

    private LinearLayout messagesContainer;
    private EditText editMessage;
    private Button btnSend;
    private int messageCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_constraint, container, false);

        messagesContainer = view.findViewById(R.id.messagesContainer);
        editMessage = view.findViewById(R.id.editMessage);
        btnSend = view.findViewById(R.id.btnSend);

        addTestMessages();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        return view;
    }

    private void addTestMessages() {
        for (int i = 1; i <= 5; i++) {
            addMessage("Тестовое сообщение " + i);
        }
    }

    private void sendMessage() {
        String messageText = editMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            addMessage(messageText);
            editMessage.setText("");
        }
    }

    private void addMessage(String text) {
        messageCount++;
        TextView messageView = new TextView(getContext());
        messageView.setText(text + " (" + messageCount + ")");
        messageView.setPadding(16, 16, 16, 16);
        messageView.setTextSize(16);
        messagesContainer.addView(messageView);
    }
}