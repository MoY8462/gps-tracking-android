package dgtic.unam.gps_tracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dgtic.unam.gps_tracking.databinding.ActivityLayoutBinding

class LayoutActivity : AppCompatActivity() {

    lateinit var binding: ActivityLayoutBinding
    var mapFragment: MapFragment = MapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.container,mapFragment)

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.item_1 -> {
                    // Respond to navigation item 1 click
                    supportFragmentManager.beginTransaction().replace(R.id.container,mapFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.item_2 -> {
                    // Respond to navigation item 2 click
                    print("Map")
                    true
                }
                else -> false
            }
        }
    }

}