package com.example.noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.noteapp.viewmodel.NoteViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ViewModelインスタンス生成
        NoteViewModel viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        //スピナー設定
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.creator_array, R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapter);

        //検索ボタンを選択した時のリスナ設定
        Button bt_search = view.findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Spinnerの値を取得し、idに変換→ViewModelに格納
                String creator = (String) spinner.getSelectedItem();
                String creator_id = convert_creator_id(creator);
                viewModel.getCreator_id().setValue(creator_id);

                //リスト表示フラグメントに置き換え
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                ListFragment listFragment = new ListFragment();
                transaction.replace(R.id.container,listFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    //クリエイター名からクリエイターidに変換するメソッド
    public String convert_creator_id(String creator) {

        String creator_id = "";

        switch (creator) {

            case "れぶ":
                creator_id = "leb397";
                break;

            case "どすこいさん":
                creator_id = "dosukoi_android";
                break;

            case "Ryoさん":
                creator_id = "ryosan1367";
                break;

            case "Naoya Kannukiさん":
                creator_id = "kankplus";
                break;

        }

        return creator_id;
    }
}