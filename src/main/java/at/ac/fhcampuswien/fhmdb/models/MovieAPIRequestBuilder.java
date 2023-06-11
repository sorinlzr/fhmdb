package at.ac.fhcampuswien.fhmdb.models;

import java.lang.reflect.Field;

public class MovieAPIRequestBuilder {

    private String base;

    private String query;

    private String genre;

    private String releaseYear;

    private String ratingFrom;

    public MovieAPIRequestBuilder(String base) {
        this.base = base;
    }

    public String build() throws IllegalAccessException {
        String regex = "/\\d+$";
        boolean isSpecificMovieRequested = base.matches(regex);
        if (isSpecificMovieRequested) {
            return base;
        } else {
            String url = base;
            boolean isFirstQueryParameter = true;
            for (Field field : getQueryParameters()) {
                String fieldName = field.getName();
                field.setAccessible(true);
                String value = (String) field.get(this);
                if (value == null || value.isEmpty()) {
                    if (isFirstQueryParameter) {
                        url += "?";
                        isFirstQueryParameter = false;
                    } else {
                        url += "&";
                    }

                    url += fieldName;
                    url += "=";
                    url += value;
                }
            }

            return url;
        }
    }

    private Field[] getQueryParameters() {
        Field[] fields = this.getClass().getDeclaredFields();
        Field[] queryParameters = new Field[fields.length];

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals("base")) {
                continue;
            }
            queryParameters[i] = fields[i];
        }

        return queryParameters;
    }
}
