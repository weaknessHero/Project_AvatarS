import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductDTO } from '../model/product-dto.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly apiUrl = 'http://localhost:8080/api/products/';

  constructor(private http: HttpClient) {}

  searchProducts(query: string): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(this.apiUrl, {
      params: { query: query },
    });
  }

  createProduct(productData: { price: number; imageUrl: string; name: string; buyUrl: string; category: string; brand: string; tags: any[] }): Observable<any> {
    return this.http.post(this.apiUrl, productData);
  }

  getProduct(id:string): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(`${this.apiUrl}${id}`);
  }
}
