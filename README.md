# Bayesian Network and Probability Testing Suite

This project contains test suites for Bayesian Networks, probability calculus, and propositional logic implementations.

## Test Categories

### Bayesian Network Tests

- **[TestBN.kt](src/test/kotlin/bayesian/TestBN.kt)**: Tests basic Bayesian Network operations including:
  - Parent/child relationships
  - Descendant relationships
  - Markov assumptions
  - Chain rule applications
  - Independence testing

- **[TestChainRule.kt](src/test/kotlin/bayesian/TestChainRule.kt)**: Tests chain rule implementations using the Sprinkler example network (Figure 4.4)
  - Parameter verification
  - Chain rule probability calculations
  - D-separation testing

- **[TestDsep.kt](src/test/kotlin/bayesian/TestDsep.kt)**: Tests d-separation in various network configurations including:
  - Tutorial rule validations
  - Alarm network d-separation
  - Smoker network d-separation
  - SSOO network d-separation

- **[TestImap.kt](src/test/kotlin/bayesian/TestImap.kt)**: Tests I-map properties and network construction
- **[TestIndependence.kt](src/test/kotlin/bayesian/TestIndependence.kt)**: Tests conditional and marginal independence in networks
- **[TestValves.kt](src/test/kotlin/bayesian/TestValves.kt)**: Tests valve operations in Bayesian networks

### Homework Implementation Tests

#### Bayesian Networks
- **[Bn4_1_Alarm.kt](src/test/kotlin/homework/bayesian_networks/Bn4_1_Alarm.kt)**: Implements the Alarm network example
- **[Bn4_4_Sprinkler.kt](src/test/kotlin/homework/bayesian_networks/Bn4_4_Sprinkler.kt)**: Implements the Sprinkler network example
- **[Bn4_10_Smoker.kt](src/test/kotlin/homework/bayesian_networks/Bn4_10_Smoker.kt)**: Implements the Smoker network example
- **[Bn4_11_SSOOs.kt](src/test/kotlin/homework/bayesian_networks/Bn4_11_SSOOs.kt)**: Implements the SSOO network
- **[Bn4_13_IMAP.kt](src/test/kotlin/homework/bayesian_networks/Bn4_13_IMAP.kt)**: Implements the IMAP example network
- **[Bn4_14_Exercise_1.kt](src/test/kotlin/homework/bayesian_networks/Bn4_14_Exercise_1.kt)**: Implements Exercise 1 network
- **[Bn4_15_DAG.kt](src/test/kotlin/homework/bayesian_networks/Bn4_15_DAG.kt)**: Implements the DAG example

#### Probability Calculus
- **[Chapter3_1.kt](src/test/kotlin/homework/probability_calculus/Chapter3_1.kt)**: Tests basic probability calculations
- **[Chapter3_2.kt](src/test/kotlin/homework/probability_calculus/Chapter3_2.kt)**: Tests probability operations with disjunction
- **[Chapter3_6.kt](src/test/kotlin/homework/probability_calculus/Chapter3_6.kt)**: Tests probability in the two-children problem
- **[Chapter3_8.kt](src/test/kotlin/homework/probability_calculus/Chapter3_8.kt)**: Tests disease testing probability calculations
- **[Chapter3_21.kt](src/test/kotlin/homework/probability_calculus/Chapter3_21.kt)**: Tests probability updates with new evidence

#### Propositional Logic
- **[Chapter2_1.kt](src/test/kotlin/homework/propositional_logic/Chapter2_1.kt)**: Tests consistency of logical formulas
- **[Chapter2_2.kt](src/test/kotlin/homework/propositional_logic/Chapter2_2.kt)**: Tests validity of logical formulas
- **[Chapter2_3.kt](src/test/kotlin/homework/propositional_logic/Chapter2_3.kt)**: Tests equivalence of logical formulas

## Running Tests

All tests can be run using the standard Kotlin test runner. Individual test classes can be run separately to test specific functionality.