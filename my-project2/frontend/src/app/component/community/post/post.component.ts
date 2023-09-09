import { Component } from '@angular/core';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent {

  post = {
    title: '',
    gender: '',
    style: '',
    imageFiles: [],
  };

  onImageChange(event){
    if(event.target.files && event.target.files.length>0){
      for(let file of event.target.files){
        let reader = new FileReader();
        reader.onload = (e:any) => {
          this.post.imageFiles.push({file, url:e.target.result});
        }
        reader.readAsDataURL(file);
      }
    }
  }

  removeImage(index:number){
    this.post.imageFiles.splice(index, 1);
  }

  onSubmit(){
    console.log(this.post);
  }
}
