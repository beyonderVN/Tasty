package com.vnwarriors.tastyclarify.ui.activity.browser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vnwarriors.advancedui.appcore.common.viewpager.InkPageIndicator;
import com.vnwarriors.advancedui.appcore.common.viewpager.ModelPagerAdapter;
import com.vnwarriors.advancedui.appcore.common.viewpager.PagerModelManager;
import com.vnwarriors.advancedui.appcore.common.viewpager.ScrollerViewPager;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.ui.activity.authentication.login.LoginActivity;
import com.vnwarriors.tastyclarify.ui.activity.authentication.userprofile.ProfileActivity;
import com.vnwarriors.tastyclarify.ui.activity.createrecipe.CreateRecipesActivity;
import com.vnwarriors.tastyclarify.ui.activity.browser.fragment.AllPostFragment;
import com.vnwarriors.tastyclarify.ui.activity.browser.fragment.GuideFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BrowserActivity extends AppCompatActivity {
    private static final String TAG = "BrowserActivity";

    @BindView(R.id.appbar)
    AppBarLayout mAppBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.view_pager)
    ScrollerViewPager viewPager;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;


    ImageView ivAvatar;
    TextView tvNavigationHeader;
    TextView tvName;
    TextView tvEmail;
    TextView tvDes;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Log.d(TAG, "onCreate: " + "auth.getCurrentUser() is not null");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
            setContentView(R.layout.activity_browser);
            ButterKnife.bind(this);
            setupDrawable();
            authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        startActivity(new Intent(BrowserActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            };
            PagerModelManager manager = new PagerModelManager();
            manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
            ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
            viewPager.setAdapter(adapter);
            viewPager.fixScrollSpeed();
            InkPageIndicator springIndicator = (InkPageIndicator) findViewById(R.id.indicator);
            // just set viewPager
            springIndicator.setViewPager(viewPager);
        }


    }

    private List<String> getTitles() {
        ArrayList<String> list = new ArrayList<String>();
        return list;
    }

    private List<String> getBgRes() {
        ArrayList<String> list = new ArrayList<>();
        list.add("https://a2milk.co.uk/wp-content/uploads/foodtip01-copy205kb.png");
        list.add("https://a2milk.co.uk/wp-content/uploads/foodtip04-copy250kb.png");
        list.add("https://a2milk.co.uk/wp-content/uploads/foodtip03-copy147kb.png");
        list.add("https://a2milk.co.uk/wp-content/uploads/foodtip02-copy203kb.png");
        return list;
    }

    private void setupDrawable() {
        setupToolbar(mDrawer);
        setupNavigationMenu();
    }

    private void setupNavigationMenu() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View headerLayout = mNavigationView.getHeaderView(0);
        ivAvatar = (ImageView) headerLayout.findViewById(R.id.ivAvatar);
        tvNavigationHeader = (TextView) headerLayout.findViewById(R.id.tvNavigationHeader);
        tvName = (TextView) headerLayout.findViewById(R.id.tvName);
        tvName.setText(convertEmailToName(user.getEmail()));
        tvEmail = (TextView) headerLayout.findViewById(R.id.tvEmail);
        tvEmail.setText(user.getEmail());
        tvDes = (TextView) headerLayout.findViewById(R.id.tvDes);
        tvDes.setText("Today is good for cooking");
    }


    private String convertEmailToName(String email) {
        String[] s = email.split("@");
        return s[0];
    }


    private void setupToolbar(DrawerLayout drawer) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                boolean showTitle = (mCollapsingToolbar.getHeight() + verticalOffset) <= (mToolbar.getHeight() * 2);
                if (showTitle) {
                    mCollapsingToolbar.setTitle("Twitter");
                } else {
                    mCollapsingToolbar.setTitle("");
                }

            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(actionBarDrawerToggle);
    }

    private void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.your_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.favorite:
                break;
            case R.id.cookBook:
                break;
            case R.id.appetizer:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.dessert:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.first_course:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.main_course:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.side_dish:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.vegetarian:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.cheap:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.pizza:
                EventBus.getDefault().post(new AllPostFragment.CatalogueAdapterItemClickEvent(findCatalogueIdPosition(item.getItemId())));
                break;
            case R.id.sign_out:
                auth.signOut();
                break;
            default:
                break;
        }
        mDrawer.closeDrawers();
    }

    private int findCatalogueIdPosition(int itemId) {
        for (int i = 0; i < catalogueIds.length; i++) {
            if (catalogueIds[i] == itemId) {
                return i;
            }
        }
        return -1;
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.fab)
    void showDailog() {
        Intent intent = new Intent(this, CreateRecipesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    static int[] catalogueIds = {R.id.appetizer, R.id.dessert, R.id.first_course, R.id.main_course, R.id.side_dish, R.id.vegetarian, R.id.cheap, R.id.pizza};


    @Subscribe
    public void onCatalogueAdapterItemClickEvent(AllPostFragment.CatalogueAdapterItemClickEvent event) {
        if (event.position < 0) return;
        Log.d(TAG, "onMessageEvent: " + R.id.appetizer);
        Log.d(TAG, "onMessageEvent: " + catalogueIds[event.position]);
        mNavigationView.setCheckedItem(catalogueIds[event.position]);
    }

    public static class NavigationViewItemClickEvent {
        public final int position;

        NavigationViewItemClickEvent(int position) {
            this.position = position;
        }
    }
}
