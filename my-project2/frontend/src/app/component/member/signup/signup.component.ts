import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SignService} from "../../../service/rest-api/sign.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  signUpForm: FormGroup;

  constructor(
    /*private router: Router,
    private formBuilder: FormBuilder,
    private signService: SignService*/
    private http: HttpClient
  ) {
    this.signUpForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      uid: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    })

    /*this.signUpForm = this.formBuilder.group({
      id: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
      ])),
      password: new FormControl('', [Validators.required]),
      password_re: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required])
    }, {validator: this.checkPassword});*/
  }
  onSubmit(){
    const user = this.signUpForm.value;
    this.http.post('api/users/signup', user).subscribe(
      response => {
        console.log(response);
      },
      error =>{
        console.log(error);
      }
    );
  }

  /*checkPassword(group:FormGroup){
    let password = group.controls.password.value;
    let passwordRe = group.controls.password_re.value;
    return password === '' || passwordRe === '' || password === passwordRe ? null : { notSame : true }
  }

  get f() {
    return this.signUpForm.controls;
  }

  submit(){
    if(this.signUpForm.valid){
      this.signService.signUp(this.signUpForm.value.id, this.signUpForm.value.password, this.signUpForm.value.name)
        .then(response => {
          this.signService.signIn(this.signUpForm.value.id, this.signUpForm.value.password)
            .then(response =>{
              this.router.navigate(['/']);
            });
        });
    }
  }*/
}
