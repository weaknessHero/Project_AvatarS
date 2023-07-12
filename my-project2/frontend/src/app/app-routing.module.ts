import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./component/home.component";
import {SigninComponent} from "./component/member/signin/signin.component";
import {SignupComponent} from "./component/member/signup/signup.component";
import {LogoutComponent} from "./component/logout/logout.component";
import {MyinfoComponent} from "./component/member/myinfo/myinfo.component";
import {AuthGuard} from "./guards/auth.guard";
import {RegistComponent} from "./component/product/regist/regist.component";
import {DetailComponent} from "./component/product/detail/detail.component";
import {AvatarpageComponent} from "./component/avatar/avatarpage/avatarpage.component";
import {MyavatarComponent} from "./component/avatar/myavatar/myavatar.component";
import { FitMainComponent } from './component/fit/fit-main/fit-main.component';
import { ImageSearchComponent } from './component/fit/image-search/image-search.component';
import {DetectLabelsComponent} from "./component/fit/detect/detect-labels/detect-labels.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'signin', component: SigninComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'myinfo', component: MyinfoComponent, canActivate: [AuthGuard]},
  {path: 'regist', component: RegistComponent},
  {path: 'detail', component: DetailComponent},
  {path: 'avatarpage', component: AvatarpageComponent},
  {path: 'myavatar', component:MyavatarComponent},
  {path: 'fit-main', component:FitMainComponent},
  {path: 'image-search', component:ImageSearchComponent},
  {path: 'detect-labels', component:DetectLabelsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
