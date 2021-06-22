
package bundles;

import java.util.ListResourceBundle;

public class Resources_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {
            {"enter","Login"},
            {"registration","Registration"},
            {"exit","Exit"},
            {"flats","Flats"},
            {"logging","Log in"},
            {"login","Login"},
            {"password","Password"},
            {"back","Back"},
            {"register","Register"},
            {"settings","Settings"},
            {"language","Language"},
            {"change_user","Change user"},
            {"help","Command Help"},
            {"info","Collection Information"},
            {"show","Show entire collection"},
            {"add","Add worker"},
            {"clear","Clear collection"},
            {"remove_last","Remove last"},
            {"average","Average of \"living space\""},
            {"max","Max by year of construction"},
            {"update","Update worker"},
            {"remove_by_id","Remove by ID"},
            {"remove_at","Remove at"},
            {"execute_script","Execute script"},
            {"filter","Filter by View"},
            {"list","List of apartments"},
            {"visualization","Visualization"},
            {"name","Name"},
            {"coordinates","Coordinates"},
            {"area","Area"},
            {"number_of_rooms","Number of rooms"},
            {"living_space","Living space"},
            {"flat","Apartment"},
            {"view","Window view"},
            {"transport","Transport"},
            {"house_name","House name"},
            {"house_year","House year"},
            {"number_of_flats","Number of flats on floor"},
            {"type","Enter"},
            {"nameErr","The name must be a non-empty string"},
            {"xErr","The X coordinate must be a number"},
            {"yErr","The Y coordinate must be a number up to 368"},
            {"areaErr","Living space must be a positive number"},
            {"numberErr","The number of rooms must be a positive integer"},
            {"livingErr","Living room area must be a positive number"},
            {"hnameErr","The name must be a non-empty string"},
            {"hyearErr","The year the house was built must be a positive integer"},
            {"hnumberErr","The number of apartments per floor must be a positive integer"},
            {"idErr","Error! 'id' must be a positive integer. \n Please re-enter the command."},
            {"indexErr","Error! The index must be a non-negative integer. \n Please re-enter the command."},
            {"scriptErr","The path must be a non-empty string"},
            {"viewErr","Error. You entered an invalid value for 'view'. \n"},
            {"showButton","Show"},
            {"creationDate","Creation\ndate"},
            {"id","ID"},
            {"user","User"},
            {"house","House information"},
            {"loginTitle", "Try to guess password!"},
            {"removeLower", "Remove lower"},
            {"addifless", "Add if less"},
            {"table", "table"},
            {"visual", "visualization"},
            {"salary", "Salary"},
            {"startAt", "Start at"},
            {"creation", "Creation"},
            {"position", "Position"},
            {"status", "Status"},
            {"height", "Height"},
            {"eye", "Eye"},
            {"hair", "Hair"},
            {"country", "Country"}

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

