package com.example.bechdaal;

import com.example.bechdaal.ui.categories.SingleCheckGenre;

import java.util.Arrays;
import java.util.List;

public class GenreDataFactory {

  public static List<SingleCheckGenre> makeSingleCheckGenres() {
    return Arrays.asList(makeSingleCheckCarGenre(),
            makeSingleCheckBikeGenre(),
            makeSingleCheckMobileGenre(),
            makeSingleCheckLaptopGenre(),
            makeSingleCheckPropertyGenre(),
            makeSingleCheckFurnitureGenre());
  }


  public static SingleCheckGenre makeSingleCheckCarGenre() {
    return new SingleCheckGenre("Car", makeCarArtists(), R.drawable.cat1);
  }

  public static SingleCheckGenre makeSingleCheckBikeGenre() {
    return new SingleCheckGenre("Bike", makeBikeArtists(), R.drawable.cat2);
  }

  public static SingleCheckGenre makeSingleCheckMobileGenre() {
    return new SingleCheckGenre("Mobile", makeMobileArtists(), R.drawable.cat3);
  }

  public static SingleCheckGenre makeSingleCheckLaptopGenre() {
    return new SingleCheckGenre("Laptop", makeLaptopArtists(), R.drawable.cat4);
  }

  public static SingleCheckGenre makeSingleCheckPropertyGenre() {
    return new SingleCheckGenre("Property", makePropertyArtists(), R.drawable.cat5);
  }

  public static  SingleCheckGenre makeSingleCheckFurnitureGenre() {
    return new SingleCheckGenre("Furniture",makeFurnitureArtists(),R.drawable.cat6);
  }


  public static List<Artist> makeCarArtists() {
    Artist cars = new Artist("Car > Cars", false);
    Artist hardware = new Artist("Car > Hardware & tools",false);
    Artist viewAll = new Artist("Car > View All", false);

    return Arrays.asList(cars,hardware,viewAll);
  }

  public static List<Artist> makeBikeArtists() {
    Artist withGear = new Artist("Bike > With Gear", true);
    Artist withoutGear = new Artist("Bike > W/O Gear", true);
    Artist cycles = new Artist("Bike > Cycles", false);
    Artist hardware = new Artist("Bike > Hardware & tools",false);
    Artist viewAll = new Artist("Bike > View All", false);

    return Arrays.asList(withGear, withoutGear, cycles,hardware,viewAll);
  }

  public static List<Artist> makeMobileArtists() {
    Artist phones = new Artist("Mobile > Mobile Phones", false);
    Artist accessories = new Artist("Mobile > Accessories", true);
    Artist tablets = new Artist("Mobile > Tablets", false);
    Artist viewAll = new Artist("Mobile > View All", false);

    return Arrays.asList(phones, accessories, tablets, viewAll);
  }

  public static List<Artist> makeLaptopArtists() {
    Artist computers = new Artist("Laptop > Computers", true);
    Artist laptops = new Artist("Laptop > Laptops", false);
    Artist hardDisks = new Artist("Laptop > Hard Disks and Printers", false);
    Artist assesories = new Artist("Laptop > Computer Assesories", false);
    Artist viewAll = new Artist("Laptop > View All", false);

    return Arrays.asList(computers, laptops, hardDisks, assesories,viewAll);
  }

  public static List<Artist> makePropertyArtists() {
    Artist houses = new Artist("Property > Houses & Appartments", false);
    Artist lands = new Artist("Property > Lands & Plots", false);
    Artist shops = new Artist("Property > Shops & Offices", true);
    Artist pg = new Artist("Property > PG & Guest Houses", false);
    Artist viewAll = new Artist("Property > View All", false);

    return Arrays.asList(houses, lands, shops, pg,viewAll);
  }

  public static List<Artist> makeFurnitureArtists() {
    Artist sofa = new Artist("Furniture > Sofa & Dinning", false);
    Artist bed = new Artist("Furniture > Beds & Wardrobes", false);
    Artist decor = new Artist("Furniture > Home Decor & Garden", true);
    Artist kidsFurniture = new Artist("Furniture > Kids Furniture", false);
    Artist viewAll = new Artist("Furniture > View All", false);

    return Arrays.asList(sofa, bed, decor, kidsFurniture,viewAll);
  }

}

