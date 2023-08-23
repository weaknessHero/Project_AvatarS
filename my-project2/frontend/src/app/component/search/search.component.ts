import {Component, OnInit} from '@angular/core';
import {Item} from "../items/item.model";
import {SearchService} from "../search.service";
import {ActivatedRoute} from "@angular/router";
import {ItemService} from "../../item.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent{


  isSearchVisible: boolean = true;
  searchResults: Item[] = [];
  searchInput: string;

  categories = ['','아우터', '반팔', '니트'];
  selectedCategory: string;

  constructor(private searchService: SearchService,
              private route: ActivatedRoute,
              private itemService: ItemService) {
  }

  performSearch(query: string) {
    this.searchResults = this.itemService.searchItems(query)
      .filter(item => !this.selectedCategory || item.category === this.selectedCategory);
  }
  toggleSearch(): void {
    this.searchResults = []; // 검색 결과 초기화
  }



}
