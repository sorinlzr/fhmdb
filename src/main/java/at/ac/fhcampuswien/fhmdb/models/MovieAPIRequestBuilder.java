package at.ac.fhcampuswien.fhmdb.models;

public class MovieAPIRequestBuilder {
    private final StringBuilder builder;
    private boolean isFirstQueryParameter = true;

    public MovieAPIRequestBuilder(String baseUrl) {
        builder = new StringBuilder(baseUrl);
    }

    public MovieAPIRequestBuilder query(String query) {
        appendQueryParam(RequestParameter.QUERY, query);
        return this;
    }

    public MovieAPIRequestBuilder genre(String genre) {
        appendQueryParam(RequestParameter.GENRE, genre);
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String releaseYear) {
        appendQueryParam(RequestParameter.RELEASE_YEAR, releaseYear);
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(String rating) {
        appendQueryParam(RequestParameter.RATING_FROM, rating);
        return this;
    }

    private void appendQueryParam(RequestParameter param, String value) {
        if (value != null && !value.isEmpty()) {
            if (isFirstQueryParameter) {
                builder.append("?");
                isFirstQueryParameter = false;
            } else {
                builder.append("&");
            }

            builder.append(param.name);
            builder.append("=");
            builder.append(value);
        }
    }

    public String build() {
        return builder.toString();
    }

    private enum RequestParameter {
        QUERY("query"),
        GENRE("genre"),
        RELEASE_YEAR("releaseYear"),
        RATING_FROM("ratingFrom");

        private final String name;

        RequestParameter(String name) {
            this.name = name;
        }
    }
}