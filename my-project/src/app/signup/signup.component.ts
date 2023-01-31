import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{

  constructor(private http: HttpClient) {
  }
  ngOnInit(): void{
  }

  onSignUp(signupForm:NgForm){
    this.http.post('/api/post/signup', signupForm.value).subscribe((res:any)=>{

    },
    err => {
      console.log(err);
      alert('에러');
    });
  }
}
