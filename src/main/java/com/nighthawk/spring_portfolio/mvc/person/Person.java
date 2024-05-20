package com.nighthawk.spring_portfolio.mvc.person;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
Person is an POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Convert(attributeName ="person", converter = JsonType.class)
public class Person {

    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max = 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @ManyToMany(fetch = EAGER)
    private Collection<PersonRole> roles = new ArrayList<>();

    
    private int csaPoints;
    private int cspPoints;
    private int cyberPoints;
    private int profilePicInt;
    private int accountPoints;
    private int accountLevel;
    private int[][] statsArray;
    private List<Integer> inventory;
    private int weaponGearIdEquipped;
    private int armorGearIdEquipped;
    private int accessoryGearIdEquipped;
    private int power;
    private int totalHealth;
    private int totalDamage;
    private boolean finishedTutorial;
    private int gamesPlayed;
    private int keysCollected;
    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "calories": 2200,
            "steps": 8000
        }
    }
    */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>();
    

    // Constructor used when building object from an API
    public Person(String email, String password, String name, int csaPoints, int cspPoints, int cyberPoints, int profilePicInt, int accountPoints, int accountLevel, int[][] statsArray, List<Integer> inventory, int weaponGearIdEquipped, int armorGearIdEquipped, int accessoryGearIdEquipped, int power, int totalHealth, int totalDamage, boolean finishedTutorial, int gamesPlayed, int keysCollected) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.csaPoints = csaPoints;
        this.cspPoints = cspPoints;
        this.cyberPoints = cyberPoints;
        this.profilePicInt = profilePicInt;
        this.accountPoints = accountPoints;
        this.accountLevel = accountLevel;
        this.statsArray = statsArray;
        this.inventory = inventory;
        this.weaponGearIdEquipped = weaponGearIdEquipped;
        this.armorGearIdEquipped = armorGearIdEquipped;
        this.accessoryGearIdEquipped = accessoryGearIdEquipped;
        this.power = power;
        this.totalDamage = totalDamage;
        this.totalHealth = totalHealth;
        this.finishedTutorial = finishedTutorial;
        this.gamesPlayed = gamesPlayed;
        this.keysCollected = keysCollected;
    }

    
    // Initialize static test data
    public static Person[] init() {
        int[][] baseStatsArray = {{100, 0}, {100, 0}};
        ArrayList<Integer> baseInventory = new ArrayList<>();

        // basics of class construction
        Person p1 = new Person();
        p1.setName("Theo Huntalas");
        p1.setEmail("theo@gmail.com");
        p1.setPassword("123Theo!");
        p1.setCsaPoints(0);
        p1.setCspPoints(0);
        p1.setCyberPoints(0);
        p1.setProfilePicInt(0);
        p1.setAccountLevel(1);
        p1.setStatsArray(baseStatsArray);
        p1.setInventory(baseInventory);
        p1.setPower(200);
        p1.setTotalHealth(100);
        p1.setTotalDamage(100);
        p1.setFinishedTutorial(false);
        p1.setGamesPlayed(0);
        p1.setKeysCollected(0);


        Person p2 = new Person();
        p2.setName("Justin Nguyen");
        p2.setEmail("justin@gmail.com");
        p2.setPassword("123Justin!");
        p2.setCsaPoints(0);
        p2.setCspPoints(0);
        p2.setCyberPoints(0);
        p2.setProfilePicInt(0);
        p2.setAccountLevel(1);
        p2.setStatsArray(baseStatsArray);
        p2.setInventory(baseInventory);
        p2.setPower(200);
        p2.setTotalHealth(100);
        p2.setTotalDamage(100);
        p2.setFinishedTutorial(false);
        p2.setGamesPlayed(0);
        p2.setKeysCollected(0);

        Person p3 = new Person();
        p3.setName("Finn Carpenter");
        p3.setEmail("finn@gmail.com");
        p3.setPassword("123Finn!");
        p3.setCsaPoints(0);
        p3.setCspPoints(0);
        p3.setCyberPoints(0);
        p3.setProfilePicInt(0);
        p3.setAccountLevel(1);
        p3.setStatsArray(baseStatsArray);
        p3.setInventory(baseInventory);
        p3.setPower(200);
        p3.setTotalHealth(100);
        p3.setTotalDamage(100);
        p3.setFinishedTutorial(false);
        p3.setGamesPlayed(0);
        p3.setKeysCollected(0);

        Person p4 = new Person();
        p4.setName("Rachit Jaiswal");
        p4.setEmail("rachit@gmail.com");
        p4.setPassword("123Rachit!");
        p4.setCsaPoints(0);
        p4.setCspPoints(0);
        p4.setCyberPoints(0);
        p4.setProfilePicInt(0);
        p4.setAccountLevel(1);
        p4.setStatsArray(baseStatsArray);
        p4.setInventory(baseInventory);
        p4.setPower(200);
        p4.setTotalHealth(100);
        p4.setTotalDamage(100);
        p4.setFinishedTutorial(false);
        p4.setGamesPlayed(0);
        p4.setKeysCollected(0);

        Person p5 = new Person();
        p5.setName("Luna Iwazaki");
        p5.setEmail("luna@gmail.com");
        p5.setPassword("123Luna!");
        p5.setCsaPoints(0);
        p5.setCspPoints(0);
        p5.setCyberPoints(0);
        p5.setProfilePicInt(0);
        p5.setAccountLevel(1);
        p5.setStatsArray(baseStatsArray);
        p5.setInventory(baseInventory);
        p5.setPower(200);
        p5.setTotalHealth(100);
        p5.setTotalDamage(100);
        p5.setFinishedTutorial(false);
        p5.setGamesPlayed(0);
        p5.setKeysCollected(0);


        Person p6 = new Person();
        p6.setName("Tanisha Patil");
        p6.setEmail("tanisha@gmail.com");
        p6.setPassword("123Tanisha!");
        p6.setCsaPoints(0);
        p6.setCspPoints(0);
        p6.setCyberPoints(0);
        p6.setProfilePicInt(0);
        p6.setAccountLevel(1);
        p6.setStatsArray(baseStatsArray);
        p6.setInventory(baseInventory);
        p6.setPower(200);
        p6.setTotalHealth(100);
        p6.setTotalDamage(100);
        p6.setFinishedTutorial(false);
        p6.setGamesPlayed(0);
        p6.setKeysCollected(0);


        Person p7 = new Person();
        p7.setName("TestPlayer");
        p7.setEmail("testPlayer@gmail.com");
        p7.setPassword("123TestPlayer!");
        p7.setCsaPoints(0);
        p7.setCspPoints(0);
        p7.setCyberPoints(0);
        p7.setProfilePicInt(0);
        p7.setAccountLevel(1);
        p7.setStatsArray(baseStatsArray);
        p7.setInventory(baseInventory);
        p7.setPower(200);
        p7.setTotalHealth(100);
        p7.setTotalDamage(100);
        p7.setFinishedTutorial(false);
        p7.setGamesPlayed(0);
        p7.setKeysCollected(0);

        Person p8 = new Person();
        p8.setName("toby");
        p8.setEmail("toby@gmail.com");
        p8.setPassword("123Toby!");
        p8.setCsaPoints(0);
        p8.setCspPoints(0);
        p8.setCyberPoints(0);
        p8.setProfilePicInt(0);
        p8.setAccountLevel(1);
        p8.setStatsArray(baseStatsArray);
        p8.setInventory(baseInventory);
        p8.setPower(200);
        p8.setTotalHealth(100);
        p8.setTotalDamage(100);
        p8.setGamesPlayed(0);
        p8.setKeysCollected(0);

        Person p9 = new Person();
        p9.setName("hop");
        p9.setEmail("hop@gmail.com");
        p9.setPassword("123Hop!");
        p9.setCsaPoints(0);
        p9.setCspPoints(0);
        p9.setCyberPoints(0);
        p9.setProfilePicInt(0);
        p9.setAccountLevel(1);
        p9.setStatsArray(baseStatsArray);
        p9.setInventory(baseInventory);
        p9.setPower(200);
        p9.setTotalHealth(100);
        p9.setTotalDamage(100);
        p9.setGamesPlayed(0);
        p9.setKeysCollected(0);

        // Array definition and data initialization
        Person persons[] = {p1, p2, p3, p4, p5, p6, p7, p8, p9};
        return(persons);
    }

    public static void main(String[] args) {
        // obtain Person from initializer
        Person persons[] = init();

        // iterate using "enhanced for loop"
        for( Person person : persons) {
            System.out.println(person);  // print object
        }
    }

}