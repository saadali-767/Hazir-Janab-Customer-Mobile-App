package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchMarket extends AppCompatActivity implements RecyclerViewInterface{

    ProductAdapter adapter;
    RecyclerView rv;
    List<Product> productList;
    String serviceType, serviceCategory;


    @Override
    protected void onResume() {
        super.onResume(); // Always call the superclass method first

        // Your code to update the ivSupport ImageView
        ImageView ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty()){
            ivSupport.setImageResource(R.drawable.fullcart);
        } else {
            // You can set a default image if the conditions are not met
            ivSupport.setImageResource(R.drawable.ic_cart); // Assuming you have an 'emptycart' drawable
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_market);

        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty() ){
            ivSupport.setImageResource(R.drawable.fullcart);
        }
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SearchAllServices.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchMarket.this, Support_11.class);
                startActivity(intent);
            }
        });

        //get attributes that were passed through an intent
        serviceType = getIntent().getStringExtra("serviceType");
        Log.d("servicetype in SearchAll",serviceType );

        serviceCategory = getIntent().getStringExtra("serviceCategory");
        Log.d("serviceCategory in SearchAll",serviceCategory );

        rv = findViewById(R.id.rv);

        productList = new ArrayList<>();
        fetchProductsFromDatabase();


        //productList.add(new Product(1, "Bulb", "This product is a bulb", 10, "Electrician", 100, true));
