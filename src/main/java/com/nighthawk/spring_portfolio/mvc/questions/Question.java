package com.nighthawk.spring_portfolio.mvc.questions;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedNativeQuery(name = "Question.findByCourse", query = "SELECT * FROM ?1", resultClass = Question.class)
public class Question {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Question", nullable = false)
    private String question;

    @Column(name = "Answer 1", nullable = false)
    private String answer1;

    @Column(name = "Answer 2", nullable = false)
    private String answer2;

    @Column(name = "Answer 3", nullable = false)
    private String answer3;

    @Column(name = "Answer 4", nullable = false)
    private String answer4;

    @Column(name = "Hint", nullable = false)
    private String hint;

    @Column(name = "Correct Answer", nullable = false)
    private Integer correctAnswer;

    @Column(name = "Difficulty", nullable = false)
    private Integer difficulty;

    @Column(name = "Unit", nullable = false)
    private String unit;

    @Column(name = "Points", nullable = false)
    private Integer points;

    // Getters and setters
    public Question(String question, String answer1, String answer2, String answer3, String answer4,String hint, Integer correctAnswer, Integer difficulty, String unit, Integer points) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.hint = hint;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.unit = unit;
        this.points = points;
    }

    public static Question[] init() {
        Question[] questions = new Question[30];
    
        // CSP Questions
        questions[0] = new Question("What is an example of binary data?", "22222", "111000", "303030300", "00600903", "Think in terms of binary digits (0s and 1s).", 1, 1, "csp", 40);
        questions[1] = new Question("Which of the following is a characteristic of a good password?", "Simple", "Easily guessable", "Complex and unique", "Short", "Consider what makes it hard for others to guess your password", 3, 2, "csp", 35);
        questions[2] = new Question("What is an example of a lossless compression algorithm?", "MP3", "JPEG", "GIF", "ZIP", "This type of compression retains all original data", 4, 2, "csp", 30);
        questions[3] = new Question("What does the acronym 'URL' stand for?", "Uniform Resource Locator", "Universal Record Locator", "Unique Resource Link", "Universal Resource Locator", "It's a standard way to access resources on the internet", 1, 1, "csp", 40);
        questions[4] = new Question("Which of the following is NOT a programming language?", "Python", "Java", "CSS", "HTML", "Two of these are markup languages; one is only for style", 3, 1, "csp", 45);
        questions[5] = new Question("What is the purpose of HTML?", "Styling web pages", "Creating dynamic content", "Defining the structure of web pages", "Handling server-side logic", "It structures web content", 3, 1, "csp", 45);
        questions[6] = new Question("What is the main function of a firewall?", "To block all incoming traffic", "To filter network traffic", "To speed up internet connection", "To install updates automatically", "It regulates the flow of data in and out of a network", 2, 2, "csp", 30);
        questions[7] = new Question("What is an example of symmetric encryption?", "RSA", "AES", "Diffie-Hellman", "SHA-256", "This encryption type uses the same key for both encryption and decryption", 2, 2, "csp", 35);
        questions[8] = new Question("What is the purpose of a DNS?", "To convert IP addresses to domain names", "To convert domain names to IP addresses", "To encrypt data transmission", "To protect against malware", "It translates domain names into something machines can read", 2, 1, "csp", 40);
        questions[9] = new Question("Which of the following is NOT a type of network topology?", "Ring", "Bus", "Mesh", "Point", "Think about a less common type of network layout", 4, 1, "csp", 45);
        questions[10] = new Question("What is the role of a router in a network?", "To connect devices within the same network", "To connect multiple networks together", "To protect against viruses", "To host websites", "It links different networks together", 2, 2, "csp", 35);
        questions[11] = new Question("Which of the following is an example of a DNS record type?", "FTP", "MX", "HTTP", "SSL", "It's used for email routing", 2, 2, "csp", 30);
        questions[12] = new Question("What does the acronym 'HTTP' stand for?", "Hypertext Transfer Protocol", "Hyperlink Text Transfer Protocol", "High Transfer Technology Protocol", "Home Text Terminal Protocol", "It's the foundation of web data transfer", 1, 1, "csp", 40);
        questions[13] = new Question("What is the purpose of an IP address?", "To identify a specific website", "To identify a specific device on a network", "To encrypt internet traffic", "To manage email communication", "It uniquely identifies devices on a network", 2, 1, "csp", 45);
        questions[14] = new Question("Which of the following is NOT a characteristic of a good algorithm?", "Efficiency", "Completeness", "Uniqueness", "Correctness", "Good algorithms should not require uniqueness", 3, 2, "csp", 35);
    
        // CSA Questions
        questions[15] = new Question("Which of the following is NOT an object-oriented programming language?", "Java", "Python", "C", "C++", "It's a procedural language, not object-oriented", 3, 1, "csa", 45);
        questions[16] = new Question("What is an example of a data structure?", "ArrayList", "Math", "Scanner", "String", "This structure allows indexed access to elements", 1, 1, "csa", 40);
        questions[17] = new Question("What does the '++' operator do in Java?", "Increment by 1", "Decrement by 1", "Multiply by 2", "Divide by 2", "It increases a variable's value by one", 1, 2, "csa", 35);
        questions[18] = new Question("Which keyword is used to define a method in Java?", "func", "method", "def", "public", "This keyword defines the visibility of methods and fields", 4, 1, "csa", 45);
        questions[19] = new Question("What is the result of 7 % 3 in Java?", "3", "2", "1", "0", "It's the remainder of a division operation", 3, 2, "csa", 30);
        questions[20] = new Question("What is the purpose of the 'this' keyword in Java?", "To refer to the current instance of the class", "To indicate the end of a method", "To print text to the console", "To create a new object", "It refers to the current object within a class", 1, 2, "csa", 35);
        questions[21] = new Question("What is the output of 'System.out.println(5 == 5)?' in Java?", "True", "False", "5", "Error", "It's a boolean expression that checks for equalit", 1, 1, "csa", 40);
        questions[22] = new Question("Which of the following is an example of a primitive data type in Java?", "String", "Array", "Integer", "Boolean", "It's a true/false data type", 4, 1, "csa", 45);
        questions[23] = new Question("What does the 'new' keyword do in Java?", "Delete an object", "Create a new object", "Declare a variable", "Define a method", "It creates a new instance of a class", 2, 2, "csa", 30);
        questions[24] = new Question("What is the purpose of inheritance in object-oriented programming?", "To create multiple instances of a class", "To allow one class to acquire properties and behavior of another class", "To perform mathematical operations", "To store data temporarily", "It allows one class to use the properties of another", 2, 2, "csa", 35);
        questions[25] = new Question("Which data structure uses the 'first-in, first-out' (FIFO) principle?", "Stack", "Queue", "Linked List", "Tree", "This structure follows a first-in, first-out principle", 2, 1, "csa", 40);
        questions[26] = new Question("What is the purpose of the 'super' keyword in Java?", "To start a loop", "To call the superclass constructor", "To print output to the console", "To delete an object", "It calls the parent class's constructor", 2, 2, "csa", 35);
        questions[27] = new Question("What does the 'extends' keyword indicate in Java?", "Inheritance", "Composition", "Initialization", "Delegation", "It signifies inheritance in class declarations", 1, 1, "csa", 45);
        questions[28] = new Question("What is the main advantage of using an interface in Java?", "To define a contract for implementing classes", "To perform input/output operations", "To handle exceptions", "To represent a set of related data", "It defines a set of methods a class must implement", 1, 2, "csa", 30);
        questions[29] = new Question("Which of the following is NOT a Java keyword?", "class", "method", "break", "integer", "It's not used to declare classes or methods", 4, 1, "csa", 45);
    
        return questions;
    }

    public static void main(String[] args) {
        Question questions[] = init();
        for (Question question : questions) {
            System.out.println(question.getQuestion());
        }
    }
}   
