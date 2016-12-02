package GSONHome;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;


public class MainGSONHome {
   public static void main(String[] args) throws Exception {


        String result = performRequest();

        Gson gson = new GsonBuilder().create();

       Person myperson = (Person) gson.fromJson(result, Person.class);
        System.out.println("Person: \n\t" + myperson.name+" , "+myperson.surname);
        for(String phone : myperson.phones){
            System.out.println("Phones: \n\t" + phone);
        }
        for(String site : myperson.sites){
            System.out.println("Sites: \n\t" + site);
        }
        System.out.println("Address: \n\t" + myperson.address.country+" , "+myperson.address.city+ myperson.address.street);
    }


    private static String performRequest() throws IOException {
        StringBuilder sb = new StringBuilder();

        try ( BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("c:\\json.txt"))))){

            char[] buf = new char[1000000];

            int r = 0;
            do {
                if ((r = br.read(buf)) > 0)
                    sb.append(new String(buf, 0, r));
            } while (r > 0);
        }

        return sb.toString();
    }
}