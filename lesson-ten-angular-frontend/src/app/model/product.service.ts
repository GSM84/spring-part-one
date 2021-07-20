import { Injectable } from '@angular/core';
import {Product} from "./product";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private identityt:number = 6;

  private products: {[key:number]: Product} = {
    1: new Product(1, "Paper", 23.3),
    2: new Product(2, "Paper2", 12.3),
    3: new Product(3, "Paper3", 33.3),
    4: new Product(4, "Paper4", 99.49),

  }

  constructor(public http: HttpClient) { }

  public findAll(){
    return this.http.get<Product[]>("/api/v1/product/all").toPromise();
    // return new Promise<Product[]> ((resolve, reject) =>
    //   {
    //     resolve(
    //       Object.values(this.products)
    //     )
    //   }
    // )
  }

  public findById(id: number){
    return this.http.get<Product>(`/api/v1/product/${id}`).toPromise()
    // return new Promise<Product> ((resolve, reject) =>
    //   {
    //     resolve(
    //       this.products[id]
    //     )
    //   }
    // )
  }

  public save(product: Product){
    const headers = { 'content-type': 'application/json'}
    const body = JSON.stringify(product);

    if (product.id == -1){
      // create new
      return this.http.post<Product>("/api/v1/product", body, {'headers':headers});
    } else {
      // update existing
      return this.http.put<Product>("/api/v1/product", body, {'headers':headers});
    }

    // return new Promise<void> ((resolve, reject) =>
    //   {
    //     if(product.id == -1){
    //       product.id = this.identityt++
    //     }
    //     this.products[product.id] = product;
    //     resolve();
    //   }
    // )
  }

  public delete(id: number){
    return this.http.delete(`/api/v1/product/${id}`).toPromise();
    // return new Promise<void> ((resolve, reject) =>
    //   {
    //     delete this.products[id];
    //     resolve();
    //   }
    // )
  }

}
