import { Component } from '@angular/core';
import { SignService } from "./service/rest-api/sign.service";
import {DataService} from "./service/data.service";
import {HomeComponent} from "./component/home.component";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  //template: '{{ data }}'
})
export class AppComponent {
  title = 'frontend';
  data: any;
  isHomeComponent = false;

  loginForm: FormGroup; //form 인스턴스로 로그인 폼 정보 관리

  constructor(
    public signService: SignService,
    private formBuilder: FormBuilder
  ) {
    this.loginForm = this.formBuilder.group({
      uid: "",
      password: ""
    });
  }


  updateIsHomeComponent(event: any): void{
    this.isHomeComponent = event instanceof HomeComponent;
  }

  onActivate(event: any): void {
    this.isHomeComponent = event instanceof HomeComponent;
  }

  onDeactivate(event: any): void {
    this.isHomeComponent = false;
  }

  /*onLogout(){
    this.signService.logout().subscribe(
      (response) => {
        console.log(response);
      },
      (error)=>{
        console.error(error);
      }
    );
  }*/

  onLogin(){
    const loginData = this.loginForm.value;

    this.signService.login(loginData).subscribe(
      (response) => {
        console.log(response);
      },
      (error)=>{
        console.error(error);
      }
    );
  }

  /*ngOnInit(){
    this.dataService.getData().subscribe((data) =>(this.data = data));
  }*/

}
