import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent {

  post = {
    username: '',
    title: '',
    gender: '',
    style: '',
    body: '',
    imageFiles: [],
    //postIds: []
  };

  constructor(private http: HttpClient) {
    this.post.username = localStorage.getItem('username');
  }

  onSubmit(){
    const formData = new FormData();
    formData.append('title', this.post.title);
    formData.append('gender', this.post.gender);
    formData.append('style', this.post.style);
    formData.append('username', this.post.username);
    formData.append('body', this.post.body);
    /*for(let postId of this.post.postIds){
      formData.append('postIds', postId);
    }*/
    for(let imageFile of this.post.imageFiles){
      formData.append('imageFiles', imageFile.file);
    }

    this.http.post('http://localhost:8080/posts', formData)
      .subscribe(
        response => {
          console.log('게시물이 등록되었습니다.');
        },
        error => {
          console.error('게시물 등록 실패', error);
        }
      );
  }

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

}
