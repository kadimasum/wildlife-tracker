import modules.EndangeredAnimal;
import modules.Sightings;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

//        String connectionString = "jdbc:postgresql://localhost:5432/animal_tracker";
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "sirkadima", "kadima123");

        staticFileLocation("/public");

        get("/", (request, response) ->{
            return new ModelAndView(new HashMap(), "layout.hbs");
        }, new HandlebarsTemplateEngine());

        get("/home.hbs", (request, response) ->{
            return new ModelAndView(new HashMap(), "home.hbs");
        }, new HandlebarsTemplateEngine());

        get("/trackedAnimals.hbs", (request, response) ->{
            return new ModelAndView(new HashMap(), "trackedAnimals.hbs");
        }, new HandlebarsTemplateEngine());

        get("/createNewAnimalTrackRecord.hbs", (request, response) ->{
            return new ModelAndView(new HashMap(), "createNewAnimalTrackRecord.hbs");
        }, new HandlebarsTemplateEngine());

        get("/home.hbs", (request, response) ->{
            return new ModelAndView(new HashMap(), "home.hbs");
        }, new HandlebarsTemplateEngine());

        post("/trackedAnimals",(request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String  animalName = request.queryParams("animalName");
            String  location = request.queryParams("location");
            String  health = request.queryParams("health");
            String  rangerName = request.queryParams("rangerName");
            String  age = request.queryParams("age");
            EndangeredAnimal endangeredAnimal = new EndangeredAnimal(animalName, health, age, location, rangerName );

            endangeredAnimal.save();
            List<EndangeredAnimal> animalObj = EndangeredAnimal.getAll();

            model.put("animal", animalObj);

            return new ModelAndView(model, "/trackedAnimals.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sightings.hbs", (request, response) ->{
            return new ModelAndView(new HashMap(), "sightings.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sightings",(request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String  sighting = request.queryParams("sightings");

            Sightings site = new Sightings(sighting);
            site.save();
            List<Sightings> siteObj = Sightings.getAll();

            model.put("sighting", siteObj);

            return new ModelAndView(model, "/sightings.hbs");
        }, new HandlebarsTemplateEngine());



    }
}
