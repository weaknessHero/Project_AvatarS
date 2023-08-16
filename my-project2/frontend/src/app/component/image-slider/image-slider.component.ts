import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-image-slider',
  templateUrl: './image-slider.component.html',
  styleUrls: ['./image-slider.component.css']
})
export class ImageSliderComponent implements OnInit{
  images: string[] = [
    'assets/sliderImage/image1.jpg',
    'assets/sliderImage/image2.jpg',
    'assets/sliderImage/image3.jpg',
    'assets/sliderImage/image4.jpg'];
  currentImageIndex: number = 0;

  constructor() {
  }

  ngOnInit():void {
    setInterval(() =>{
      this.currentImageIndex = (this.currentImageIndex + 1) % this.images.length;
    }, 1000);
  }

}
