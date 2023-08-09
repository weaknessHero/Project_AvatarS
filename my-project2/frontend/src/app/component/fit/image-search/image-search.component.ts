import { Component } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { ObjectAnnotation } from "../../../model/object-annotation.model";

@Component({
  selector: 'app-image-search',
  templateUrl: './image-search.component.html',
  styleUrls: ['./image-search.component.css']
})
export class ImageSearchComponent {

  imageFile: File;     // 이미지 파일 저장할 변수
  objectAnnotations: ObjectAnnotation[];   // 객체 주석(바운딩 박스 등) 저장할 변수
  imageUrl: string;
  colors: string[] = ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF', '#00FFFF'];
  constructor(private http: HttpClient) { }

  // 파일 변경 이벤트에 대한 처리
  /*onFileChange(event: any) {
    this.imageFile = event.target.files[0];
  }*/

  onFileChange(event: any) {
    this.imageFile = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.imageUrl = e.target.result;
    };
    reader.readAsDataURL(this.imageFile);
  }

  // 이미지 업로드 전 이미지를 나타내는 이벤트 핸들러
  openImageInput() {
    const imageInput = document.getElementById('image-input') as HTMLInputElement;
    imageInput.click();
  }

  // 이미지를 변경할 때 발생하는 이벤트 처리
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

  //가장 일반적인 색상을 가져오는 함수
  private getMostCommonColor(imageData: ImageData) {
    const colors = new Map<string, number>();
    const pixelData = imageData.data;
    for (let i = 0; i < pixelData.length; i += 4) {
      const rgb = [pixelData[i], pixelData[i + 1], pixelData[i + 2]];
      const hex = rgb.map(c => c.toString(16).padStart(2, '0')).join('');
      colors.set(hex, (colors.get(hex) || 0) + 1);
    }
    const mostCommonColor = [...colors.entries()].reduce((a, b) => (a[1] > b[1] ? a : b))[0];
    return mostCommonColor;
  }

  //서버에서 이미지를 처리한 결과값을 가져와 주석 처리되 객체를 화면에 표시함
  /*detectObjects() {
    const formData = new FormData();
    formData.append('image', this.imageFile);
    this.http.post<any[]>('http://localhost:8080/api/image', formData)
      .subscribe(
        response => {
          this.objectAnnotations = response;
          // 검출된 객체들의 바운딩 박스에 대해 색상 추출 수행
          setTimeout(() => {
            this.extractMostCommonColorsFromBoundingBoxes();
          }, 0);
        },
        error => {
          console.log(error);
        }
      );
  }*/

  detectObjects() {
    const formData = new FormData();
    formData.append('image', this.imageFile);
    this.http.post<ObjectAnnotation[]>('http://localhost:8080/api/image', formData)
      .subscribe(
        response => {
          console.log('Response', response);
          this.objectAnnotations = response;
        },
        error => {
          console.log(error);
        }
      );
  }

  getColor(index: number): string{
    return this.colors[index % this.colors.length];
  }

  // 바운딩 박스에서 이미지의 가장 일반적인 색상을 추출
  /*private extractMostCommonColorsFromBoundingBoxes() {
    const boundingBoxes = document.querySelectorAll('.bounding-box');

    boundingBoxes.forEach(boundingBox => {
      const canvas = document.createElement('canvas');
      canvas.width = boundingBox.clientWidth;
      canvas.height = boundingBox.clientHeight;
      const ctx = canvas.getContext('2d');
      const img = boundingBox.querySelector('img');
      ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
      const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
      const mostCommonColor = this.getMostCommonColor(imageData);
      const colorDiv = document.createElement('div');
      colorDiv.style.backgroundColor = '#' + mostCommonColor;
      colorDiv.style.width = '20px';
      colorDiv.style.height = '20px';
      colorDiv.style.margin = '5px';
      boundingBox.appendChild(colorDiv);
    });
  }*/

}



/*detectObjects() {
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
}*/
