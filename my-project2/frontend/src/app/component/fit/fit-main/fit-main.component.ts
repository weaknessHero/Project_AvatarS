import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-fit-main',
  templateUrl: './fit-main.component.html',
  styleUrls: ['./fit-main.component.css']
})
export class FitMainComponent {

  imagePath: string;
  selectedColor: string;
  @ViewChild('imageElement') imageElement: ElementRef;

  zoomIn($event: MouseEvent){
    const target = $event.currentTarget as HTMLElement;
    target.classList.toggle('zoomed-in');
  }

  handleManImage(){
    /*if (this.imageElement && this.imageElement.nativeElement) {
      this.imageElement.nativeElement.remove();
    }*/
    this.imagePath = '/assets/avatar_man.png'
  }

  handleWomanImage(){
    /*if (this.imageElement && this.imageElement.nativeElement) {
      this.imageElement.nativeElement.remove();
    }*/
    this.imagePath = '/assets/avatar_woman.png'
  }

  handleCategoryChange(event: any) {
    const selectedCategory = event.target.value;
    // 선택된 카테고리를 처리하는 로직을 구현합니다.
    console.log('선택된 카테고리:', selectedCategory);
    // 추가적인 작업을 수행합니다.

    if (this.selectedColor === 'top_color1') {
      document.querySelector('select').style.color = 'red';
    } else if (this.selectedColor === 'top_color2') {
      document.querySelector('select').style.color = 'blue';
    } else if (this.selectedColor === 'top_color3') {
      document.querySelector('select').style.color = 'green';
    } else if (this.selectedColor === 'top_color4') {
      document.querySelector('select').style.color = 'black';
    } else if (this.selectedColor === 'top_color5') {
      document.querySelector('select').style.color = 'grey';
    }
  }

  openImageInput() {
    const imageInput = document.getElementById('image-input') as HTMLInputElement;
    imageInput.click();
  }

  handleImageChange(event: any) {
    if (this.imageElement && this.imageElement.nativeElement) {
      this.imageElement.nativeElement.remove();
    }
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
