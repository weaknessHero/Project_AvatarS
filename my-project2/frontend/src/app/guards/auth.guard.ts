// 바로 '/myinfo' 접근 시 오류 후 확인 버튼 누르면 로그인 화면으로 연동
import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {SignService} from "../service/rest-api/sign.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate{
  constructor(private router: Router,
              private signService: SignService) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean{
    if (this.signService.isSignIn()){
      return true;
    } else{
      this.router.navigate(['/signin'], {queryParams: { redirectTo: state.url }});
      return false;
    }
  }
}
