package com.example.parth.munimji;

/**
 * Created by parth on 28/6/16.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */
public class ExpoTabFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expo_tab_layout,null);
        return rootView;
    }
}
