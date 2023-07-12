import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-detect-labels',
  templateUrl: './detect-labels.component.html',
  styleUrls: ['./detect-labels.component.css']
})
export class DetectLabelsComponent {
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
}
