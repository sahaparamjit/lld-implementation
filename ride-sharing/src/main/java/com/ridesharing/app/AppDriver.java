package com.ridesharing.app;

import com.ridesharing.app.dao.RideDao;
import com.ridesharing.app.dao.UserDao;
import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.impl.*;
import com.ridesharing.app.service.MatchService;
import com.ridesharing.app.service.RideManager;
import com.ridesharing.app.service.RideService;
import com.ridesharing.app.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class AppDriver {

  public static void main(String[] args) {//        Sample code to read from file passed as command line argument
    try {
      // the file to be opened for reading
      File file = new File("./sample_input/input1.txt");
//      FileInputStream fis = new FileInputStream(file);
      FileInputStream fis = new FileInputStream(args[0]);
      Scanner sc = new Scanner(fis); // file to be scanned
      // returns true if there is another line to read
      UserDao userDao = new UserDaoImpl();
      RideDao rideDao = new RideDaoImpl();
      UserService userService = new UserServiceImpl(userDao);
      MatchService matchService = new MatchServiceImpl(userDao);
      RideService rideService = new RideServiceImpl(rideDao, userDao);
      RideManager rideManager = new RideManagerImpl(rideService, matchService);
      while (sc.hasNextLine()) {
        //Add your code here to process input commands
        processCommand(sc.nextLine(), rideManager, userService);
      }
      sc.close(); // closes the scanner
    } catch (IOException e) {
    }
  }

  private static void processCommand(String command, RideManager rideManager, UserService userService) {
    String[] parts = command.split(" ");
    switch (parts[0]) {
      case "ADD_DRIVER":
        userService.addDriver(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        break;
      case "ADD_RIDER":
        userService.addRider(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        break;
      case "MATCH":
        rideManager.matchRider(parts[1]);
        break;
      case "START_RIDE":
        rideManager.startRide(parts[1], parts[3], Integer.parseInt(parts[2]));
        break;
      case "STOP_RIDE":
        rideManager.stopRide(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        break;
      case "BILL":
        rideManager.getBill(parts[1]);
        break;
      case "NEW":
        System.out.println("-----------NEW TEST CASE-----------");
        DataStore.flush();
        break;
      default:
        System.out.println("INVALID_COMMAND");
    }
  }
}
