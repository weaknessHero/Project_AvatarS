import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.css']
})
export class CommunityComponent {

  constructor(private router: Router) {
  }

  navigateToPost(): void {
    this.router.navigate(['/post']);
  }
}
