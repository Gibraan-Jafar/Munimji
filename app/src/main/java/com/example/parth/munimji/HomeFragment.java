package com.example.parth.munimji;

/**
 * Created by parth on 28/6/16.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */
public class HomeFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.home_tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return x;
    }
    class MyAdapter extends FragmentPagerAdapter{
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        /**
         * Return fragment with respect to Position .
         */
        @Override
        public Fragment getItem(int position)
        {
            System.out.println("hello:"+position);
            switch (position){
                case 0 : return new Addnewowner_index();
                case 1 : return new Paymentin();
                case 2 : return new Paymentout();
                case 3: return new Listpaymentin_index();
                case 4:return new Listpayment_out_index();
                case 5:return new Tenantinfo();
                case 6:return new Companyinfo();
                case 7:return new Help();
                case 8: return new showPhone();
                case 9: return new showVehicle();
            }
            return null;
        }
        @Override
        public int getCount() {
            return int_items;
        }
        /**
         * This method returns the title of the tab according to the position.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "Owner Info";
                case 1 :
                    return "Credits";
                case 2 :
                    return "Debits";
                case 3:
                    return "List Credit";
                case 4:
                    return "List Debits";
                case 5:
                    return "Teanant Info";
                case 6:
                    return "Company info";
                case 7:
                    return "Helplines";
                case 8:
                    return "Mobile Nos";
                case 9:
                    return "Vehicle Info";
            }
            return null;
        }
    }
}
