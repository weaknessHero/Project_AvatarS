import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {Item} from "../items/item.model";
import {ItemService} from "../../item.service";

@Component({
  selector: 'app-overlay',
  templateUrl: './overlay.component.html',
  styleUrls: ['./overlay.component.css']
})
export class OverlayComponent implements OnInit {
  visible = false;
  isSearchVisible: boolean = false;
  searchInput: string;
  searchResults: Item[] = [];
  @ViewChild('overlay') overlayDiv: ElementRef;

  constructor(private itemService: ItemService,
              private renderer: Renderer2) {}

  ngOnInit(): void {}

  showOverlay() {
    this.visible = true;
    this.overlayDiv.nativeElement.style.display = 'block';
  }

  hideOverlay() {
    this.visible = false;
    this.overlayDiv.nativeElement.style.display = 'none';
  }

  showSearch() {
    this.isSearchVisible = true;
  }

  hideSearch() {
    this.isSearchVisible = false;
  }

  /*performSearch(query: string) {
    this.searchResults = this.itemService.searchItems(query);
  }*/

  toggleSearch(){
    console.log('toggle called');
    this.isSearchVisible = !this.isSearchVisible;
  }

  // 새로운 메서드 추가하기
  onClickOverlay(event: MouseEvent) {
    if (event.target === this.overlayDiv.nativeElement) {
      this.hideOverlay();
    }
  }
}
