import {Component, Inject, NgZone} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent {
  constructor(
    public dialogRef: MatDialogRef<ResultComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {generatedImage: string},
    private ngZone: NgZone,
    private http: HttpClient
  ) {}

  async downloadImage() {
    const timeStampText = this.getTimeStampText();
    const imageName = `AvatarS_${timeStampText}.jpg`;
    const link = document.createElement('a');
    const imageUrl = 'assets/test/result.jpg'; // 임시 이미지 사용

    this.ngZone.runOutsideAngular(async () => {
      const blob = await this.imageToBlob(imageUrl);

      link.href = URL.createObjectURL(blob);
      link.download = imageName;
      link.click();
      link.remove();
    });
  }

  private getTimeStampText(): string {
    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    const day = now.getDate().toString().padStart(2, '0');
    const hour = now.getHours().toString().padStart(2, '0');
    const minute = now.getMinutes().toString().padStart(2, '0');
    const second = now.getSeconds().toString().padStart(2, '0');

    return `${year}${month}${day}_${hour}${minute}${second}`;
  }

  private async imageToBlob(imageUrl: string): Promise<Blob> {
    const response = await this.http.get(imageUrl, { responseType: 'blob' }).toPromise();

    return new Blob([response], { type: response.type });
  }
}
