import {Component, OnInit} from '@angular/core';
import {ClosetService} from "../../../service/closet.service";
import {forkJoin} from "rxjs";
import {ProductService} from "../../../service/product.service";
import {ClosetResponse} from "src/app/component/member/closet/closet-response.model";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-closet',
  templateUrl: './closet.component.html',
  styleUrls: ['./closet.component.css']
})
export class ClosetComponent implements OnInit{

  closetItems = [];
  posts = [];

  constructor(private closetService: ClosetService,
              private productService: ProductService,
              private http: HttpClient) {}

  ngOnInit() {
    this.loadClosetItems();

    const username = localStorage.getItem('username');
    this.http.get(`http://localhost:8080/posts/${username}`)
      .subscribe(data =>{
        this.posts = data as any[];
      });
  }

  removeItem(itemId: string) {
    this.closetService.removeFromCloset(itemId).subscribe(
      () => {
        // 성공적으로 제거된 후 옷장 아이템 리스트 갱신
        this.loadClosetItems();
      },
      error => console.error(error)
    );
  }

  loadClosetItems() {
    const username = localStorage.getItem('username');
    if(username){
      this.closetService.getCloset(username).subscribe(
        response => {
          const requests = response.productIds.map(productId =>
            this.productService.getProduct(productId) // getProduct 메서드가 /api/products/{id} API를 호출한다고 가정합니다.
          );

          forkJoin(requests).subscribe(
            products => this.closetItems = products,
            error => console.error(error)
          );
        },
        error => console.error(error)
      );
    }
  }
}