//        productList.add(new Product(2, "Drain Unclogging", "1200", "This service offers expert drain unclogging in Lahore", "Lahore", "Plumber", "Appointment", "image2.jpg", "video2.mp4"));
//        productList.add(new Product(3,"Transmission Overhaul", "8000", "This service offers comprehensive transmission overhaul in Karachi", "Karachi", "Mechanic", "Emergency", "image3.jpg", "video3.mp4"));
//        productList.add(new Product(4, "Wooden Deck Installation", "5500", "This service offers custom wooden deck installation in Islamabad", "Islamabad", "Carpenter", "Appointment", "image4.jpg", "video4.mp4"));
//        productList.add(new Product(5, "Electrical Wiring Inspection", "1900", "This service offers thorough electrical wiring inspection in Lahore", "Lahore", "Electrician", "Appointment", "image5.jpg", "video5.mp4"));
//        productList.add(new Product(6, "Leaky Faucet Repair", "1300", "This service offers leaky faucet repair and maintenance in Karachi", "Karachi", "Plumber", "Emergency", "image6.jpg", "video6.mp4"));
//        productList.add(new Product(7, "Oil Change Service", "2500", "This service offers quick and efficient oil change for your car in Islamabad", "Islamabad", "Mechanic", "Appointment", "image7.jpg", "video7.mp4"));
//        productList.add(new Product(8, "Hardwood Flooring", "6000", "This service offers hardwood flooring installation and finishing in Lahore", "Lahore", "Carpenter", "Appointment", "image8.jpg", "video8.mp4"));
//        productList.add(new Product(9, "Outdoor Lighting Setup", "2200", "This service offers outdoor lighting installation and design in Karachi", "Karachi", "Electrician", "Emergency", "image9.jpg", "video9.mp4"));
//        productList.add(new Product(10, "Sump Pump Repair", "1400", "This service offers sump pump repair and installation in Islamabad", "Islamabad", "Plumber", "Appointment", "image10.jpg", "video10.mp4"));
//        productList.add(new Product(11, "Fuse Box Repair", "1800", "This service offers fuse box repair and safety checks in Karachi", "Karachi", "Electrician", "Emergency", "image11.jpg", "video11.mp4"));
//        productList.add(new Product(12, "Bathroom Plumbing", "2200", "This service offers comprehensive bathroom plumbing in Lahore", "Lahore", "Plumber", "Appointment", "image12.jpg", "video12.mp4"));
//        productList.add(new Product(13, "Tire Balancing", "1500", "This service offers tire balancing for smoother vehicle handling in Islamabad", "Islamabad", "Mechanic", "Emergency", "image13.jpg", "video13.mp4"));
//        productList.add(new Product(14, "Door Framing", "3200", "This service offers door framing and installation in Karachi", "Karachi", "Carpenter", "Appointment", "image14.jpg", "video14.mp4"));
//        productList.add(new Product(15, "Lighting Installation", "1900", "This service offers indoor and outdoor lighting installation in Lahore", "Lahore", "Electrician", "Appointment", "image15.jpg", "video15.mp4"));
//        productList.add(new Product(16, "Septic Tank Services", "2800", "This service offers septic tank maintenance and repair in Islamabad", "Islamabad", "Plumber", "Emergency", "image16.jpg", "video16.mp4"));
//        productList.add(new Product(17, "Engine Overhaul", "4000", "This service offers complete engine overhaul for enhanced performance in Lahore", "Lahore", "Mechanic", "Appointment", "image17.jpg", "video17.mp4"));
//        productList.add(new Product(18, "Window Installation", "3600", "This service offers window installation and repair in Karachi", "Karachi", "Carpenter", "Emergency", "image18.jpg", "video18.mp4"));
//        productList.add(new Product(19, "Electrical Inspections", "2100", "This service offers comprehensive electrical inspections for safety in Islamabad", "Islamabad", "Electrician", "Appointment", "image19.jpg", "video19.mp4"));
//        productList.add(new Product(20, "Drain Cleaning", "1600", "This service offers thorough drain cleaning to prevent blockages in Lahore", "Lahore", "Plumber", "Emergency", "image20.jpg", "video20.mp4"));
//        productList.add(new Product(21, "Air Conditioning Repair", "3500", "This service offers air conditioning repair for optimal cooling in Karachi", "Karachi", "Electrician", "Emergency", "image21.jpg", "video21.mp4"));
//        productList.add(new Product(22, "Kitchen Plumbing Fixtures", "2400", "This service offers installation and repair of kitchen plumbing fixtures in Islamabad", "Islamabad", "Plumber", "Appointment", "image22.jpg", "video22.mp4"));
//        productList.add(new Product(23, "Brake System Repair", "2800", "This service offers comprehensive brake system repair for safety in Lahore", "Lahore", "Mechanic", "Emergency", "image23.jpg", "video23.mp4"));
//        productList.add(new Product(24, "Hardwood Flooring", "4100", "This service offers hardwood flooring installation and maintenance in Karachi", "Karachi", "Carpenter", "Appointment", "image24.jpg", "video24.mp4"));
//        productList.add(new Product(25, "Generator Repair", "2000", "This service offers generator repair and maintenance in Islamabad", "Islamabad", "Electrician", "Appointment", "image25.jpg", "video25.mp4"));
//        productList.add(new Product(26, "Water Line Repair", "2600", "This service offers water line repair and replacement in Lahore", "Lahore", "Plumber", "Emergency", "image26.jpg", "video26.mp4"));
//        productList.add(new Product(27, "Suspension Tuning", "3000", "This service offers vehicle suspension tuning for optimal performance in Karachi", "Karachi", "Mechanic", "Appointment", "image27.jpg", "video27.mp4"));
//        productList.add(new Product(28, "Cabinet Making", "3800", "This service offers custom cabinet making for homes and offices in Islamabad", "Islamabad", "Carpenter", "Emergency", "image28.jpg", "video28.mp4"));
//        productList.add(new Product(29, "Home Electrical Wiring", "2300", "This service offers home electrical wiring and safety inspections in Lahore", "Lahore", "Electrician", "Appointment", "image29.jpg", "video29.mp4"));
//        productList.add(new Product(30, "Toilet Repair and Installation", "1700", "This service offers toilet repair and installation services in Karachi", "Karachi", "Plumber", "Emergency", "image30.jpg", "video30.mp4"));
//        productList.add(new Product(31, "Exhaust System Repair", "3200", "This service offers exhaust system repair for vehicles in Islamabad", "Islamabad", "Mechanic", "Emergency", "image31.jpg", "video31.mp4"));
//        productList.add(new Product(32, "Custom Furniture Design", "4200", "This service offers custom furniture design and crafting in Lahore", "Lahore", "Carpenter", "Appointment", "image32.jpg", "video32.mp4"));
//        productList.add(new Product(33, "Outdoor Lighting Setup", "2100", "This service offers outdoor lighting setup and maintenance in Karachi", "Karachi", "Electrician", "Appointment", "image33.jpg", "video33.mp4"));
//        productList.add(new Product(34, "Sump Pump Installation", "2700", "This service offers sump pump installation and repair in Islamabad", "Islamabad", "Plumber", "Emergency", "image34.jpg", "video34.mp4"));
//        productList.add(new Product(35, "Wheel and Tire Services", "2500", "This service offers wheel and tire services, including alignment and rotation in Lahore", "Lahore", "Mechanic", "Appointment", "image35.jpg", "video35.mp4"));
//        productList.add(new Product(36, "Deck Building", "3900", "This service offers deck building and repair services in Karachi", "Karachi", "Carpenter", "Emergency", "image36.jpg", "video36.mp4"));
//        productList.add(new Product(37, "Electrical Panel Upgrades", "3100", "This service offers electrical panel upgrades for enhanced safety in Islamabad", "Islamabad", "Electrician", "Appointment", "image37.jpg", "video37.mp4"));
//        productList.add(new Product(38, "Shower and Tub Installation", "2900", "This service offers shower and tub installation in Lahore", "Lahore", "Plumber", "Emergency", "image38.jpg", "video38.mp4"));
//        productList.add(new Product(39, "Oil Change and Lubrication", "1800", "This service offers oil change and lubrication for better engine performance in Karachi", "Karachi", "Mechanic", "Appointment", "image39.jpg", "video39.mp4"));
//        productList.add(new Product(40, "Interior Woodwork", "4300", "This service offers custom interior woodwork and design in Islamabad", "Islamabad", "Carpenter", "Emergency", "image40.jpg", "video40.mp4"));
//        productList.add(new Product(41, "Security System Installation", "2200", "This service offers security system installation and maintenance in Lahore", "Lahore", "Electrician", "Appointment", "image41.jpg", "video41.mp4"));
//        productList.add(new Product(42, "Gas Line Services", "2800", "This service offers gas line installation and repair in Karachi", "Karachi", "Plumber", "Emergency", "image42.jpg", "video42.mp4"));
//        productList.add(new Product(43, "Transmission Services", "3600", "This service offers transmission repair and maintenance in Islamabad", "Islamabad", "Mechanic", "Appointment", "image43.jpg", "video43.mp4"));
//        productList.add(new Product(44, "Fence and Gate Construction", "4000", "This service offers fence and gate construction for enhanced property security in Lahore", "Lahore", "Carpenter", "Emergency", "image44.jpg", "video44.mp4"));
//        productList.add(new Product(45, "Home Automation Services", "3300", "This service offers home automation setup and troubleshooting in Karachi", "Karachi", "Electrician", "Appointment", "image45.jpg", "video45.mp4"));
//        productList.add(new Product(46, "Radiant Floor Heating Installation", "3000", "This service offers radiant floor heating installation for comfortable homes in Islamabad", "Islamabad", "Plumber", "Emergency", "image46.jpg", "video46.mp4"));
//        productList.add(new Product(47, "AC and Heating System Repair", "3700", "This service offers AC and heating system repair for optimal climate control in Lahore", "Lahore", "Mechanic", "Appointment", "image47.jpg", "video47.mp4"));
//        productList.add(new Product(48, "Pergola and Gazebo Construction", "4100", "This service offers pergola and gazebo construction for beautiful outdoor spaces in Karachi", "Karachi", "Carpenter", "Emergency", "image48.jpg", "video48.mp4"));
//        productList.add(new Product(49, "Smart Home Wiring", "2400", "This service offers smart home wiring for integrated home systems in Islamabad", "Islamabad", "Electrician", "Appointment", "image49.jpg", "video49.mp4"));
//        productList.add(new Product(50, "Emergency Pipe Repair", "2500", "This service offers emergency pipe repair for urgent plumbing issues in Lahore", "Lahore", "Plumber", "Emergency", "image50.jpg", "video50.mp4"));
//        productList.add(new Product(51, "Vehicle Electrical System Check", "3400", "This service offers a comprehensive check of the vehicle's electrical system in Karachi", "Karachi", "Mechanic", "Emergency", "image51.jpg", "video51.mp4"));
//        productList.add(new Product(52, "Hardwood Refinishing", "4200", "This service offers hardwood floor refinishing for a renewed look in Islamabad", "Islamabad", "Carpenter", "Appointment", "image52.jpg", "video52.mp4"));
//        productList.add(new Product(53, "Surge Protection Installation", "2300", "This service offers surge protection installation for electrical safety in Lahore", "Lahore", "Electrician", "Appointment", "image53.jpg", "video53.mp4"));
//        productList.add(new Product(54, "Sewer Line Repair", "3100", "This service offers sewer line repair and maintenance in Karachi", "Karachi", "Plumber", "Emergency", "image54.jpg", "video54.mp4"));
//        productList.add(new Product(55, "Clutch Repair and Replacement", "3500", "This service offers clutch repair and replacement for manual vehicles in Islamabad", "Islamabad", "Mechanic", "Appointment", "image55.jpg", "video55.mp4"));
//        productList.add(new Product(56, "Custom Bookcases", "4500", "This service offers custom bookcase design and installation in Lahore", "Lahore", "Carpenter", "Emergency", "image56.jpg", "video56.mp4"));
//        productList.add(new Product(57, "Wiring Upgrade", "2600", "This service offers wiring upgrades for older homes in Karachi", "Karachi", "Electrician", "Appointment", "image57.jpg", "video57.mp4"));
//        productList.add(new Product(58, "Irrigation System Installation", "3300", "This service offers irrigation system installation and repair in Islamabad", "Islamabad", "Plumber", "Emergency", "image58.jpg", "video58.mp4"));
//        productList.add(new Product(59, "Car Battery Services", "1900", "This service offers car battery replacement and maintenance in Lahore", "Lahore", "Mechanic", "Appointment", "image59.jpg", "video59.mp4"));
//        productList.add(new Product(60, "Wooden Deck Repair", "3800", "This service offers wooden deck repair and restoration in Karachi", "Karachi", "Carpenter", "Emergency", "image60.jpg", "video60.mp4"));
//        productList.add(new Product(61, "Ceiling Fan Installation", "2500", "This service offers ceiling fan installation and repair in Islamabad", "Islamabad", "Electrician", "Appointment", "image61.jpg", "video61.mp4"));
//        productList.add(new Product(62, "Hot Water System Repair", "2900", "This service offers hot water system repair and maintenance in Lahore", "Lahore", "Plumber", "Emergency", "image62.jpg", "video62.mp4"));
//        productList.add(new Product(63, "Auto Air Conditioning Service", "3600", "This service offers auto air conditioning repair and recharge in Karachi", "Karachi", "Mechanic", "Appointment", "image63.jpg", "video63.mp4"));
//        productList.add(new Product(64, "Custom Kitchen Cabinets", "4300", "This service offers custom kitchen cabinet design and installation in Islamabad", "Islamabad", "Carpenter", "Emergency", "image64.jpg", "video64.mp4"));
//        productList.add(new Product(65, "Home Theater Wiring", "2800", "This service offers home theater wiring and setup in Lahore", "Lahore", "Electrician", "Appointment", "image65.jpg", "video65.mp4"));
//        productList.add(new Product(66, "Garbage Disposal Installation", "3200", "This service offers garbage disposal installation and repair in Karachi", "Karachi", "Plumber", "Emergency", "image66.jpg", "video66.mp4"));
//        productList.add(new Product(67, "Motorcycle Repair Services", "3700", "This service offers comprehensive motorcycle repair and maintenance in Islamabad", "Islamabad", "Mechanic", "Appointment", "image67.jpg", "video67.mp4"));
//        productList.add(new Product(68, "Trim and Molding Installation", "3900", "This service offers trim and molding installation for interior beautification in Lahore", "Lahore", "Carpenter", "Emergency", "image68.jpg", "video68.mp4"));
//        productList.add(new Product(69, "Solar Panel Installation", "3000", "This service offers solar panel installation and maintenance in Karachi", "Karachi", "Electrician", "Appointment", "image69.jpg", "video69.mp4"));
//        productList.add(new Product(70, "Gutter Repair and Cleaning", "2000", "This service offers gutter repair and cleaning to prevent water damage in Islamabad", "Islamabad", "Plumber", "Emergency", "image70.jpg", "video70.mp4"));
//        productList.add(new Product(71, "Fuel System Service", "3800", "This service offers fuel system cleaning and repair for efficient performance in Lahore", "Lahore", "Mechanic", "Appointment", "image71.jpg", "video71.mp4"));
//        productList.add(new Product(72, "Wooden Fence Installation", "4400", "This service offers wooden fence installation and repair for property privacy in Karachi", "Karachi", "Carpenter", "Emergency", "image72.jpg", "video72.mp4"));
//        productList.add(new Product(73, "EV Charger Installation", "3100", "This service offers electric vehicle charger installation for home use in Islamabad", "Islamabad", "Electrician", "Appointment", "image73.jpg", "video73.mp4"));
//        productList.add(new Product(74, "Tankless Water Heater Installation", "3400", "This service offers tankless water heater installation for efficient hot water supply in Lahore", "Lahore", "Plumber", "Emergency", "image74.jpg", "video74.mp4"));
//        productList.add(new Product(75, "Diagnostic Scanning Service", "3900", "This service offers advanced diagnostic scanning for vehicle troubleshooting in Karachi", "Karachi", "Mechanic", "Appointment", "image75.jpg", "video75.mp4"));
//        productList.add(new Product(76, "Custom Wardrobe Construction", "4500", "This service offers custom wardrobe construction for bedroom organization in Islamabad", "Islamabad", "Carpenter", "Emergency", "image76.jpg", "video76.mp4"));
//        productList.add(new Product(77, "Power Outlet Installation", "2700", "This service offers power outlet installation and repair in Lahore", "Lahore", "Electrician", "Appointment", "image77.jpg", "video77.mp4"));
//        productList.add(new Product(78, "Underfloor Heating Installation", "3500", "This service offers underfloor heating installation for comfortable living spaces in Karachi", "Karachi", "Plumber", "Emergency", "image78.jpg", "video78.mp4"));
//        productList.add(new Product(79, "Transmission Fluid Change", "2000", "This service offers transmission fluid change for smooth vehicle operation in Islamabad", "Islamabad", "Mechanic", "Appointment", "image79.jpg", "video79.mp4"));
//        productList.add(new Product(80, "Staircase Construction and Repair", "4600", "This service offers staircase construction and repair for homes and commercial spaces in Lahore", "Lahore", "Carpenter", "Emergency", "image80.jpg", "video80.mp4"));
//        productList.add(new Product(81, "LED Lighting Upgrade", "2900", "This service offers LED lighting upgrades for energy efficiency in Karachi", "Karachi", "Electrician", "Appointment", "image81.jpg", "video81.mp4"));
//        productList.add(new Product(82, "Backflow Preventer Installation", "3600", "This service offers backflow preventer installation to ensure water safety in Islamabad", "Islamabad", "Plumber", "Emergency", "image82.jpg", "video82.mp4"));
//        productList.add(new Product(83, "Vehicle Tune-Up", "2100", "This service offers comprehensive vehicle tune-ups for optimal performance in Lahore", "Lahore", "Mechanic", "Appointment", "image83.jpg", "video83.mp4"));
//        productList.add(new Product(84, "Custom Wall Units", "4700", "This service offers custom wall unit construction for living and office spaces in Karachi", "Karachi", "Carpenter", "Emergency", "image84.jpg", "video84.mp4"));
//        productList.add(new Product(85, "Smoke Detector Installation", "2800", "This service offers smoke detector installation and testing in Islamabad", "Islamabad", "Electrician", "Appointment", "image85.jpg", "video85.mp4"));
//        productList.add(new Product(86, "Bathtub and Shower Installation", "3700", "This service offers bathtub and shower installation for bathroom upgrades in Lahore", "Lahore", "Plumber", "Emergency", "image86.jpg", "video86.mp4"));
//        productList.add(new Product(87, "Cooling System Service", "2200", "This service offers cooling system service and repair for vehicles in Karachi", "Karachi", "Mechanic", "Appointment", "image87.jpg", "video87.mp4"));




        adapter = new ProductAdapter(SearchMarket.this, productList, this);
        GridLayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        Log.d("Run Check", "fine");


        //Searchview
        androidx.appcompat.widget.SearchView searchView;
        searchView = findViewById(R.id.svSearchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText, serviceType, serviceCategory);
                return true;
            }
        });
    }

    List<Product> filteredList = new ArrayList<>();
    private void filterList(String searchText, String serviceType, String serviceCategory) {
        filteredList.clear();

        //Toast all the parameters received
//        Toast.makeText(this, "searchText: "+searchText, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceType: "+serviceType, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceCategory: "+serviceCategory, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "productList.size(): "+productList.size(), Toast.LENGTH_SHORT).show();

//        int Inquiry = 0;
//        if(serviceType.equals("Inquiry")){
//            Log.d("inquiry", "filterList: serviceType is Inquiry");
//            serviceType="Appointment";
//            Inquiry = 1;
//        }

        for (Product item: productList){
            //Log.d item category and service category

            Log.d("itemCategory", "item.getItemCategory(): "+item.getCategory());
            Log.d("serviceCategory", "serviceCategory.toLowerCase(): "+serviceCategory.toLowerCase());
//            Toast.makeText(this, "item.getItemCategory(): "+item.getItemCategory(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "serviceCategory.toLowerCase(): "+serviceCategory.toLowerCase(), Toast.LENGTH_SHORT).show();
            if( item.getCategory().toLowerCase().contains(serviceCategory.toLowerCase())){
                if(item.getName().toLowerCase().contains(searchText.toLowerCase()) || item.getCategory().toLowerCase().contains(searchText.toLowerCase())){
                    Log.d("filterlist Abdullah", "filterList: "+item.getName());
                    filteredList.add(item);
                }
            }
        }

        if(filteredList.isEmpty()){
            Log.d("filteredList", "filterList: filteredList is empty");
            Toast.makeText(this, "No service found",Toast.LENGTH_SHORT).show();
            adapter.setFilteredList(filteredList);
        }else{
            // Toast.makeText(this, "Service found",Toast.LENGTH_SHORT).show();
            adapter.setFilteredList(filteredList);
            //show filterlist contents
            for (Product item: filteredList){
                Log.d("filterlistMujeeb", "filterList: "+item.getName());
            }
        }
//        if(Inquiry==1){
//            serviceType="Inquiry";
//        }
        adapter.notifyDataSetChanged();
        for(int i=0; i<filteredList.size();i++){
            Log.d("value of i:", String.valueOf(i));
            Log.d("filteredlist content", "product: "+ filteredList.get(i).getName());
        }
        ProductDataHolder.setProductList(filteredList);

        Log.d("SendingIntentDatacheckinside filter", serviceType);

    }

    private void fetchProductsFromDatabase() {
        String serverUrl = getResources().getString(R.string.server_url);
        String allServicesApiUrl = serverUrl + "hazirjanab/product.php";
        String url = allServicesApiUrl; // Replace with your server URL for fetching item data

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the response as a JSONObject
                            JSONObject responseObject = new JSONObject(response);

                            // Check if the status is success
                            if (responseObject.getInt("Status") == 1) {
                                // Get the items array from the response
                                JSONArray itemsArray = responseObject.getJSONArray("Products");

                                // Process each item in the array
                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject itemObject = itemsArray.getJSONObject(i);
                                    int id = itemObject.getInt("id");
                                    String name = itemObject.getString("name");
                                    String description = itemObject.getString("description");
                                    int quantity = itemObject.getInt("quantity");
                                    String category = itemObject.getString("category");
                                    int price = itemObject.getInt("price");
                                    int availabilityInt = itemObject.getInt("availability");
                                    boolean availability = availabilityInt == 1;

                                    Log.d("ProductCheck", "Product : " + id + " " + name + " " + description + " " + quantity + " " + category + " " + price + " " + availability);

                                    // Assuming you have a way to convert your image data to a URL or file path
                                    // String imageUrl = /* Your logic to fetch or construct the image URL */;

                                    // Construct an Product object
                                    Product item = new Product(id, name, description, quantity, category, price, availability);
                                    Log.d("ProductLog", "Product : " + item);

                                    // Add the item to your list
                                    productList.add(item);
                                }

                                // Notify the adapter that the data set has changed to refresh the RecyclerView
                                adapter.notifyDataSetChanged();
                                filterList("", serviceType, serviceCategory);
                            } else {
                                // Handle the failure case here
                                String message = responseObject.getString("Message");
                                Toast.makeText(SearchMarket.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SearchMarket.this, "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(SearchMarket.this, "Failed to fetch items: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(this, ProductDetails.class);
        //Item serviceItem = servicedataholder.findItemByServiceId(bookingdataholder.getNormalBookingInstance().getServiceId()).getItemCategory();
        Log.d("product category", serviceCategory);
        //Log.d("service category",servicedataholder.findItemByServiceId(bookingdataholder.getNormalBookingInstance().getServiceId()).getItemCategory() );

        if(bookingdataholder.isBookingInstanceValid() && !servicedataholder.findItemByServiceId(bookingdataholder.getNormalBookingInstance().getServiceId()).getItemCategory().equals(serviceCategory)){
            Toast.makeText(SearchMarket.this, "Please select the product of same service category ("+servicedataholder.findItemByServiceId(bookingdataholder.getNormalBookingInstance().getServiceId()).getItemCategory()+")", Toast.LENGTH_SHORT).show();
            //Integer id=bookingdataholder.getNormalBookingInstance().getServiceId();





        } else if (!bookingdataholder.isBookingInstanceValid()) {
            Toast.makeText(SearchMarket.this, "Please select a service first", Toast.LENGTH_SHORT).show();


        } else if (bookingdataholder.isBookingInstanceValid() && servicedataholder.findItemByServiceId(bookingdataholder.getNormalBookingInstance().getServiceId()).getItemCategory().equals(serviceCategory)) {
            Product clickedProduct;
            if (filteredList.isEmpty()) {
                Log.d("its empty", "onProductClicked: " + productList.get(position).getName());
                clickedProduct = productList.get(position);
                intent.putExtra("productId", productList.get(position).getId());
                intent.putExtra("productName", productList.get(position).getName());
                intent.putExtra("productPrice", productList.get(position).getPrice());
                intent.putExtra("productDescription", productList.get(position).getDescription());
                intent.putExtra("ProductQuantity", productList.get(position).getQuantity());
                intent.putExtra("ProductCategory", productList.get(position).getCategory());
                intent.putExtra("ProductAvailability", productList.get(position).isAvailability());


            } else {
                Log.d("its not empty", "onProductClicked: " + filteredList.get(position).getName());
                clickedProduct = filteredList.get(position);
                intent.putExtra("productId", filteredList.get(position).getId());
                intent.putExtra("productName", filteredList.get(position).getName());
                intent.putExtra("productPrice", filteredList.get(position).getPrice());
                intent.putExtra("productDescription", filteredList.get(position).getDescription());
                intent.putExtra("ProductQuantity", filteredList.get(position).getQuantity());
                intent.putExtra("ProductCategory", filteredList.get(position).getCategory());
                intent.putExtra("ProductAvailability", filteredList.get(position).isAvailability());
            }

            startActivity(intent);
        }


    }
}