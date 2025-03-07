//============================================================================
// Name        : BinarySearchTree.cpp
// Author      : Riley Johnson
// Version     : 1.0
// Copyright   : Copyright © 2023 SNHU COCE
// Description : Lab 5-2 Binary Search Tree
//============================================================================

#include <iostream>
#include <time.h>

#include "CSVparser.hpp"

using namespace std;

//============================================================================
// Global definitions visible to all methods and classes
//============================================================================

// forward declarations
double strToDouble(string str, char ch);

// define a structure to hold bid information
struct Bid {
    string bidId; // unique identifier
    string title;
    string fund;
    double amount;
    Bid() {
        amount = 0.0;
    }
};

// Internal structure for tree node
struct Node {
    Bid bid;
    Node *left;
    Node *right;

    // default constructor
    Node() {
        left = nullptr;
        right = nullptr;
    }

    // initialize with a bid
    Node(Bid aBid) :
            Node() {
        bid = aBid;
    }
};

//============================================================================
// Binary Search Tree class definition
//============================================================================

/**
 * Define a class containing data members and methods to
 * implement a binary search tree
 */
class BinarySearchTree {

private:
    Node* root;

    void addNode(Node* node, Bid bid);
    void inOrder(Node* node);
    void postOrder(Node* node);
    void preOrder(Node* node);
    Node* removeNode(Node* node, string bidId);

public:
    BinarySearchTree();
    virtual ~BinarySearchTree();
    void InOrder();
    void PostOrder();
    void PreOrder();
    void Insert(Bid bid);
    void Remove(string bidId);
    Bid Search(string bidId);
};

/**
 * Default constructor
 */
BinarySearchTree::BinarySearchTree() {
    
    //root is equal to nullptr
    root = nullptr;
}

/**
 * Destructor
 */
BinarySearchTree::~BinarySearchTree() {

    while (root != nullptr) {

        // remove each node as it becomes the root 
        Remove(root->bid.bidId);

    }
}

/**
 * Traverse the tree in order
 */
void BinarySearchTree::InOrder() {

    // call inOrder fuction and pass root 
    inOrder(root);
}

/**
 * Traverse the tree in post-order
 */
void BinarySearchTree::PostOrder() {
    
    // call postOrder fuction and pass root
    postOrder(root);
}

/**
 * Traverse the tree in pre-order
 */
void BinarySearchTree::PreOrder() {

    // call preOrder fuction and pass root
    preOrder(root);
}



/**
 * Insert a bid
 */
void BinarySearchTree::Insert(Bid bid) {

    // instanciate new node
    Node* newNode = new Node(bid);

    // if root is null
    if (root == nullptr) { 
        root = newNode; // new node becomes root
    }
    else{
        addNode(root, bid); // add new node to tree using addNode()
    }

}

/**
 * Remove a bid
 */
void BinarySearchTree::Remove(string bidId) {

    // call removeNode function and pass it root + bidId parameter 
    removeNode(root, bidId);

}

/**
 * Search for a bid
 */
Bid BinarySearchTree::Search(string bidId) {

    // set current node equal to root
    Node* currNode = root; 

    while (currNode != nullptr) { // while current node is not null 

        if (currNode->bid.bidId == bidId) { // if matching bidId is found 

            return currNode->bid; // return bid

        }
        else if (bidId < currNode->bid.bidId) { // if bidId key is less than current bidId

            currNode = currNode->left; // traverse left 

        }
        else {

            currNode = currNode->right; // traverse right 

        }

    }

    //otherwise return null bid
    Bid bid;
    return bid;
}

/**
 * Add a bid to some node (recursive)
 *
 * @param node Current node in tree
 * @param bid Bid to be added
 */
void BinarySearchTree::addNode(Node* node, Bid bid) {

    // instanciate new node 
    Node* newNode = new Node(bid);
    
    if (bid.bidId < node->bid.bidId) { // if bid to be entered has a bidId less than bid in current node 

        if (node->left == nullptr) { // if current node has no left child 
            node->left = newNode; // new node becomes left child 
        }
        else {
            addNode(node->left, bid); // recusrsively transverse left 
        }
    }
    else { // if bid to be entered has a bidId greater than the bid in current node 

        if (node->right == nullptr) { // if current node has no right child 
            node->right = newNode; // new node becomes right child 
        }
        else { 
            addNode(node->right, bid); // recursively transverse right 
        }

    }

}


void BinarySearchTree::inOrder(Node* node) {

    if (node != nullptr) { // if current node is not null

        inOrder(node->left); // traverse left
        cout << node->bid.bidId << ": " << node->bid.title << " | " << node->bid.amount << " | " << node->bid.fund << endl;
        inOrder(node->right); // traverse right 

    }

    return;
}

void BinarySearchTree::postOrder(Node* node) {

    if (node != nullptr) { // if current node is not null

        postOrder(node->left); // traverse left
        postOrder(node->right); // traverse right
        cout << node->bid.bidId << ": " << node->bid.title << " | " << node->bid.amount << " | " << node->bid.fund << endl;
    }

    return;
}

void BinarySearchTree::preOrder(Node* node) {

    if (node != nullptr) { // if current node is not null

        cout << node->bid.bidId << ": " << node->bid.title << " | " << node->bid.amount << " | " << node->bid.fund << endl;
        preOrder(node->left); // traverse left
        preOrder(node->right); // traverse right 

    }

    return; 
}

/**
 * Remove a bid from some node (recursive)
 */
