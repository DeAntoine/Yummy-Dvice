package yummy_dvice.com;

public class Reqs {

    public static String awsEndPoint = "https://myxzcnelvk.execute-api.eu-west-3.amazonaws.com/api/";

    public static String verifiyUsernamePassword = awsEndPoint + "verifiyUsernamePassword?mdp=?pw&user=?u";

    public static String getRestaurantCuisine = awsEndPoint + "getCategories/";

    public static String getReviewsBusiness = awsEndPoint + "getReviewsFromId/";

    public static String getRestaurantNameAlmost = awsEndPoint + "getRestaurantNameAlmost/";

    public static String getRestaurantNameAlmostCount = awsEndPoint + "getCountRestaurantNameAlmost/";
}
