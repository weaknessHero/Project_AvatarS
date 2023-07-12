import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-image-search',
  templateUrl: './image-search.component.html',
  styleUrls: ['./image-search.component.css']
})
export class ImageSearchComponent {

  imageFile: File;
  objectAnnotations: any[];

  constructor(private http: HttpClient) { }

  onFileChange(event: any) {
    this.imageFile = event.target.files[0];
  }

  detectObjects() {
    const formData = new FormData();
    formData.append('image', this.imageFile);

    this.http.post<any[]>('http://localhost:8080/api/image', formData)
      .subscribe(
        response => {
          this.objectAnnotations = response;
        },
        error => {
          console.log(error);
        }
      );
  }
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
