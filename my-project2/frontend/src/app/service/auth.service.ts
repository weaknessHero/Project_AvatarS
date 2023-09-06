import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _isSignedIn: BehaviorSubject<boolean>;
  private _username: BehaviorSubject<string>;

  constructor() {
    this._isSignedIn = new BehaviorSubject<boolean>(false);
    this._username = new BehaviorSubject<string>('');
  }

  get isSignedIn(): Observable<boolean> {
    return this._isSignedIn.asObservable();
  }

  get username(): Observable<string> {
    return this._username.asObservable();
  }

  login(username: string, password: string): void {
    // 로그인 로직을 구현하고, 성공 시에 아래 코드 실행

    // 사용자 이름 업데이트
    this._username.next(username);
    // 로그인 상태 업데이트
    this._isSignedIn.next(true);
  }

  logout(): void {
    // 로그아웃 로직을 구현하고, 성공 시에 아래 코드 실행

    // 사용자 이름 초기화
    this._username.next('');
    // 로그인 상태 업데이트
    this._isSignedIn.next(false);
  }
}
