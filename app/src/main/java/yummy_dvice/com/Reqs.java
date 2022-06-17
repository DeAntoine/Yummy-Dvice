package yummy_dvice.com;

public class Reqs {

    public static String awsEndPoint = "https://myxzcnelvk.execute-api.eu-west-3.amazonaws.com/api/";

    public static String verifiyUsernamePassword = awsEndPoint + "verifyUsernamePassword/";

    public static String getRestaurantCuisine = awsEndPoint + "getCategories/";

    public static String getCloserRestaurant = awsEndPoint + "getCloserRestaurant?long=?lg&lat=?lt";

    public static String getReviewsBusiness = awsEndPoint + "getReviewsFromId/";

    public static String getRestaurantNameAlmost = awsEndPoint + "getRestaurantNameAlmost/";

    public static String getRestaurantNameAlmostCount = awsEndPoint + "getCountRestaurantNameAlmost/";

    public static String addUser = awsEndPoint + "addUser/";

    public static String getCategories = awsEndPoint + "getCategories/";

    public static String getCategoriesOr = awsEndPoint + "getCategoriesOr/";

    public static String getReco = awsEndPoint + "getCategoriesOr/?c";

    public static String addReviews = awsEndPoint + "addReviews/?r";

    public static String getTendance = awsEndPoint + "TendMonth/?d";
}