import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiReponseSingle} from "../../model/common/ApiReponseSingle";
import {ApiValidationService} from "./common/api-validation.service";

@Injectable({
  providedIn: 'root'
})
export class SignService {

  private signInUrl = '/api/user/signin';
  private signUpUrl = '/api/user/signup';

  constructor(private http: HttpClient,
              private apiValidationService: ApiValidationService) {}

  signIn(uid: string, password: string): Promise<any>{
    const params = new FormData();
    params.append('uid', uid);
    params.append('password', password);
    return this.http.post<ApiReponseSingle>(this.signInUrl, params)
      .toPromise()
      .then(this.apiValidationService.validateResponse)
      .then(response =>{
        localStorage.setItem('x-auth-token', response.data);
      })
      .catch(response =>{
        alert('[로그인 실패]\n' + response.error.msg);
        return Promise.reject(response.error.msg);
      });
  }
  signUp(uid: string, password: string, name: string): Promise<any>{

    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    const params = new FormData();
    params.append('uid', uid);
    params.append('password',password);
    params.append('name', name);
    return this.http.post<ApiReponseSingle>(
      this.signUpUrl,params
      /*JSON.stringify(params)*/, {headers: headers})
      .toPromise()
      .then(this.apiValidationService.validateResponse)
      .then(response =>{
        //response.json();
        return true;
      })
      .catch(response =>{
        alert('[가입 실패]\n'+response.error.msg);
        return Promise.reject(response.error.msg);
      })
  }

  //로그인 여부 확인
  isSignIn(): boolean{
    const token = localStorage.getItem('x-auth-token');
    if (token){
      return true;
    } else {
      return false;
    }
  }
}
