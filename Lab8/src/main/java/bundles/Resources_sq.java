package bundles;

import java.util.ListResourceBundle;

public class Resources_sq extends ListResourceBundle {
    private static final Object[][] contents = {
            {"enter","Identifikohu"},
            {"registration","Regjistrimi"},
            {"exit","Dalja"},
            {"flats","Banesa"},
            {"logging","Identifikimi"},
            {"login","Login"},
            {"password","Fjalëkalimi"},
            {"back","Kthehu"},
            {"register","Regjistrohu"},
            {"settings","Cilësimet"},
            {"language","Gjuha"},
            {"change_user","Ndrysho përdoruesin"},
            {"help","Ndihmë për komandën"},
            {"info","Informacioni i koleksionit"},
            {"show","Shfaq të gjithë koleksionin"},
            {"add","Shto apartament"},
            {"clear","Pastro koleksionin"},
            {"remove_last","Hiqe të fundit"},
            {"average","Mesatarja \"hapësira e jetesës\""},
            {"max","Max sipas vitit të ndërtimit"},
            {"update","Azhurnimi i banesës"},
            {"remove_by_id","Hiq nga ID"},
            {"remove_at","Hiq nga indeksi"},
            {"execute_script","Ekzekuto skriptin"},
            {"filter","Filtro sipas pamjes"},
            {"list","Lista e apartamenteve"},
            {"visualization","Vizualizimi"},
            {"name","Emri i pronarit"},
            {"coordinates","Koordinatat"},
            {"area","Area"},
            {"number_of_rooms","Numri i dhomave"},
            {"living_space","Hapësira e jetesës"},
            {"flat","Apartament"},
            {"view","Pamje nga dritarja"},
            {"transport","Transport"},
            {"house_name","Emri i shtëpisë"},
            {"house_year","Viti kur u ndërtua shtëpia"},
            {"number_of_flats","Numri i banesave për kat"},
            {"type","Enter"},
            {"nameErr","Emri duhet të jetë një varg jo bosh"},
            {"xErr","Koordinata X duhet të jetë një numër"},
            {"yErr","Koordinata Y duhet të jetë një numër deri në 368"},
            {"areaErr","Hapësira e jetesës duhet të jetë një numër pozitiv"},
            {"numberErr","Numri i dhomave duhet të jetë një numër i plotë pozitiv"},
            {"livingErr","Zona e dhomës së ndenjes duhet të jetë një numër pozitiv"},
            {"hnameErr","Emri duhet të jetë një varg jo bosh"},
            {"hyearErr","Viti i ndërtimit të shtëpisë duhet të jetë një numër i plotë pozitiv"},
            {"hnumberErr","Numri i apartamenteve për kat duhet të jetë një numër i plotë pozitiv"},
            {"idErr","Gabim! 'id' duhet të jetë një numër i plotë pozitiv. \n Ju lutemi futni sërish komandën."},
            {"indexErr","Gabim! Indeksi duhet të jetë një numër i plotë jo negativ. \n Ju lutemi futni përsëri komandën."},
            {"scriptErr","Shtegu duhet të jetë një varg jo bosh"},
            {"viewErr","Gabim. Keni futur një vlerë të pavlefshme për \"pamje\".\n"},
            {"showButton","Drumdram"},
            {"creationDate","Lumdon\ndat"},
            {"id","ID"},
            {"user","User"},
            {"house", "Informacioni I Shtëpisë"}



    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
