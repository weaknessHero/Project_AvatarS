import { Component } from '@angular/core';

@Component({
  selector: 'app-fit-main',
  templateUrl: './fit-main.component.html',
  styleUrls: ['./fit-main.component.css']
})
export class FitMainComponent {

  openImageInput() {
    const imageInput = document.getElementById('image-input') as HTMLInputElement;
    imageInput.click();
  }

  handleImageChange(event: Event) {
    const file = (event.target as HTMLInputElement).files[0];
    const previewImage = document.getElementById('preview-image') as HTMLImageElement;

    if (file) {
      const reader = new FileReader();
      
      reader.onload = function(e) {
        previewImage.src = e.target.result as string;
        previewImage.style.display = 'block';
      }
      
      reader.readAsDataURL(file);
    } else {
      previewImage.src = '';
      previewImage.style.display = 'none';
    }
  }
}