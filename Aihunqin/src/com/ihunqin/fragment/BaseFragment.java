package com.ihunqin.fragment;

import java.util.ArrayList;
import java.util.Date;

import com.ihunqin.model.Task;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	static int groupp;
	static int childp;
	static ArrayList<Date> group = null;
	static ArrayList<ArrayList<Task>> child = null;
	static boolean newTask = false;
	static ArrayList<String> tags = null;
}
