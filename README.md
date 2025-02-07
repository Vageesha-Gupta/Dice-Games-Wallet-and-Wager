# DiceGames with wager

## Description 

**Dice Games** is an interactive mobile application that simulates a fun and engaging dice game, where players can roll dice, place wagers, and manage their virtual currency through a dynamic wallet system. The app provides a variety of game modes, each offering unique gameplay rules and opportunities for players to earn or lose virtual currency based on their dice rolls.

The app’s key functionalities are as follows:

### 1. **Wallet Fragment**
   Players can roll a six-sided die with a simple tap on the "Roll" button. Each roll generates a random result, which is then used to determine the outcome based on the selected game mode. A **six** is a key value in the game, granting players rewards, while consecutive **six** rolls increments their wallet balance by 10.

### 2. **Games Fragment**
   The app offers multiple **game modes** that players can choose from to tailor their gameplay experience. Each game mode has its own rules for how wagers are placed and how balance is affected. For example:
   - **Two Alike**: Players win or lose based on whether two dice rolls match. Rolling a pair of identical numbers (e.g., two fives) grants a reward, while different numbers lead to a loss.
   - **Three Alike**: This mode rewards players for rolling three identical numbers, and penalizes for any mismatch.
   - **Four Alike**: Similar to the other modes, but with a more complex rule set that rewards players for rolling four identical numbers in a single roll.

   Each mode has different odds, payouts, and rules, giving players a variety of ways to strategize and interact with the game.

### 3. **Wagering and Balance Updates**
   Before each roll, players can choose to place a wager from their available balance. This wager determines how much of their balance will be risked on the outcome of the roll. After each round, the app updates the player's balance based on the wager and the game mode:
   - Winning a round results in a balance increase according to the rules of the selected game mode.
   - Losing a round results in a decrease in the balance by the amount of the wager.
   
   Players can continue to play and increase their balance or try to recover from losses.
