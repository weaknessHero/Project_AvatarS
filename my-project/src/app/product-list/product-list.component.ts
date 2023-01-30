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
    image: '../../assets/img/1.png',
    pid: '1',
    pname: '스프링 울 2-WAY 헤링턴 자켓',
    price: 111585,
    rating: 3.3,
    brand: '키뮤어',
    type: '아우터',
    style: ['캐주얼', '아메카지']
  },{
    image: '../../assets/img/2.png',
    pid: '2',
    pname: '어센틱 로고 후디',
    price: 68671,
    rating: 4.3,
    brand: '커버낫',
    type: '상의',
    style: ['캐주얼']
  },{
    image: '../../assets/img/3.png',
    pid: '3',
    pname: '리버시블 슬랙스',
    price: 32000,
    rating: 2.7,
    brand: '후러브스아트',
    type: '바지',
    style: ['아메카지']
  },{
    image: '../../assets/img/4.png',
    pid: '4',
    pname: '로고 패치 캐시미어 머플러',
    price: 898000,
    rating: 3.7,
    brand: '질 샌더',
    type: '악세사리',
    style: ['럭셔리']
  },{
    image: '../../assets/img/5.png',
    pid: '5',
    pname: '4.5cm 소가죽 코만도 첼시부츠 루틴(CR0011)',
    price: 86800,
    rating: 3.4,
    brand: 'CUSTOMADE',
    type: '악세사리',
    style: ['신발']
  }];
  ngOnInit(): void {
  }
}


export class Product{
  image: any;
  pid: string;
  pname: string;
  price: number;
  rating: number;
  brand: string;
  type: string;
  style: string[];
}
