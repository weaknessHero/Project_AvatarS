import { Component } from '@angular/core';
import { HttpClient  } from '@angular/common/http';

@Component({
  selector: 'app-fitting',
  templateUrl: './fitting.component.html',
  styleUrls: ['./fitting.component.css']
})
export class FittingComponent {

  localserver: string = '';
  imagePath: string = '/assets/avatar_woman.png';
  modelInput;
  clothesInput;
  modelImg;
  clothesImg;
  modelPreview;
  clothesPreview;

  constructor(private http: HttpClient) { }

  handleManImage(){
    /*if (this.imageElement && this.imageElement.nativeElement) {
      this.imageElement.nativeElement.remove();
    }*/
    this.imagePath = '/assets/avatar_man.png'
  }

  //this[imageId]  저건 this의 imageId 외부에서 임력한것과 동일한 변수명에다가 저장하라는뜻
  imageInput(imageId:string) {
    this[imageId] = document.getElementById(imageId) as HTMLInputElement;

    this[imageId].click();
  }

  handleImageChange(event: Event, previewId: string, imgId:string) {
    const fileInput = (event.target as HTMLInputElement);
    if (fileInput.files && fileInput.files.length > 0) {
      this[imgId] = fileInput.files[0];
      var file = fileInput.files[0];
    }

    this[previewId] = document.getElementById(previewId) as HTMLImageElement;
    if (fileInput.files && fileInput.files.length > 0) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this[previewId].src = e.target.result as string;
        this[previewId].style.display = 'block';
      }
      reader.readAsDataURL(file);
    } else {
      this[previewId].src = '';
      this[previewId].style.display = 'none';
    }
  }

  sendImageInput(){
    if(this.modelImg&&this.clothesImg){
      const formData: FormData = new FormData();
      formData.append('modelImage', this.modelImg);
      formData.append('clothesImage', this.clothesImg);
      this.http.post('/fitting/upload', formData).subscribe((event) => {
        console.error("aa");
        console.error(formData);
      });
    }
  }

}