Node* BinarySearchTree::removeNode(Node* node, string bidId) {

    Node* currNode = node;

    Node* parentNode = nullptr;
    Node* succNode = nullptr;
    Node* temp = nullptr;

    while (currNode != nullptr) {

        if (currNode->bid.bidId == bidId) { // if found match for bidId in current node 

            if (currNode->left == nullptr && currNode->right == nullptr) { // if node is leaf 

                if (parentNode == nullptr) { // if node is root 
                    root = nullptr; // remove root
                }
                else if (parentNode->left == currNode) { // if current node is left child
                    parentNode->left = nullptr; // remove leaf
                }
                else { // current node is right child
                    parentNode->right = nullptr; // remove leaf 
                }

            }
            else if (currNode->right == nullptr) { // node only has left child 

                if (parentNode == nullptr) { // if node is root 
                    root = currNode->left; // left child becomes root 
                }
                else if (parentNode->left == currNode) { // if current node is left child 
                    parentNode->left = currNode->left; // skip over current node 
                }
                else { // current node is right child 
                    parentNode->right = currNode->left; // skip over current node 
                }

            }
            else if (currNode->left == nullptr) { // node only has right child 

                if (parentNode == nullptr) { // if node is root
                    root = currNode->right; // right child becomes root
                }
                else if (parentNode->left == currNode) { // if current node is a left child 
                    parentNode->left = currNode->right; // skip over current node 
                }
                else { // current node is right child 
                    parentNode->right = currNode->right;
                }

            }
            else { // node has 2 children 

                // find successor to current node 
                succNode = currNode->right;

                while (succNode->left != nullptr) {
                    succNode = succNode->left; // traverse left 
                }

                temp = succNode; // store copy of successor node 
                removeNode(succNode, succNode->bid.bidId); // reccurisively remove successor
                currNode = temp; // successor becomes current node 

            }

            return node;

        }
        else if (currNode->bid.bidId < bidId) { // current node's bidId is less than search key bidId
            parentNode = currNode; // current node becomes parent node 
            currNode = currNode->right; // traverse right
        }
        else { // current node's bidId is greater than search key bidId
            parentNode = currNode; // current node becomes parent node
            currNode = currNode->left; // traverse left
        }

    }

    return nullptr; // node not fund 

}



//============================================================================
// Static methods used for testing
//============================================================================

/**
 * Display the bid information to the console (std::out)
 *
 * @param bid struct containing the bid info
 */
void displayBid(Bid bid) {
    cout << bid.bidId << ": " << bid.title << " | " << bid.amount << " | "
            << bid.fund << endl;
    return;
}

/**
 * Load a CSV file containing bids into a container
 *
 * @param csvPath the path to the CSV file to load
 * @return a container holding all the bids read
 */
void loadBids(string csvPath, BinarySearchTree* bst) {
    cout << "Loading CSV file " << csvPath << endl;

    // initialize the CSV Parser using the given path
    csv::Parser file = csv::Parser(csvPath);

    /* read and display header row - optional
    vector<string> header = file.getHeader();
    for (auto const& c : header) {
        cout << c << " | ";
    }
    cout << "" << endl;
    */

    int count = 0;

    try {
        // loop to read rows of a CSV file
        for (unsigned int i = 0; i < file.rowCount(); i++) {

            // Create a data structure and add to the collection of bids
            Bid bid;
            bid.bidId = file[i][1];
            bid.title = file[i][0];
            bid.fund = file[i][8];
            bid.amount = strToDouble(file[i][4], '$');

            //cout << "Item: " << bid.title << ", Fund: " << bid.fund << ", Amount: " << bid.amount << endl;

            // push this bid to the end
            bst->Insert(bid);
            ++count;
        }

        cout << count << " bids read" << endl;

    } catch (csv::Error &e) {
        std::cerr << e.what() << std::endl;
    }
}

/**
 * Simple C function to convert a string to a double
 * after stripping out unwanted char
 *
 * credit: http://stackoverflow.com/a/24875936
 *
 * @param ch The character to strip out
 */
double strToDouble(string str, char ch) {
    str.erase(remove(str.begin(), str.end(), ch), str.end());
    return atof(str.c_str());
}

/**
 * The one and only main() method
 */
int main(int argc, char* argv[]) {

    // process command line arguments
    string csvPath, bidKey;
    switch (argc) {
    case 2:
        csvPath = argv[1];
        bidKey = "98223";
        break;
    case 3:
        csvPath = argv[1];
        bidKey = argv[2];
        break;
    default:
        csvPath = "CS 300 eBid_Monthly_Sales_Dec_2016.csv";
        bidKey = "98223";
    }

    // Define a timer variable
    clock_t ticks;

    // Define a binary search tree to hold all bids
    BinarySearchTree* bst;
    bst = new BinarySearchTree();
    Bid bid;

    int choice = 0;
    while (choice != 9) {
        cout << "Menu:" << endl;
        cout << "  1. Load Bids" << endl;
        cout << "  2. Display All Bids" << endl;
        cout << "  3. Find Bid" << endl;
        cout << "  4. Remove Bid" << endl;
        cout << "  9. Exit" << endl;
        cout << "Enter choice: ";
        cin >> choice;

        switch (choice) {

        case 1:
            
            // Initialize a timer variable before loading bids
            ticks = clock();

            // Complete the method call to load the bids
            loadBids(csvPath, bst);

            //cout << bst->Size() << " bids read" << endl;

            // Calculate elapsed time and display result
            ticks = clock() - ticks; // current clock ticks minus starting clock ticks
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;
            break;

        case 2:
            bst->InOrder();
            break;

        case 3:
            ticks = clock();

            bid = bst->Search(bidKey);

            ticks = clock() - ticks; // current clock ticks minus starting clock ticks

            if (!bid.bidId.empty()) {
                displayBid(bid);
            } else {
            	cout << "Bid Id " << bidKey << " not found." << endl;
            }

            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;

            break;

        case 4:
            bst->Remove(bidKey);
            break;
        }
    }

    cout << "\nGood bye." << endl;

	return 0;
}
