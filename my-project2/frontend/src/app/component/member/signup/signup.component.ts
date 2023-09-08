import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SignService} from "../../../service/rest-api/sign.service";
import {HttpClient} from "@angular/common/http";
import {Location} from "@angular/common";
import {BehaviorSubject} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  signUpForm: FormGroup; // 회원가입 폼 정보를 관리하는 FormGroup 인스턴스
  private _isSignedIn = new BehaviorSubject<boolean>(false);
  isSignedIn$ = this._isSignedIn.asObservable();

  constructor(
    private signService: SignService,
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router,
    private snackBar: MatSnackBar) {
    this.signUpForm = this.formBuilder.group({
      name: '', // 이름 입력 필드
      uid: '', // 아이디(Email) 입력 필드
      password: '', // 비밀번호 입력 필드
      password_re: '' // 비밀번호 확인 입력 필드
    }, {validator: this.checkPasswords}); // 비밀번호 일치 여부 검사 Validator 추가
  }

  get f() {
    return this.signUpForm.controls;
  }

  checkPasswords(group: FormGroup) {
    let pass = group.get('password').value;
    let confirmPass = group.get('password_re').value;

    return pass === confirmPass ? null : {notSame: true}
  }

  submit() {
    const signUpData = this.signUpForm.value;
    if (this.signUpForm.valid && !this.signUpForm.hasError('notSame')) {
      this.signService.signup(signUpData).subscribe(
        (response) => {
          console.log(response); // Signup successful
          this.router.navigate(['/']);
          this.snackBar.open('회원가입에 성공했습니다!', '', {
            duration: 2000, // Duration of the message in milliseconds
            horizontalPosition: 'center', // Horizontal position of the message on the screen
            verticalPosition: 'top' // Vertical position of the message on the screen
          });
        },
        (error) => {
          console.error(error); // Error handling
        }
      );
    }
  }

}
