import { Component } from '@angular/core';
import { ProductService} from "../../../service/product.service";

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css']
})
export class ManageComponent {
  product = {
    name: '',
    category: '',
    brand: '',
    price: 0,
    imageUrl: '',
    buyUrl: '',
    tags: [],
  };

  tagInput = "";

  successMessage = '';
  errorMessage = '';

  constructor(private productService: ProductService) {}

  submitForm() {

    this.product.tags = this.tagInput.split(',');

    this.productService.createProduct(this.product).subscribe(
      (response) => {
        this.successMessage = 'Product created successfully.';
        this.errorMessage = '';
        // 폼 초기화
        this.product = {
          name: '',
          category: '',
          brand: '',
          price: 0,
          imageUrl:'',
          buyUrl:'',
          tags:[]
        };
      },
      (error) => {
        this.successMessage = '';
        this.errorMessage = 'Failed to create product. Please try again.';
      }
    );
  }
}
