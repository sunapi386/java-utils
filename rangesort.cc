/*
May 24, 2013
Jason Sun

Give a list of ranges, we uniquely insert them to a map, 
the <key> being the lower bound and <value> being upper, inclusively.

Insertion of a list of ranges takes O( nlog(n) ), 
n insertions each O( log(n) ), as per STL implementation.

Find is also O( log(n) ), as C++'s STL implementation.

This was done for integers only, but we can use any type we want in a map, like floats.
I piggybacked off map implementation. It makes sense to use things already built.

The general idea is to:
1) Sort by lower range, collapse and fold overlapping ranges
2) Binary search on list of ranges (now taken care of by C++ map)

To try it out, see testing.in for documentation.
My tests are in ranges.in

*/

#include <iostream>
#include <map>
#define DEBUG 0
using namespace std;



void map_add (map<int,int>* mymap, int key, int val) {
    mymap->insert(pair<int,int>(key,val));
    if (DEBUG) cout << "add " << key << ", " << val;
}



void map_update (map<int,int>* mymap, int old_key, int new_key, int val) {
    // map is read-only, so need to delete and re-add
    mymap->erase ( mymap->find(old_key), mymap->upper_bound(val) );    // erasing by range [x,y)
    mymap->insert(pair<int,int>(new_key,val));
    if (DEBUG) cout << "updated " << old_key << ", " << val << " to " << new_key << ", " << val;
}




void read_and_build(map<int,int> &mymap) {
    int pairs_to_read;
    cin >> pairs_to_read;

    for (int i = 0; i < pairs_to_read; ++i) {
        if (DEBUG) cout << "\nLOOP " << i << ": ";
        int A, B;
        cin >> A >> B;
        if (A > B) {
            cerr << "ERROR Invalid range, range must be low to high: " << A << " " << B << endl;
            break;
        }
        map<int,int>::iterator it = mymap.lower_bound(A);

        // Special case 0: initialization.
        if (mymap.size() == 0) {
            if(DEBUG)  cout << "\t\t\t\tcase 0 ";
            map_add (&mymap, A, B);
            continue;
        }
        --it;
        int a, b;
        a = it->first;
        b = it->second;
        
        if(DEBUG)  cout << "\ta,b=[" << a << "," << b << "] A,B=[" << A << "," << B << "]\t";

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

        if (a <= A && b < B && b >= A) {
            if(DEBUG)  cout << "case 1 ";
            map_update (&mymap, a, a, B);
            continue;
        }
        if (a <= A && b >= B && a <= B) {
            if(DEBUG)  cout << "case 2 ";
            continue;
        }
        if (a >= A && b >= B && a <= B) { 
            if(DEBUG)  cout << "case 3 ";
            map_update (&mymap, a, A, b);
            continue;
        }
        if (a >= A && b <= B) {           
            if(DEBUG)  cout << "case 4 ";
            map_update (&mymap, a, A, B);
            continue;
        }
        if (b < A) {
            if(DEBUG)  cout << "case 5 ";
            map_add (&mymap, A, B);
            continue;
        }
        if (B <= a) {
            if(DEBUG)  cout << "case 6 ";
            map_add (&mymap, A, B);
            continue;
        }
        if(DEBUG) cout << " end LOOP " << i;
    } // for

}


// Print content
void map_print (map<int,int>& m) {
    cout << "\nPRINTED MAP:" << endl;
    for (map<int,int>::iterator it=m.begin(); it!=m.end(); ++it)
        cout << it->first << " --- " << it->second << '\n';
}


// The search function
bool map_is_in_range (map<int,int>& mymap, int x) {
    map<int,int>::iterator it = mymap.upper_bound(x);
    --it;
    if (DEBUG) cout << "find " << x << " prev range " << it->first << ", " << it->second << endl;
    return (x >= it->first && x <= it->second);
}







int main () {

    // Create a map
    map<int,int> mymap;
    read_and_build(mymap);

    map_print (mymap);

    // Read some numbers, see if they are in a range
    while (true) {
        int x = 0;
        cin >> x;
        cout << "\t: " << map_is_in_range(mymap, x) << endl;
        if (x == 1337) break;
    }

    return 0;
}

