import { Component, ElementRef, ViewChild } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ProductService} from "../../../service/product.service";
import {ProductDTO} from "../../../model/product-dto.model";

@Component({
  selector: 'app-fit-main',
  templateUrl: './fit-main.component.html',
  styleUrls: ['./fit-main.component.css']
})
export class FitMainComponent {

  imagePath: string = '/assets/avatar_woman.png';
  selectedTopColor: string;
  selectedTopCategory: string;
  selectedBottomColor: string;
  selectedBottomCategory: string;
  imageFile: File;
  labels: any[];

  constructor(private http: HttpClient,
              private productService: ProductService) { }

  overlayImg: HTMLImageElement = new Image();
  @ViewChild('imageElement') imageElement: ElementRef;
  @ViewChild('previewImage', { static: false }) previewImage: ElementRef;
  @ViewChild('overlayImage') overlayImage: ElementRef;
  @ViewChild('canvas', { static: false }) canvas: ElementRef;

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

  /*handleCategoryChange(event: any) {
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
  }*/

  openImageInput() {
    const imageInput = document.getElementById('image-input') as HTMLInputElement;
    imageInput.click();
  }

  /*handleColorChange(event: any){
    if(this.selectedColor==='top_color1'){
      //빨간 색 선택 시
      this.previewImage.nativeElement.style.backgroundImage = 'url("/assets/red_tshirt.png")';
    } else if(this.selectedColor==='top_color2'){
      //파란색 선택 시
      this.previewImage.nativeElement.style.backgroundImage = 'url("/assets/blue_tshirt.png")';
    } else if(this.selectedColor==='top_color3'){
      //초록색 선택 시
      this.previewImage.nativeElement.style.backgroundImage = 'url("/assets/green_tshirt.png")';
    } else if(this.selectedColor==='top_color4'){
      //검정색 선택 시
      this.previewImage.nativeElement.style.backgroundImage = 'url("/assets/black_tshirt.png")';
    } else if(this.selectedColor==='top_color5'){
      //흰색 선택 시
      this.previewImage.nativeElement.style.backgroundImage = 'url("/assets/white_tshirt.png")';
    }
  }*/

  removePreviewImage(){
    const previewImage = this.previewImage.nativeElement;
    previewImage.src = '';
    previewImage.style.display = 'none';
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

  downloadImage(): void {
    const avatarImage = document.getElementById('avatar-image') as HTMLImageElement;
    const overlayImage = document.getElementById('overlay-image') as HTMLImageElement;

    avatarImage.onload = () => {
      const canvas = document.createElement('canvas');
      const context = canvas.getContext('2d');

      canvas.width = avatarImage.width;
      canvas.height = avatarImage.height;

      // 아바타 이미지 그리기
      context.drawImage(avatarImage, 0, 0);

      // 오버레이 이미지 그리기
      context.drawImage(overlayImage, 0, 0);

      // 캔버스 데이터를 이미지 URL로 변환하여 다운로드
      const dataURL = canvas.toDataURL();
      const link = document.createElement('a');
      link.href = dataURL;
      link.download = 'merged_image.png';

      // 다운로드 트리거
      window.location.href = link.href;
    };
  }


  handleTopCategoryChange(event: any) {
    // 선택된 상의 종류 변경 처리
    this.selectedTopCategory = event.target.value;
    // 추가적인 로직 수행
  }

  handleTopColorChange(event: any) {
    // 선택된 상의 색상 변경 처리
    this.selectedTopColor = event.target.value;
    // 추가적인 로직 수행
  }

  handleBottomCategoryChange(event: any) {
    // 선택된 하의 종류 변경 처리
    this.selectedBottomCategory = event.target.value;
    // 추가적인 로직 수행
  }

  handleBottomColorChange(event: any) {
    // 선택된 하의 색상 변경 처리
    this.selectedBottomColor = event.target.value;
    // 추가적인 로직 수행
  }

  handleShoesCategoryChange(event: any) {
    // 선택된 신발 종류 변경 처리
    // 추가적인 로직 수행
  }

  handleSexCategoryChange(event: any) {
    // 선택된 성별 변경 처리
    // 추가적인 로직 수행
  }

  onFileChange(event: any) {
    this.imageFile = event.target.files[0];
  }

  detectLabels() {
    const formData = new FormData();
    formData.append('image', this.imageFile);

    this.http.post<any[]>('http://localhost:8080/api/image', formData)
      .subscribe(
        response => {
          this.labels = response.map(entity => {
            return {
              description: entity['google.cloud.vision.v1.EntityAnnotation.description'],
              score: entity['google.cloud.vision.v1.EntityAnnotation.score']
            };
          });
        },
        error => {
          console.log(error);
        }
      );
  }

  productResults: ProductDTO[] = [];

  searchProducts(query: string): void {
    this.productService.searchProducts(query).subscribe((results) => {
      this.productResults = results;
    });
  }

}
