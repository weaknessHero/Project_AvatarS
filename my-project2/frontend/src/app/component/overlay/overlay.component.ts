import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-overlay',
  templateUrl: './overlay.component.html',
  styleUrls: ['./overlay.component.css']
})
export class OverlayComponent implements OnInit {
  visible = false;
  @ViewChild('overlay') overlayDiv: ElementRef;

  constructor() {}

  ngOnInit(): void {}

  showOverlay() {
    this.visible = true;
    this.overlayDiv.nativeElement.style.display = 'block';
  }

  hideOverlay() {
    this.visible = false;
    this.overlayDiv.nativeElement.style.display = 'none';
  }
}
