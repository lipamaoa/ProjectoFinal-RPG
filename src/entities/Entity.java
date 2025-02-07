package src.entities;

/**
 * Abstract class representing an entity in the game.
 * It serves as a base for both the player and NPCs.
 */

public abstract class Entity{
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int strength;

    public Entity(String name, int maxHp, int strength) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.strength = strength;
    }

    /**
     * Displays the entity's details
    */
    public void showDetails(){
        System.out.println("Name: " + name);
        System.out.println("HP: " + currentHp + "/" + maxHp);
        System.out.println("Strength: " + strength);
    }


    /**
     * Applies damage to the entity
     * Ensures that HP does not drop below 0.
     */


     public void takeDamage(int amount){
        this.currentHp-=amount;

        if(this.currentHp<0){
            this.currentHp=0;
        }
        System.out.println(name + " took " + amount + " damage. Remaining HP: " + currentHp);
     }

     /**
      * Heals the entity for a specified amount.
      Ensures that HP does not exceed maxHp
      */

      public void heal(int amount){
        this.currentHp+=amount;
        if(this.currentHp>maxHp){
            this.currentHp=maxHp;
        }
        System.out.println(name + " healed " + amount + " HP. Current HP: " + currentHp);
      }

      /**
       * Getters
       */

       public String getName(){
        return name;
       }

       public int getCurrentHp(){
        return currentHp;
       }


       public int getMaxHp(){
        return maxHp;
       }

       public int getStrength(){
        return strength;
       }



       /**
        * Setters
        */

        public void setCurrentHp(int hp){
            this.currentHp=Math.max(0, Math.min(hp, maxHp)); //Ensures HP is within valid range
        }

        public void setStrength(int strength){
            this.strength=strength;

        }


    /**
     * Abstract method for attack behavior.
     * Must be implemented by subclasses (Hero and NPC).
     */

    public abstract void attack(Entity target);


   
}
