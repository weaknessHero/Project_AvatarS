import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import firebase from "firebase/compat/app";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { FormsModule } from "@angular/forms";
import {AngularFireModule} from "@angular/fire/compat";
import {AngularFireAuthModule} from "@angular/fire/compat/auth";
import { LoginComponent } from './page/admin/login/login.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { UserinfoComponent } from './userinfo/userinfo.component';
import {HttpClientModule} from "@angular/common/http";
import { ProductListComponent } from './product-list/product-list.component';

const firebaseConfig = {
  apiKey: "AIzaSyC-30NLxccpMplYTcMtmga2e9zk030279E",
  authDomain: "avatars-c5350.firebaseapp.com",
  projectId: "avatars-c5350",
  storageBucket: "avatars-c5350.appspot.com",
  messagingSenderId: "862550936227",
  appId: "1:862550936227:web:7d21c7c3e16d792e8b0d16",
  measurementId: "G-FMSV38GX8V"
};
@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    SigninComponent,
    SignupComponent,
    UserinfoComponent,
    ProductListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    AngularFireModule.initializeApp(firebaseConfig),
    AngularFireAuthModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
