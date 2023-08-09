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
}
