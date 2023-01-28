import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userDetails: any;
  responseMessageType = '';
  responseMessage = '';


  constructor(private authService: AuthService) {
  }

  showMessage(type, msg) {
    this.responseMessageType = type;
    this.responseMessage = msg;
    setTimeout(() => {
      this.responseMessage = '';
    }, 2000);
  }

  isUserLogin() {
    this.userDetails = this.authService.isUserLoggedIn();
  }

  logoutUser() {
    this.authService.logout()
      .then(res => {
        console.log(res);
        this.userDetails = undefined;
        localStorage.removeItem('user');
      }, err => {
        this.showMessage('danger', err.message);
      });
  }

  googleLogin() {
    this.authService.loginWithGoogle()
      .then(res => {
        console.log(res);
        this.isUserLogin();
      }, err => {
        this.showMessage('danger', err.message);
      });
  }

  ngOnInit() {
  }

}
