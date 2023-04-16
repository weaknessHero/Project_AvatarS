import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class KakaoService {

  private readonly API_URL = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  getKakaoAuthUrl(){
    return this.http.get('${this.API_URL}/auth/kakao');
  }
  /*getKakaoAuthUrl(): Observable<any> {
    const url = 'https://kauth.kakao.com/oauth/authorize' +
      '?client_id=4985bb6e1259e1eea63d88a7decc596b' +
      '&redirect_uri=http://localhost:8080/signin/oauth_kakao' +
      '&response_type=code';
    return this.http.get<any>(url);
  }*/

  oauthKakao(code: String){
    return this.http.post('{this.API_URL}/auth/kakao/callback', { code });
  }

}
