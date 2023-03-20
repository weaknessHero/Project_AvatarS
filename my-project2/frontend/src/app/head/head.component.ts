import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
@Component({
  selector: 'app-head',
  templateUrl: './head.component.html',
  styleUrls: ['./head.component.css']
})
export class HeadComponent {

  form: FormGroup = new FormGroup({});

  constructor(private fb: FormBuilder) {
  }

  get f(){
    return this.form.controls;
  }

  submit(){
    console.log(this.form.value);
  }
}
