import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.application.Application;
import java.io.*;
import java.util.*;
import java.text.*;

public class ShopPane extends VBox
{
   Button plus = new Button("+");;
   Button minus = new Button("-");
   Label amt = new Label("0");
   Label title = new Label("");
   Label costLabel = new Label("");

   Player.WeaponType panelType = null;

   //player object
   Player player = Player.getInstance();

   //cost of the object
   int cost = 0;

   int intAmt=0;

   //setup the shop
   public ShopPane(String name, int cost)
   {
      title.setText(name);
      costLabel.setText(""+cost);
      amt.setText(""+0);
      this.cost = cost;
      
      HBox row = new HBox();
      
      getChildren().add(title);
      getChildren().add(costLabel);
      
      row.getChildren().add(minus);
      row.getChildren().add(amt);
      row.getChildren().add(plus);
      
      getChildren().add(row);
      
      minus.setOnAction(new ButtonListener());  
      plus.setOnAction(new ButtonListener());
      checkType();
      
      setAlignment(Pos.TOP_CENTER);
   }
   public void checkType()
   {
      if(cost == 100)
      {
         panelType = Player.WeaponType.SPECK;
      } else if (cost == 1000)
      {
         panelType = Player.WeaponType.SIDES;
      } else if (cost == 10000)
      {
         panelType = Player.WeaponType.AUTOTRACK;
      }
   }
   
   //handlder for the buttons
   public class ButtonListener implements EventHandler<ActionEvent>
   {
      public void handle(ActionEvent e)      
      {
         if(e.getSource() == plus && player.getMoney() >= cost)
         {
            //ensures the player has money
            intAmt++;
            if(intAmt > 0)
            {
               player.setWeapon(panelType, true);
            }
            //updates the player's money field
            player.setMoney(player.getMoney()-cost);
            amt.setText(""+intAmt);
         }
         if(e.getSource() == minus && intAmt > 0)
         {
            intAmt--;
            if(intAmt <= 0)
            {
               player.setWeapon(panelType, false);
            }
            amt.setText(""+intAmt);
            //updates the player's money field, sells ofr 50% of cost
            player.setMoney(player.getMoney() + (cost/2));
         }
         
      }
   }
   
}