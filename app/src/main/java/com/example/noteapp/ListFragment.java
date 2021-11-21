package com.example.noteapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.noteapp.api.ApiCall;
import com.example.noteapp.api.data.Articles;
import com.example.noteapp.api.NoteApiInterface;
import com.example.noteapp.api.data.Content;
import com.example.noteapp.api.data.Data;
import com.example.noteapp.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lv_article;

    public ListFragment() {
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
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ViewModelインスタンス生成
        NoteViewModel viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        //ViewModelからcreator_idを取得
        String creator_id = viewModel.getCreator_id().getValue();

        lv_article = view.findViewById(R.id.lv_article);

        //API実行→ListView表示
        ApiCall call = new ApiCall();
        call.api_execution(creator_id,lv_article,getActivity());

        //ListViewの各アイテムが選択された時のリスナー設定
        lv_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //記事のURLを取得し、Bundleに埋め込み
                Bundle bundle = new Bundle();
                Map<String,String> article = (Map<String, String>) parent.getItemAtPosition(position);
                String noteUrl = article.get("noteUrl");
                bundle.putString("noteUrl",noteUrl);

                //WebViewフラグメントに置き換え
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                WebViewFragment webViewFragment = new WebViewFragment();
                webViewFragment.setArguments(bundle);
                transaction.replace(R.id.container,webViewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //戻りボタンを押した際のリスナー設定
        Button bt_return = view.findViewById(R.id.bt_return);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //検索フラグメントに置き換え
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                SearchFragment searchFragment = new SearchFragment();
                transaction.replace(R.id.container,searchFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}