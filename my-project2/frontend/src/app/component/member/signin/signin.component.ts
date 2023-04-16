import {Component, OnInit} from '@angular/core';
import {FormGroup, FormControl, Validators} from "@angular/forms";
import { SignService } from "../../../service/rest-api/sign.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Observable, switchMap} from "rxjs";
import {KakaoService} from "../../../service/rest-api/kakao/kakao.service";

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  //template: `<img class="kakaoLogin" src="assets/kakao_login.jpg" (click)="KakaoIn()">`,
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit{

  redirectTo: string;
  signInForm: FormGroup;

  constructor(private signService: SignService,
              private router: Router,
              private route: ActivatedRoute,
              private http: HttpClient,
              private kakaoService: KakaoService) {
    this.signInForm = new FormGroup({
      id: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });
  }

  get id(){
    return this.signInForm.get('id');
  }

  get password(){
    return this.signInForm.get('password');
  }

  getKakaoAuthUrl(): Observable<any> {
    const url = 'https://kauth.kakao.com/oauth/authorize' +
      '?client_id=4985bb6e1259e1eea63d88a7decc596b' +
      '&redirect_uri=http://localhost:8080/signin/oauth_kakao' +
      '&response_type=code';
    return this.http.get<any>(url);
  }

  oauthKakao(code: string): Observable<any> {
    const url = 'https://kauth.kakao.com/oauth/token';
    const body = {
      grant_type: 'authorization_code',
      client_id: '4985bb6e1259e1eea63d88a7decc596b',
      redirect_uri: 'http://localhost:8080/signin/oauth_kakao',
      code: code,
    };
    return this.http.post<any>(url, body).pipe(
      switchMap(response => {
        const access_Token = response.access_token;
        const headers = {
          Authorization: `Bearer ${access_Token}`
        };
        return this.http.get<any>('https://kapi.kakao.com/v2/user/me', {headers});
      })
    );
  }

  KakaoIn(): void{
    this.kakaoService.getKakaoAuthUrl().subscribe((url: String) =>{
      window.location.href = JSON.stringify(url);
    });
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params =>{
      this.redirectTo = params['redirectTo']
    });
  }

  submit(){
    if (this.signInForm.valid){
      this.signService.signIn(this.signInForm.value.id, this.signInForm.value.password)
        .then(data => {
          this.router.navigate([this.redirectTo ? this.redirectTo : '/']);
        });
    }
  }
}
