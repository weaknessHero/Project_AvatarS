import { Injectable } from '@angular/core';
import {AngularFireAuth} from "@angular/fire/compat/auth";
import {User} from 'firebase/auth';
import firebase from "firebase/compat/app";
import auth = firebase.auth;
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user: User;
  constructor(public afAuth: AngularFireAuth) {
    this.afAuth.authState.subscribe(user =>{
      if(user){
        this.user = user;
        localStorage.setItem('user', JSON.stringify(this.user));
      } else{
        localStorage.setItem('user', null);
      }
    });
  }

  async loginWithGoogle(){
    return await this.afAuth.signInWithPopup(new auth.GoogleAuthProvider());
  }

  isUserLoggedIn(){
    return JSON.parse(localStorage.getItem('user'));
  }

  async logout(){
    return await this.afAuth.signOut();
  }


}
