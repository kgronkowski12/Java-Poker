javac -encoding UTF-8 poker/Main.java poker/players/Player.java poker/players/HumanControlled.java poker/shoe/Card.java poker/shoe/Deck.java poker/players/AI.java poker/table/Table.java poker/table/HandEvaluator.java

jar cfe Poker.jar poker.Main poker/Main.class poker/players/HumanControlled.class poker/shoe/Card.class poker/shoe/Deck.class poker/shoe/Color.class poker/shoe/Suit.class poker/players/Player.class poker/players/AI.class poker/table/Table.class poker/table/HandEvaluator.class

java -jar Poker.jar
