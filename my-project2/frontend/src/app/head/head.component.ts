import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SearchService} from "../component/search.service";
import {Router} from "@angular/router";
@Component({
  selector: 'app-head',
  templateUrl: './head.component.html',
  styleUrls: ['./head.component.css']
})
export class HeadComponent {

  form: FormGroup = new FormGroup({});

  constructor(private fb: FormBuilder,
              private router: Router,
              private searchService: SearchService) {
    this.form = this.fb.group({
      searchValue: ["",Validators.required]
    });
  }

  onSearch() {
    const searchValueForm = this.form.get('searchValue')?.value;
    if (searchValueForm) {
      this.searchService.searchItems(searchValueForm);
      this.router.navigate(['/search', searchValueForm]);
    }
  }

  get f(){
    return this.form.controls;
  }

  submit(){
    console.log(this.form.value);
  }
}
