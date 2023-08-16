import { Injectable } from '@angular/core';
import { Item } from '../app/component/items/item.model';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private items: Item[] = [
    new Item(1, '남성 나슬란 와트로 다운 패딩 재킷' ,'스톤아일랜드', '../../../assets/items/1.jpg',
      'https://www.musinsa.com/app/goods/3261311?loc=goods_rank', ['패딩', '검정색', '재킷', '자켓', '남자', '스톤아일랜드'])
  ];

  constructor() { }

  searchItems(searchInput: string): Item[] {
    return this.items.filter(item => {
      return item.tags.some(tag => tag.toLowerCase().includes(searchInput.toLowerCase()));
    });
  }
}
