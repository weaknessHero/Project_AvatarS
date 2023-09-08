import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiValidationService} from "./common/api-validation.service";
import {User} from "../../model/myinfo/User";
import {ApiReponseSingle} from "../../model/common/ApiReponseSingle";

@Injectable({
  providedIn: 'root'
})
export class MyinfoService {
  constructor(
    private http: HttpClient,
    private apiValidationService: ApiValidationService
  ) { }

  private getUserUrl = '/api/v1/user';

  getUser(): Promise<User>{
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if(loginUser == null){
      return this.http.get<ApiReponseSingle>(this.getUserUrl)
        .toPromise()
        .then(this.apiValidationService.validateResponse)
        .then(response => {
          localStorage.setItem('loginUser', JSON.stringify(response.data));
          return response.data as User;
        })
        .catch(response => {
          localStorage.removeItem('x-auth-token');
          alert('[회원 정보 조회 중 오류 발생]\n' + response.error.msg);
          return Promise.reject(response.error.msg);
        });
    }else {
      return Promise.resolve(loginUser);
    }
  }
}
