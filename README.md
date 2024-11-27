# A3-DiceGames

## Student Details
| Name | Roll Number | Email ID |
|----------|----------|----------|
| Vageesha Gupta | 2022A7PS1107G | f20221107@goa.bits-pilani.ac.in |
| Priya Rathi | 2022A7PS1096G | f20221096@goa.bits-pilani.ac.in |

## Description of the App

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

## Implementation

## UI Implementation

- Designed a **simple and intuitive interface** using **ConstraintLayout**, ensuring a responsive and flexible layout.
- Displayed the **wallet balance** prominently on the main screen, ensuring users can easily track their balance.
- Added **input validation** to prevent negative balances, ensuring users cannot spend more coins than available in their wallet.

## Data Persistence

- Used **SharedPreferences** to persist the user's wallet balance across app sessions, ensuring the balance is retained even when the app is restarted.

## Transaction Logic

- Created methods to manage **adding and spending coins** based on user input and game actions.
- Implemented **validation checks** before each transaction to ensure that users cannot spend more coins than they have in their wallet.
- Developed **helper methods** to streamline **balance updates**, ensuring accuracy and consistency after each transaction.

## Error Handling

- Added proper **input validation** to ensure that users only provide valid data (e.g., preventing negative or invalid numbers).
- Implemented **error messages** to notify users about invalid transactions or actions (e.g., insufficient balance).
- Created **safeguards** against **negative balance scenarios**, ensuring that the wallet balance never drops below zero.

## Testing Implementation

- Developed **unit tests** for core functionality, including balance updates, transaction handling, and input validation.
- Added **UI tests using Espresso** to simulate user interactions and ensure the correct display and functionality of UI elements (e.g., balance display, buttons).
- Implemented **SharedPreferences testing** to verify that data is correctly saved and retrieved across app sessions, ensuring persistent wallet balance even after restarting the app.


## Testing

A **Test-Driven Development (TDD)** approach was  followed from the beginning. Here are some key testing steps:

### Instrumented Tests

Instrumented tests run on an Android device or emulator to verify UI components and user interactions. These tests include:

- **Die Roll Test**: Verifies that the "Roll Die" button is clickable and functional.
- **Navigation Test**: Ensures smooth navigation from the main screen to the game screen.
- **Game Type Selection Test**: Validates that users can select game types such as **Two Alike**, **Three Alike**, and **Four Alike**.
- **Wager Input Test**: Confirms that wager input is validated and correctly processed.
- **Dice Display Test**: Ensures that dice are displayed properly after a valid wager.
- **Balance Update Test**: Validates that the balance is updated correctly after a game session.

### Unit Tests

Unit tests verify the core game logic, such as balance management, dice outcomes, and wager validation. These tests include:

- **Game Play Logic**: Validates the rules of each game type (e.g., **Two Alike**, **Three Alike**, **Four Alike**).
- **Balance Management**: Verifies the balance updates correctly after each game round.
- **Wager Validation**: Ensures that wagers are only accepted if the player has enough balance.
- **Game Type Validation**: Tests each game type's logic, ensuring the proper conditions for winning and losing.

## Test Coverage

The following functionalities are covered by tests:

- **User Interface (UI)**: Buttons, forms, and screens interactions are tested using Espresso.
- **Core Game Logic**: Winning/losing scenarios, dice roll outcomes, balance management, and wager validation are tested using JUnit and Robolectric.


No crashes have been detected in the app, and the logic has been validated with different scenarios.

Additionally, **ChatGPT** was used for guidance with coding challenges and understanding difficult concepts. Relevant code snippets were taken from Android documentation and slides provided in the course. 

## Time Taken

It took approximately **40 hours** to complete the assignment, which includes coding, testing, debugging, and ensuring accessibility compliance.


## Pair Programming

**Extent of Pair Programming Used**: **5/5** 


## Difficulty Rating

**Assignment Difficulty**: **7/10** 

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/h3RlyAsJ)
