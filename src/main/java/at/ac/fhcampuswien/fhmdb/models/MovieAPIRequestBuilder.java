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

    public MovieAPIRequestBuilder query(String query) {
        this.query = query;
        return this;
    }

    public MovieAPIRequestBuilder genre(String genre) {
        this.genre = genre;
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(String ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }

    public String build() {
        String regex = "/\\d+$";
        boolean isSpecificMovieRequested = base.matches(regex);
        if (isSpecificMovieRequested) {
            return base;
        } else {
            StringBuilder url = new StringBuilder(base);
            boolean isFirstQueryParameter = true;
            for (Field field : this.getClass().getDeclaredFields()) {
                String fieldName = field.getName();
                if (!fieldName.equals("base")) {
                    field.setAccessible(true);

                    String value = null;
                    try {
                        value = (String) field.get(this);
                    } catch (IllegalAccessException e) {
                        throw new UnsupportedOperationException(e);
                    }

                    if (value != null && !value.isEmpty()) {
                        if (isFirstQueryParameter) {
                            url.append("?");
                            isFirstQueryParameter = false;
                        } else {
                            url.append("&");
                        }

                        url.append(fieldName);
                        url.append("=");
                        url.append(value);
                    }
                }
            }

            return url.toString();
        }
    }
}
