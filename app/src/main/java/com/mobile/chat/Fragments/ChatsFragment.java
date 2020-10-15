package com.mobile.chat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.chat.Adapter.GroupAdapter;
import com.mobile.chat.Modal.Group;
import com.mobile.chat.R;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {
    private RecyclerView recyclerView;

    private GroupAdapter groupAdapter;
    private List<Group> mGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView= view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mGroup = new ArrayList<>();
        readUsers();

        return view;
    }

    private void readUsers() {

        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Groups");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGroup.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Group group = snapshot.getValue(Group.class);
                    assert  group!= null;
                    assert firebaseUser!= null;
                    if(!group.getId().equals(firebaseUser.getUid())){
                        mGroup.add(group);
                    }
                }
                groupAdapter = new GroupAdapter(getContext(), mGroup);
                recyclerView.setAdapter(groupAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
