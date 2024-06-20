package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      for (int i = 1; i < 5; i++) {
         User user_i = new User("User" + i, "Lastname" + i, "user" + i + "@mail.ru");
         user_i.setCar(new Car("BMW X", i));
         userService.add(user_i);
      }
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = "+user.getCar());
         System.out.println();
      }
      String testModel = "BMW X";
      int testSeries = 2;
      Optional<User> userOpt = userService.getUserByCarModelAndSeries(testModel, testSeries);
      if (userOpt.isPresent()) {
         System.out.println(userOpt.get().getEmail() + " car: " + userOpt.get().getCar());
      } else {
         System.out.println("User owned car with model: " + testModel + " and series: " + testSeries + " doesn't exist");
      }

      context.close();
   }
}
