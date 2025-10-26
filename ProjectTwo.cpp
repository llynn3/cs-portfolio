#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <algorithm>
#include <cctype>
using namespace std;

//----------------------------------------------------
// Struct to represent a course and its prerequisites
//----------------------------------------------------
struct Course {
    string courseId;         // Example: "CSCI101"
    string title;            // Example: "Intro to Programming in C++"
    vector<string> prereqs;  // Holds all prerequisite course IDs
};

//----------------------------------------------------
// Node structure for the BST
//----------------------------------------------------
struct Node {
    Course course;
    Node* left;
    Node* right;

    // Constructor that sets up a new node with a given course
    Node(Course aCourse) {
        course = aCourse;
        left = nullptr;
        right = nullptr;
    }
};

//----------------------------------------------------
// Binary Search Tree class definition
//----------------------------------------------------
class BinarySearchTree {
private:
    Node* root;                              // Root node of the BST
    void addNode(Node* node, Course course);  // Recursive helper
    void inOrder(Node* node);                 // Used to print the tree in sorted order

public:
    BinarySearchTree();                       // Constructor
    void Insert(Course course);               // Add a new course
    void PrintSampleSchedule();               // Print all courses in order
    Course Search(string courseId);           // Find a specific course
};

//----------------------------------------------------
// Constructor initializes root as null
//----------------------------------------------------
BinarySearchTree::BinarySearchTree() {
    root = nullptr;
}

//----------------------------------------------------
// Recursive helper to insert nodes in the correct place
//----------------------------------------------------
void BinarySearchTree::addNode(Node* node, Course course) {
    if (course.courseId < node->course.courseId) {          // If new course ID is smaller
        if (node->left == nullptr) node->left = new Node(course);  // Add to left
        else addNode(node->left, course);                          // Keep going left
    } else {                                                // If new course ID is greater
        if (node->right == nullptr) node->right = new Node(course); // Add to right
        else addNode(node->right, course);                         // Keep going right
    }
}

//----------------------------------------------------
// Public insert method calls recursive helper
//----------------------------------------------------
void BinarySearchTree::Insert(Course course) {
    if (root == nullptr) root = new Node(course);   // If tree is empty, set root
    else addNode(root, course);                     // Otherwise, insert correctly
}

//----------------------------------------------------
// Helper function to remove extra quotes/spaces from text
//----------------------------------------------------
void trimQuotes(string &s) {
    while (!s.empty() && (isspace(s.front()) || s.front() == '"' || s.front() == '\'')) s.erase(s.begin());
    while (!s.empty() && (isspace(s.back()) || s.back() == '"' || s.back() == '\'')) s.pop_back();
}

//----------------------------------------------------
// Load courses from CSV file into the BST
//----------------------------------------------------
void loadCourses(string filename, BinarySearchTree &bst) {
    ifstream file(filename);                           // Open the CSV file
    if (!file.is_open()) {                             // If file can’t be opened
        cout << "Error: Could not open file " << filename << endl;
        return;
    }

    string line;
    while (getline(file, line)) {                      // Read file line by line
        if (line.empty()) continue;                    // Skip empty lines

        stringstream ss(line);                         // Convert line into stream for parsing
        Course course;
        string prereq;

        getline(ss, course.courseId, ',');             // Read first field: course ID
        trimQuotes(course.courseId);

        getline(ss, course.title, ',');                // Read second field: course title
        trimQuotes(course.title);

        while (getline(ss, prereq, ',')) {             // Read remaining fields as prerequisites
            trimQuotes(prereq);
            if (!prereq.empty()) course.prereqs.push_back(prereq);
        }

        bst.Insert(course);                            // Insert each course into the BST
    }

    file.close();                                      // Close file after reading
    cout << "Courses successfully loaded from file.\n";
}

//----------------------------------------------------
// Print BST in sorted (alphanumeric) order
//----------------------------------------------------
void BinarySearchTree::inOrder(Node* node) {
    if (node != nullptr) {
        inOrder(node->left);                           // Visit left subtree
        cout << node->course.courseId << ", " << node->course.title << endl; // Print current node
        inOrder(node->right);                          // Visit right subtree
    }
}

//----------------------------------------------------
// Print all courses alphabetically
//----------------------------------------------------
void BinarySearchTree::PrintSampleSchedule() {
    cout << "\nHere is a sample schedule:\n" << endl;
    inOrder(root);
    cout << endl;
}

//----------------------------------------------------
// Search for a course in the BST by ID
//----------------------------------------------------
Course BinarySearchTree::Search(string courseId) {
    Node* current = root;

    while (current != nullptr) {                       // Keep looping until found or tree ends
        if (current->course.courseId == courseId) {    // Match found
            return current->course;
        } else if (courseId < current->course.courseId) {
            current = current->left;                   // Move left if smaller
        } else {
            current = current->right;                  // Move right if larger
        }
    }

    // If not found, return empty course
    Course emptyCourse;
    emptyCourse.courseId = "NULL";
    return emptyCourse;
}

//----------------------------------------------------
// Main function with menu-driven interface
//----------------------------------------------------
int main() {
    BinarySearchTree bst;       // Create a new BST object
    string filename;            // To hold file name input
    int choice = 0;             // User menu choice

    while (choice != 9) {       // Loop until user chooses to exit
        // Display main menu
        cout << "\nWelcome to the course planner." << endl;
        cout << "  1. Load Data Structure." << endl;
        cout << "  2. Print Course List." << endl;
        cout << "  3. Print Course." << endl;
        cout << "  9. Exit." << endl;
        cout << "\nWhat would you like to do? ";
        cin >> choice;

        switch (choice) {
        case 1: // Load CSV data
            cout << "\nEnter course data file name (e.g. ABCU_Advising_Program_Input.csv): ";
            cin >> filename;
            loadCourses(filename, bst);
            break;

        case 2: // Print all courses
            bst.PrintSampleSchedule();
            break;

        case 3: { // Print one course
            string courseId;
            cout << "\nWhat course do you want to know about? ";
            cin >> courseId;

            // Convert to uppercase so input is case-insensitive
            transform(courseId.begin(), courseId.end(), courseId.begin(), ::toupper);

            Course found = bst.Search(courseId);        // Search for the course

            if (found.courseId == "NULL") {            // If not found
                cout << "Course not found.\n";
            } else {                                   // Otherwise, print info
                cout << found.courseId << ", " << found.title << endl;

                if (found.prereqs.empty()) {
                    cout << "No prerequisites.\n";
                } else {
                    cout << "Prerequisites: ";
                    for (size_t i = 0; i < found.prereqs.size(); ++i) {
                        cout << found.prereqs[i];
                        if (i < found.prereqs.size() - 1) cout << ", ";
                    }
                    cout << endl;
                }
            }
            break;
        }

        case 9: // Exit program
            cout << "\nThank you for using the course planner. Goodbye!\n";
            break;

        default:
            cout << "\n" << choice << " is not a valid option.\n";
            break;
        }
    }

    return 0;  // End of program
}
