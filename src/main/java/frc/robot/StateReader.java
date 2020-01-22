package frc.robot;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StateReader {

	public static List<State> read(String fileName) throws Exception {
		System.out.println(fileName);

		Type type = new TypeToken<List<State>>() {
		}.getType();

		FileReader fileReader = new FileReader(fileName);
		Gson gson = new Gson();

		List<State> states = gson.fromJson(fileReader, type);

		return states;
	}
}
