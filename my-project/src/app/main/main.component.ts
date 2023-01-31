import {Component, OnInit} from '@angular/core';
import { Main } from '../main';
import {NAVIGATION} from "../category";
@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{

  main: Main = {
    id: 1,
    name: 'Main Page'
  };

  categories = NAVIGATION;

  constructor() {
  }

  ngOnInit(): void {
  }

}
