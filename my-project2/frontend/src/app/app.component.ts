import { Component } from '@angular/core';
import { SignService } from "./service/rest-api/sign.service";
import {DataService} from "./service/data.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  //template: '{{ data }}'
})
export class AppComponent {
  title = 'frontend';
  data: any;
  constructor(
    public signService: SignService,
    //private dataService: DataService
  ) {}

  /*ngOnInit(){
    this.dataService.getData().subscribe((data) =>(this.data = data));
  }*/

}
