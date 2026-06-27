package com.app.photoapp.utils;

import android.content.Context;
import com.app.photoapp.data.model.TemplateModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateLoader {

    public static List<TemplateModel> loadTemplates(Context context) {
        List<TemplateModel> result = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("templates.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            JsonObject root = gson.fromJson(json, JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                JsonArray array = entry.getValue().getAsJsonArray();
                for (JsonElement el : array) {
                    TemplateModel model = gson.fromJson(el, TemplateModel.class);
                    result.add(model);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
