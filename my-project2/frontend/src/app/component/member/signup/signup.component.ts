import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  signUpForm: FormGroup;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.signUpForm = this.formBuilder.group({
    })
  }

  submit(){

  }
}
