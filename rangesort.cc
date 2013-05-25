#include <iostream>
#include <map>
#define DEBUG 0
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

int main () {
    
    int pairs_to_read;
    cin >> pairs_to_read;

    map<int,int> mymap;
    for (int i = 0; i < pairs_to_read; ++i) {
      int A, B;
    	cin >> A >> B;
        
        if (A > B) {
            cerr << "ERROR Invalid range, range must be low to high: " << A << " " << B << endl;
            break;
        }
        
        map<int,int>::iterator it = mymap.lower_bound(A);

        if (mymap.size() == 0) {
            if(DEBUG)  cout << "case 0 ";
            map_add (&mymap, A, B);
            continue;
        }

        --it;
        int a, b;
        a = it->first;
        b = it->second;
        if(DEBUG)  cout << "a,b=[" << a << "," << b << "] A,B=[" << A << "," << B << "]" << endl;

        if (a < A && b < B && b > A) {
            if(DEBUG)  cout << "case 1 ";
            map_update (&mymap, a, a, B);
            continue;
        }

        if (a > A && b > B, a < B) {
            if(DEBUG)  cout << "case 2 ";
            continue;
        }
        if (a > A && b > B && a < B) {   // read-only, so need to delete and re-add
            if(DEBUG)  cout << "case 3 ";
            map_update (&mymap, a, A, b);
            continue;
        }

        if (a > A && b < B) {   // read-only, so need to delete and re-add
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
        
    }
    	

    // Print content
        for (std::map<int,int>::iterator it=mymap.begin(); it!=mymap.end(); ++it)
            std::cout << it->first << " --- " << it->second << '\n';

}

