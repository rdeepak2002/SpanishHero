/* Deepak Ramalingam
 * SpanishHero.java
 * 04/17/2017
 *
 * Description:
 * 2D side scroller game that will teach different tenses, conjugations, and irregular verbs from Spanish 2.
 * The topics that will be covered are the present tense, preterite tense, imperfect tense, imperfect vs. preterite,
 * present progressive, and future tense. The game supports two resolution sizes for two different screen sizes.
 * The game frame utilizes a card layout to switch between the different JPanels. The game features an enemy and a boss.
 * The game also has three big levels that are fun to play. The game is a medium difficulty game. In order to learn the
 * Spanish topics, the user will be shown a JPanel with a scroll bar and information needed to answer questions. All the
 * blast and enemy data is stored in arrays. Questions for each level are randomly picked from the Questions.dat file and
 * the levels are extracted from the Levels.dat file. The game will run at a smooth 30 fps when playing on the smaller
 * resolution version. The larger resolution version requires a good computer in order to smoothly run the game. I designed
 * all the images except for the character images. This is a good game to play for review and for learning Spanish 2 topics
 * that are essential.
 */

// imports

public class SpanishHeroRunner                              // class header
{
    public static void main(String[] args)                  // main method
    {
        SpanishHero sh = new SpanishHero();                 // create instance of SpanishHero
    }
}

