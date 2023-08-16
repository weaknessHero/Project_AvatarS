import { Component } from '@angular/core';
import { SignService } from "./service/rest-api/sign.service";
import {DataService} from "./service/data.service";
import {HomeComponent} from "./component/home.component";

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
  constructor(
    public signService: SignService,
    //private dataService: DataService
  ) {}

  updateIsHomeComponent(event: any): void{
    this.isHomeComponent = event instanceof HomeComponent;
  }

  onActivate(event: any): void {
    this.isHomeComponent = event instanceof HomeComponent;
  }

  onDeactivate(event: any): void {
    this.isHomeComponent = false;
  }


  /*ngOnInit(){
    this.dataService.getData().subscribe((data) =>(this.data = data));
  }*/

}
