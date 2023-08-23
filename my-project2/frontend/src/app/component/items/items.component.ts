import { Component } from '@angular/core';
import {Item} from "./item.model";

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent {
  items: Item[] = [
    new Item(1, '남성 나슬란 와트로 다운 패딩 재킷1','스톤 아일랜드', '아우터',  790000, '../../../assets/items/1.jpg', 'https://www.musinsa.com/app/goods/3261311?loc=goods_rank', ['패딩', '검정색', '재킷', '자켓', '남자', '스톤아일랜드']),
  ];
}
