import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})


export class ProductListComponent implements OnInit{

  constructor(private http: HttpClient) {}

  products: Product[] = [{
    pid: '1',
    pname: '헤링본 자켓',
    price: 30000,
    rating: 3.3,
    brand: '라퍼지스토어',
    style: ['캐주얼', '아메카지']
  },{
    pid: '2',
    pname: '블루 바시티',
    price: 50000,
    rating: 4.3,
    brand: 'EURL',
    style: ['캐주얼', '시티보이']
  }];
  ngOnInit(): void {
  }
}


export class Product{
  pid: string;
  pname: string;
  price: number;
  rating: number;
  brand: string;
  style: string[];
}
