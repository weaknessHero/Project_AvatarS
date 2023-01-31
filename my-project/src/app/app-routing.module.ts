import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./page/admin/login/login.component";
import {SigninComponent} from "./signin/signin.component";
import {SignupComponent} from "./signup/signup.component";
import {UserinfoComponent} from "./userinfo/userinfo.component";
import {MainComponent} from "./main/main.component";
import {ProductListComponent} from "./product-list/product-list.component";

const routes: Routes = [
  { path: 'signin', component: SigninComponent},
  { path: 'signup', component: SignupComponent},
  { path: 'userinfo', component: UserinfoComponent},
  { path: 'main', component: MainComponent},
  { path: 'ProductList', component: ProductListComponent},
//  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
