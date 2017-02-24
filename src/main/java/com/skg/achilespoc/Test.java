package com.skg.achilespoc;

import com.datastax.driver.core.Cluster;
import com.skg.achilespoc.entity.Biography;
import com.skg.achilespoc.entity.User;
import info.archinnov.achilles.generated.ManagerFactory;
import info.archinnov.achilles.generated.ManagerFactoryBuilder;
import info.archinnov.achilles.generated.manager.User_Manager;
import info.archinnov.achilles.schema.SchemaGenerator;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by satish on 2/22/17.
 */
public class Test {
    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("localhost").withPort(9042).build();
        ManagerFactory managerFactory = ManagerFactoryBuilder
                .builder(cluster)
                .withDefaultKeyspaceName("testkeyspace")
                .doForceSchemaCreation(true)
                .build();

        // Favorite Tags
        List<String> tags = new ArrayList<>();
        tags.add("computing");
        tags.add("java");
        tags.add("cassandra");

        // Preferences
        Map<Integer,String> preferences = new HashMap<>();
        preferences.put(1,"FR");
        preferences.put(2,"French");
        preferences.put(3,"Paris");

        //
        User_Manager userManager = managerFactory.forUser();
        Biography bio = new Biography();
        bio.setBirthPlace("VietNam");
        bio.setDiplomas(Arrays.asList("Master of Science","Diplome d'ingenieur"));
        bio.setDescription("Yet another framework developer");

        Long user1Id = new Random().nextLong();
        Long user2Id = new Random().nextLong();
        Long user3Id = new Random().nextLong();
        User user1 = createUser(userManager,user1Id,"satish" + Math.random(),"gupta", 100, preferences, tags, bio);
        User user2 = createUser(userManager,user2Id,"manish" + Math.random(),"gupta", 100, preferences, tags, bio);
        User user3 = createUser(userManager,user3Id,"ranish" + Math.random(),"gupta", 100, preferences, tags, bio);

        createFromJson(userManager, "{\"id\": 10, \"firstname\": \"John\", \"lastname\": \"DOE\", \"age\":\"12\"}");
        // Find user by id using the CRUD API
        User foundUser1 = userManager
                .crud()
                .findById(user1Id).get();

        System.out.println("Found user with id 1: " + foundUser1);

        // Now add anew favorite tag to this user by using the DSL API
        user1.setAge(user1.getAge() + 1);
        userManager
                .crud()
                .update(user1)
                .execute();
        userManager
                .dsl()
                .update()
                .fromBaseTable()
                .favoriteTags().AppendTo("achilles" + user1.getAge())
                .where()
                .id().Eq(user1Id)
                .execute();

        System.out.println("Updated user with id " + user1Id + userManager
                .crud()
                .findById(user1Id).get());


        //Delete
        /*userManager
                .dsl().delete()
                .allColumns_FromBaseTable()
                .where().id().Eq(user2Id)
                .execute();*/
        /*userManager
                .crud()
                .deleteById(user3Id)
                .execute();*/
    }

    public static User createUser(User_Manager userManager, Long id,String firstName, String lastName, Integer age, Map<Integer,String> preferences, List<String> favoriteTags,Biography bio) {
        User user = new User();
        user.setId(id);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setAge(age);
        user.setFavoriteTags(favoriteTags);
        user.setPreferences(preferences);
        user.setBio(bio);

        userManager.crud().insert(user).execute();
        return user;
    }

    public static void createFromJson(User_Manager userManager,String jsonString) {
        userManager
                .crud()
                .insertJSON(jsonString)
                .execute();
    }

    public static void generteSchemaScript(String keyspaceName,File file) {
        if(file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            SchemaGenerator.builder()
                    .withKeyspace(keyspaceName)
                    .generateCustomTypes(true) //default = true
                    .generateIndices(true) //default = true
                    .generateTo(file);

        } catch (IOException exception) {
            System.out.println("Exception:" + exception);

        }
    }
}
