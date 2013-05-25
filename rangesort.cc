#include <iostream>
#include <map>
#define DEBUG 1
using namespace std;

void map_add (map<int,int>* mymap, int key, int val) {
    mymap->insert(pair<int,int>(key,val));
    if (DEBUG) cout << "added " << key << ", " << val << endl;
}

void map_update (map<int,int>* mymap, int key_to_update, int new_key, int val) {
    mymap->erase (key_to_update);
    mymap->insert(pair<int,int>(new_key,val));
    if (DEBUG) cout << "updated " << key_to_update << ", " << val << endl;
}


void read_and_build(map<int,int> &mymap) {
    int pairs_to_read;
    cin >> pairs_to_read;

    for (int i = 0; i < pairs_to_read; ++i) {
        int A, B;
        cin >> A >> B;
        if (A > B) {
            cerr << "ERROR Invalid range, range must be low to high: " << A << " " << B << endl;
            break;
        }
        map<int,int>::iterator it = mymap.lower_bound(A);

        // Special case: initial
        if (mymap.size() == 0) {
            if(DEBUG)  cout << "case 0 ";
            map_add (&mymap, A, B);
            continue;
        }
        --it;
        int a, b;
        a = it->first;
        b = it->second;
        if(DEBUG)  cout << "\ta,b=[" << a << "," << b << "] A,B=[" << A << "," << B << "]" << endl;
        /* 
        Variables A, B is the current range we are trying to add to list.
        Variables a, b is already in list. It is the closest range to A, B.

            In addition to case 0, there are 6 cases:
        1: Extend range right to B
        2: Do nothing, as old range is encompases new range.
        3: Extend range left to A
        4: Extend range both left and right to A, B
        5: Insert entire new range. A, B is left of a, b
        6: Insert entire new range. A, B is right of a, b
        */

        if (a < A && b < B && b > A) {
            if(DEBUG)  cout << "case 1 ";
            map_update (&mymap, a, a, B);
            continue;
        }
        if (a > A && b > B && a < B) {
            if(DEBUG)  cout << "case 2 ";
            continue;
        }
        if (a > A && b > B && a < B) {   // map is read-only, so need to delete and re-add
            if(DEBUG)  cout << "case 3 ";
            map_update (&mymap, a, A, b);
            continue;
        }
        if (a > A && b < B) {           // map is read-only, so need to delete and re-add
            if(DEBUG)  cout << "case 4 ";
            map_update (&mymap, a, A, B);
            continue;
        }
        if (b < A) {
            if(DEBUG)  cout << "case 5 ";
            map_add (&mymap, A, B);
            continue;
        }
        if (B < a) {
            if(DEBUG)  cout << "case 6 ";
            map_add (&mymap, A, B);
            continue;
        }
    } // for

}

void map_print (map<int,int>& m) {
    cout << "print map" << endl;
    // Print content
    for (std::map<int,int>::iterator it=m.begin(); it!=m.end(); ++it)
        std::cout << it->first << " --- " << it->second << '\n';
}


int main () {
    map<int,int> mymap;

    read_and_build(mymap);

    map_print (mymap);

    return 0;
}

