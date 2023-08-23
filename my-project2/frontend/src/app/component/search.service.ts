import { Injectable } from '@angular/core';
import { Item} from "./items/item.model";
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private items: Item[] = [ /* ... */ ];
  currentSearch = new Subject<Item[]>(); // Observable to hold search results

  constructor() { }

  searchItems(searchInput: string): void {
    const results = this.items.filter(item => {
      return item.tags.some(tag => tag.toLowerCase().includes(searchInput.toLowerCase()));
    });
    this.currentSearch.next(results); // Push the results into the observable
  }
}
