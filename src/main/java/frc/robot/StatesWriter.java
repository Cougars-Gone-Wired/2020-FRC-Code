package frc.robot;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class StatesWriter {

	public static void writeStates(List<State> states, String fileName) throws Exception {
		Type type = new TypeToken<List<State>>() {
		}.getType();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String gsonString = gson.toJson(states, type);

		PrintWriter pw = new PrintWriter("/home/lvuser/gsonFiles/" + fileName + ".json");

		pw.print(gsonString);

		pw.close();
	}
}
