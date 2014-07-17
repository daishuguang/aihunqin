package com.aihunqin.fragment;

import com.example.aihunqin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentInvitationCreateNew extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_invitation_createnew,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

	}
}
