#include <stdio.h>
#include <stdlib.h>
typedef struct node
{
    int x;
    struct node *suivant;
}node;
node *p,*tete=NULL,*q;
void affiche(node *tete){
    
    p = tete;
    while(p != NULL){
        printf("%i \t",p->x);
        p = p->suivant;
    }
    }
node * fill(){
   
    int x = 0;
    tete=NULL;
    while(x < 5){
        p = (node *)malloc(sizeof(node));
        p->x  = x;
        if(tete == NULL){
            tete = p ;
        }else
        q->suivant = p;
        q=p;
        x++;
    }
    return tete;
}

int main(){
    printf("hello world");
    affiche(fill());
    return 0;
}